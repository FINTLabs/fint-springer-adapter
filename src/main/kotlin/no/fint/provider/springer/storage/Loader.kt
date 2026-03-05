package no.fint.provider.springer.storage

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import no.fint.provider.springer.config.SpringerProperties
import org.slf4j.LoggerFactory
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.stream.StreamSupport

@Service
class Loader(
    private val mongoTemplate: MongoTemplate,
    private val wrapper: Wrapper,
    private val objectMapper: ObjectMapper,
    private val springerProperties: SpringerProperties,
    private val fakerSeeder: FakerSeeder
) {
    private val log = LoggerFactory.getLogger(Loader::class.java)

    @PostConstruct
    @Throws(IOException::class, ClassNotFoundException::class)
    fun load() {
        log.info("Checking database content ...")
        reseedConfiguredTypes()
        loadJsonResourcesIfMissing()
        fakerSeeder.seedIfEmpty()

        log.info("Completed database initialization.")
    }

    private fun reseedConfiguredTypes() {
        if (springerProperties.reseedTypes.isEmpty()) return

        springerProperties.reseedTypes.forEach { type ->
            val result = mongoTemplate.remove(Query(Criteria.where("type").`is`(type)), Springer::class.java)
            if (result.deletedCount > 0) {
                log.info("Removed {} elements of {} as per configuration.", result.deletedCount, type)
            }
        }
    }

    private fun loadJsonResourcesIfMissing() {
        for (r in PathMatchingResourcePatternResolver(javaClass.classLoader).getResources("classpath*:/springer/**/*.json")) {
            try {
                log.info("Checking {} ...", r)
                val jsonNode: JsonNode = objectMapper.readTree(r.inputStream)
                val type = Class.forName(jsonNode.get("_class").asText())
                if (mongoTemplate.count(wrapper.query(type), Springer::class.java) == 0L) {
                    val count = StreamSupport
                        .stream(jsonNode.get("_embedded").get("_entries").spliterator(), false)
                        .map(wrapper.wrapper(type))
                        .peek(mongoTemplate::insert)
                        .count()
                    log.info("Added {} elements of {}", count, type)
                }
            } catch (e: Exception) {
                log.error("Exception in load() on ${r.filename}: ${e.message}")
                throw e
            }
        }
    }
}
