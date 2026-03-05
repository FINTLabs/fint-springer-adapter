package no.fint.provider.springer.seeder

import net.datafaker.Faker
import no.fint.provider.springer.storage.SeederRepository
import org.slf4j.LoggerFactory
import java.util.Locale

abstract class AbstractSeeder<T>(
    protected val seederRepository: SeederRepository,
    private val entityClass: Class<T>
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    protected val faker = Faker(Locale.of("no"))

    fun seedIfMissing() {
        if (seederRepository.exists(entityClass)) return
        
        val entities = generateEntities()
        entities.forEach { entity ->
            seederRepository.save(entity, entityClass)
        }
        
        log.info("Added {} {} resources", entities.size, entityClass.simpleName)
    }

    protected abstract fun generateEntities(): List<T>
}
