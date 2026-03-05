package no.fint.provider.springer.seeder.administrasjon.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.kodeverk.LonnsartResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class LonnsartSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<LonnsartResource>(seederRepository, LonnsartResource::class.java) {

    fun generateEntitiesForTest(): List<LonnsartResource> = generateEntities()

    override fun generateEntities(): List<LonnsartResource> {
        return listOf(
            LonnsartResource().apply {
                systemId = Identifikator().apply { 
                    identifikatorverdi = "394636"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1385856000000L) // 2013-12-01T00:00:00Z
                        slutt = Date(4102444800000L)  // 2099-12-01T00:00:00Z
                    }
                }
                kode = "0001"
                navn = "Fastlønn"
                gyldighetsperiode = Periode().apply {
                    start = Date(1385856000000L)
                    slutt = Date(4102444800000L)
                }
                
                addSelf(link<LonnsartResource>("394636"))
            }
        )
    }
}
