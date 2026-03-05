package no.fint.provider.springer.seeder.utdanning.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.utdanning.kodeverk.TerminResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class TerminSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<TerminResource>(seederRepository, TerminResource::class.java) {

    fun generateEntitiesForTest(): List<TerminResource> = generateEntities()

    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<TerminResource> {
        return listOf(
            TerminResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "H1" }
                kode = "H1"
                navn = "H1"
                gyldighetsperiode = Periode().apply {
                    start = Date(1627776000000L)
                    slutt = Date(1642118400000L)
                }
                addSelf(link<TerminResource>("H1"))
            },
            TerminResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "H2" }
                kode = "H2"
                navn = "H2"
                gyldighetsperiode = Periode().apply {
                    start = Date(1642377600000L)
                    slutt = Date(1659225600000L)
                }
                addSelf(link<TerminResource>("H2"))
            }
        )
    }
}
