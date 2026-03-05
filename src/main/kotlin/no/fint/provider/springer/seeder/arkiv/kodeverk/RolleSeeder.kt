package no.fint.provider.springer.seeder.arkiv.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.arkiv.kodeverk.RolleResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class RolleSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<RolleResource>(seederRepository, RolleResource::class.java) {

    fun generateEntitiesForTest(): List<RolleResource> = generateEntities()


    override fun generateEntities(): List<RolleResource> {
        return listOf(
            RolleResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "101" }
                kode = "101"
                navn = "Arkivansvarlig"
                gyldighetsperiode = Periode().apply {
                    start = Date(1576749712000L)
                }
                addSelf(link<RolleResource>("101"))
            }
        )
    }
}
