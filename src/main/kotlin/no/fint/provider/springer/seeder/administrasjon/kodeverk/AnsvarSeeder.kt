package no.fint.provider.springer.seeder.administrasjon.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.kodeverk.AnsvarResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AnsvarSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<AnsvarResource>(seederRepository, AnsvarResource::class.java) {

    fun generateEntitiesForTest(): List<AnsvarResource> = generateEntities()

    override fun generateEntities(): List<AnsvarResource> {
        return listOf(
            AnsvarResource().apply {
                systemId = Identifikator().apply { 
                    identifikatorverdi = "450693"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1458056725000L) // 2016-03-15T15:45:25Z
                        slutt = Date(4102444800000L)  // 2099-12-01T00:00:00Z
                    }
                }
                kode = "3212"
                navn = "Seksjon for inntak og gjennomføring"
                gyldighetsperiode = Periode().apply {
                    start = Date(1458056725000L)
                    slutt = Date(4102444800000L)
                }
                
                addSelf(link<AnsvarResource>("450693"))
            }
        )
    }
}
