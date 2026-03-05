package no.fint.provider.springer.seeder.administrasjon.kodeverk

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.administrasjon.kodeverk.FravarstypeResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class FravarstypeSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<FravarstypeResource>(seederRepository, FravarstypeResource::class.java) {

    fun generateEntitiesForTest(): List<FravarstypeResource> = generateEntities()

    override fun generateEntities(): List<FravarstypeResource> {
        return listOf(
            FravarstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "915039" }
                kode = "1111"
                navn = "Sykemelding"
                gyldighetsperiode = Periode().apply {
                    start = Date(1480550400000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<FravarstypeResource>("915039"))
            },
            FravarstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "408396" }
                kode = "2222"
                navn = "Egenmelding"
                gyldighetsperiode = Periode().apply {
                    start = Date(1356998400000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<FravarstypeResource>("408396"))
            }
        )
    }
}
