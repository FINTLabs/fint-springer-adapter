package no.fint.provider.springer.seeder.arkiv.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.arkiv.kodeverk.SaksstatusResource
import org.springframework.stereotype.Service

@Service
class SaksstatusSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<SaksstatusResource>(seederRepository, SaksstatusResource::class.java) {

    fun generateEntitiesForTest(): List<SaksstatusResource> = generateEntities()

    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<SaksstatusResource> {
        return listOf(
            SaksstatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "R" }
                kode = "R"
                navn = "Opprettet av saksbehandler"
                addSelf(link<SaksstatusResource>("R"))
            },
            SaksstatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "B" }
                kode = "B"
                navn = "Under behandling"
                addSelf(link<SaksstatusResource>("B"))
            },
            SaksstatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "S" }
                kode = "S"
                navn = "Avsluttet av saksbehandler"
                addSelf(link<SaksstatusResource>("S"))
            },
            SaksstatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "F" }
                kode = "F"
                navn = "Ferdig fra saksbehandler"
                addSelf(link<SaksstatusResource>("F"))
            },
            SaksstatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "19" }
                kode = "OE"
                navn = "Overført til eArchive"
                addSelf(link<SaksstatusResource>("19"))
            },
            SaksstatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "6" }
                kode = "A"
                navn = "Avsluttet"
                addSelf(link<SaksstatusResource>("6"))
            },
            SaksstatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "17" }
                kode = "AS"
                navn = "Avsluttet av saksbehandler"
                addSelf(link<SaksstatusResource>("17"))
            },
            SaksstatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "8" }
                kode = "U"
                navn = "Utgår"
                addSelf(link<SaksstatusResource>("8"))
            },
            SaksstatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "P" }
                kode = "P"
                navn = "Unntatt prosesstyring"
                addSelf(link<SaksstatusResource>("P"))
            },
            SaksstatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "5" }
                kode = "B"
                navn = "Under behandling"
                addSelf(link<SaksstatusResource>("5"))
            },
            SaksstatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "20" }
                kode = "K"
                navn = "Kassert"
                addSelf(link<SaksstatusResource>("20"))
            },
            SaksstatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "U" }
                kode = "U"
                navn = "Utgår"
                addSelf(link<SaksstatusResource>("U"))
            },
            SaksstatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "4" }
                kode = "R"
                navn = "Reservert"
                addSelf(link<SaksstatusResource>("4"))
            },
            SaksstatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "A" }
                kode = "A"
                navn = "Avsluttet"
                addSelf(link<SaksstatusResource>("A"))
            }
        )
    }
}
