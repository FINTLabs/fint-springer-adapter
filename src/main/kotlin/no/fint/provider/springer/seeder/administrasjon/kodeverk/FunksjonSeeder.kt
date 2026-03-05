package no.fint.provider.springer.seeder.administrasjon.kodeverk

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.administrasjon.kodeverk.FunksjonResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class FunksjonSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<FunksjonResource>(seederRepository, FunksjonResource::class.java) {

    fun generateEntitiesForTest(): List<FunksjonResource> = generateEntities()

    override fun generateEntities(): List<FunksjonResource> {
        return listOf(
            FunksjonResource().apply {
                systemId = Identifikator().apply { 
                    identifikatorverdi = "400573"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1385856000000L) // 2013-12-01T00:00:00Z
                        slutt = Date(4102444800000L)  // 2099-12-01T00:00:00Z
                    }
                }
                kode = "100"
                navn = "Undervisning"
                gyldighetsperiode = Periode().apply {
                    start = Date(1385856000000L)
                    slutt = Date(4102444800000L)
                }
                
                addSelf(link<FunksjonResource>("400573"))
            }
        )
    }
}
