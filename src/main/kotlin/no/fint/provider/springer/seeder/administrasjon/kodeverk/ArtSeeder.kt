package no.fint.provider.springer.seeder.administrasjon.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.kodeverk.ArtResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class ArtSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<ArtResource>(seederRepository, ArtResource::class.java) {

    fun generateEntitiesForTest(): List<ArtResource> = generateEntities()

    override fun generateEntities(): List<ArtResource> {
        return listOf(
            ArtResource().apply {
                systemId = Identifikator().apply { 
                    identifikatorverdi = "391693"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1385856000000L) // 2013-12-01T00:00:00Z
                        slutt = Date(4102444800000L)  // 2099-12-01T00:00:00Z
                    }
                }
                kode = "010"
                navn = "Lønn"
                gyldighetsperiode = Periode().apply {
                    start = Date(1385856000000L)
                    slutt = Date(4102444800000L)
                }
                
                addSelf(link<ArtResource>("391693"))
            }
        )
    }
}
