package no.fint.provider.springer.seeder.administrasjon.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.kodeverk.UketimetallResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class UketimetallSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<UketimetallResource>(seederRepository, UketimetallResource::class.java) {

    fun generateEntitiesForTest(): List<UketimetallResource> = generateEntities()


    override fun generateEntities(): List<UketimetallResource> {
        val codes = listOf(
            "DAGTID" to "ordning",
            "34,0" to "timers uke",
            "TURNUS" to "ordning",
            "35,0" to "timers uke",
            "40" to "timers uke",
            "33,6" to "timers uke",
            "42,0" to "timers uke",
            "35,5" to "timers uke",
            "FYSIO" to "ordning",
            "37,5" to "timers uke",
            "40,0" to "timers uke",
            "BRANN" to "ordning",
            "36,0" to "timers uke",
            "34,5" to "timers uke"
        )
        
        return codes.map { (kode, type) ->
            val systemIdValue = faker.number().digits(6)
            val startDate = Date.from(faker.timeAndDate().past(3650, java.util.concurrent.TimeUnit.DAYS))
            val endDate = Date.from(faker.timeAndDate().future(36500, java.util.concurrent.TimeUnit.DAYS))
            
            UketimetallResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = systemIdValue }
                this.kode = kode
                navn = when (type) {
                    "ordning" -> "${faker.job().field()}$type"
                    else -> "$kode $type"
                }
                gyldighetsperiode = Periode().apply {
                    start = startDate
                    slutt = endDate
                }
                addSelf(link<UketimetallResource>(systemIdValue))
            }
        }
    }
}
