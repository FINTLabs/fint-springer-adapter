package no.fint.provider.springer.seeder

import net.datafaker.Faker
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.resource.Link
import org.slf4j.LoggerFactory
import java.util.Locale

abstract class BaseSeeder<T>(
    protected val seederRepository: SeederRepository,
    private val entityClass: Class<T>
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    protected val faker = Faker(Locale.of("no"))

    fun seedIfMissing() {
        if (seederRepository.exists(entityClass)) {
            log.trace("No need to seed ${entityClass.simpleName} - already exists.")
            return
        }

        val entities = generateEntities()
        entities.forEach { entity ->
            seederRepository.save(entity, entityClass)
        }
        log.info("Running seeder for ${entityClass.simpleName}: Added ${entities.size} entities.")
    }

    protected inline fun <reified R> link(identificatorValue: String, identificatorName: String = "systemid"): Link =
        Link.with(R::class.java, identificatorName, identificatorValue)

    fun handledTypeName(): String = entityClass.canonicalName

    protected abstract fun generateEntities(): List<T>
}
