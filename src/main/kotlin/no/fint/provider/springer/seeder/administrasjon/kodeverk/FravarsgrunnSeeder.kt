package no.fint.provider.springer.seeder.administrasjon.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.kodeverk.FravarsgrunnResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class FravarsgrunnSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<FravarsgrunnResource>(seederRepository, FravarsgrunnResource::class.java) {

    fun generateEntitiesForTest(): List<FravarsgrunnResource> = generateEntities()

    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<FravarsgrunnResource> {
        return listOf(
            FravarsgrunnResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "915039" }
                kode = "111"
                navn = "Arbeidsrelatert"
                gyldighetsperiode = Periode().apply {
                    start = Date(1480550400000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<FravarsgrunnResource>("915039"))
            },
            FravarsgrunnResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "408396" }
                kode = "222"
                navn = "Ikke arbeidsrelatert"
                gyldighetsperiode = Periode().apply {
                    start = Date(1356998400000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<FravarsgrunnResource>("408396"))
            }
        )
    }
}
