package no.fint.provider.springer.seeder.administrasjon.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.kodeverk.ProsjektResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class ProsjektSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<ProsjektResource>(seederRepository, ProsjektResource::class.java) {

    fun generateEntitiesForTest(): List<ProsjektResource> = generateEntities()

    override fun generateEntities(): List<ProsjektResource> {
        return listOf(
            ProsjektResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "437135" }
                kode = "106P1501"
                navn = "Lyttevenn"
                gyldighetsperiode = Periode().apply {
                    start = Date(1398902400000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ProsjektResource>("437135"))
            },
            ProsjektResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "881018" }
                kode = "2P1501"
                navn = "Egenkapitalinnskudd Telemark IKS"
                gyldighetsperiode = Periode().apply {
                    start = Date(1420070400000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ProsjektResource>("881018"))
            },
            ProsjektResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "891033" }
                kode = "108P1501"
                navn = "Konferanse psykisk helse"
                gyldighetsperiode = Periode().apply {
                    start = Date(1420070400000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ProsjektResource>("891033"))
            }
        )
    }
}
