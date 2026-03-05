package no.fint.provider.springer.seeder.arkiv.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.arkiv.kodeverk.DokumentStatusResource
import org.springframework.stereotype.Service

@Service
class DokumentstatusSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<DokumentStatusResource>(seederRepository, DokumentStatusResource::class.java) {

    fun generateEntitiesForTest(): List<DokumentStatusResource> = generateEntities()

    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<DokumentStatusResource> {
        return listOf(
            DokumentStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "B" }
                kode = "B"
                navn = "Dokumentet er under redigering"
                addSelf(link<DokumentStatusResource>("B"))
            },
            DokumentStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "2" }
                kode = "F"
                navn = "Ferdig"
                addSelf(link<DokumentStatusResource>("2"))
            },
            DokumentStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "4" }
                kode = "V"
                navn = "Utgått"
                addSelf(link<DokumentStatusResource>("4"))
            },
            DokumentStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1" }
                kode = "B"
                navn = "Behandles"
                addSelf(link<DokumentStatusResource>("1"))
            },
            DokumentStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "3" }
                kode = "A"
                navn = "Godkjent"
                addSelf(link<DokumentStatusResource>("3"))
            },
            DokumentStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "F" }
                kode = "F"
                navn = "Dokumentet er ferdigstilt"
                addSelf(link<DokumentStatusResource>("F"))
            },
            DokumentStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "5" }
                kode = "S"
                navn = "Klar for eSignering"
                addSelf(link<DokumentStatusResource>("5"))
            }
        )
    }
}
