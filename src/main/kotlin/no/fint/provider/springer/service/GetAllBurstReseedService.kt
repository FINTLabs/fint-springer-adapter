package no.fint.provider.springer.service

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.Springer
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.resource.FintLinks
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import java.time.Clock
import java.util.ArrayDeque
import java.util.concurrent.ConcurrentHashMap

@Service
class GetAllBurstReseedService(
    private val mongoTemplate: MongoTemplate,
    private val wrapper: Wrapper,
    private val applicationContext: ApplicationContext,
    private val clock: Clock = Clock.systemUTC()
) {
    private val log = LoggerFactory.getLogger(GetAllBurstReseedService::class.java)
    private val eventTimesByType = ConcurrentHashMap<String, ArrayDeque<Long>>()

    fun registerGetAll(type: Class<out FintLinks>, action: String) {
        if (!action.startsWith("GET_ALL_")) return

        val now = clock.millis()
        val threshold = now - WINDOW_MILLIS
        val typeKey = type.canonicalName
        val queue = eventTimesByType.computeIfAbsent(typeKey) { ArrayDeque() }

        val shouldReseed = synchronized(queue) {
            while (queue.isNotEmpty() && queue.first() < threshold) {
                queue.removeFirst()
            }
            queue.addLast(now)
            if (queue.size >= REQUIRED_HITS) {
                queue.clear()
                true
            } else {
                false
            }
        }

        if (shouldReseed) {
            reseed(type)
        }
    }

    private fun reseed(type: Class<out FintLinks>) {
        val deleted = mongoTemplate.remove(wrapper.query(type), Springer::class.java).deletedCount
        val fakerSeeded = seedFromFakerSeeder(type)

        log.info(
            "Reseeded {} after GET_ALL burst using Faker seeders: deleted={}, matchedSeeders={}",
            type.canonicalName,
            deleted,
            fakerSeeded
        )

        if (fakerSeeded == 0) {
            log.warn("No BaseSeeder matched type {} during burst reseed", type.canonicalName)
        }
    }

    private fun seedFromFakerSeeder(type: Class<out FintLinks>): Int {
        val seeders = applicationContext.getBeansOfType(BaseSeeder::class.java).values
            .filter { it.handledTypeName() == type.canonicalName }

        seeders.forEach { it.seedIfMissing() }
        return seeders.size
    }

    companion object {
        private const val REQUIRED_HITS = 3
        private const val WINDOW_MILLIS = 10_000L
    }
}
