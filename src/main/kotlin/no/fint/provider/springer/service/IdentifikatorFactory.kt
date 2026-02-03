package no.fint.provider.springer.service

import jakarta.annotation.PostConstruct
import no.fint.provider.springer.storage.Springer
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
class IdentifikatorFactory(
    private val mongoTemplate: MongoTemplate?
) {
    private val log = LoggerFactory.getLogger(IdentifikatorFactory::class.java)
    private val atomicInteger = AtomicInteger()

    @PostConstruct
    fun init() {
        mongoTemplate?.let {
            try {
                val count = it.count(Query(), Springer::class.java)
                atomicInteger.set(count.toInt())
                log.info("Setting initial value to {}", atomicInteger.get())
            } catch (e: Exception) {
                log.info("Unable to update initial value: {} {}", e.javaClass.simpleName, e.message)
            }
        }
    }

    fun create(): Identifikator = Identifikator().apply {
        identifikatorverdi = "ID%010d".format(atomicInteger.incrementAndGet())
    }
}
