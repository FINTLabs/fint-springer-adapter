package no.fint.provider.springer.storage

import no.fint.provider.springer.seeder.BaseSeeder
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationContext
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
class FakerSeeder(
    private val applicationContext: ApplicationContext
) {
    private val log = LoggerFactory.getLogger(FakerSeeder::class.java)

    @EventListener
    @Order(1)
    fun onApplicationReady(event: ApplicationReadyEvent) {
        seedIfEmpty()
    }

    fun seedIfEmpty() {
        log.info("Starting data seeding...")

        // Automatically find and run all seeders
        val seeders = applicationContext.getBeansOfType(BaseSeeder::class.java).values

        seeders.forEach { seeder ->
            log.info("Running seeder: ${seeder::class.simpleName}")
            seeder.seedIfMissing()
        }

        log.info("Data seeding completed. Seeded ${seeders.size} seeders.")
    }
}
