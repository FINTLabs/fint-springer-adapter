package no.fint.provider.springer.seeder

import net.datafaker.Faker
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.resource.Link
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.ZoneOffset
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

    protected inline fun <reified R> link(systemid: String, param: String = "systemid"): Link =
        Link.with(R::class.java, param, systemid)

    protected abstract fun generateEntities(): List<T>
}
