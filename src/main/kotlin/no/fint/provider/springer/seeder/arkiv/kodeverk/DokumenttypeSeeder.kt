package no.fint.provider.springer.seeder.arkiv.kodeverk

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.arkiv.kodeverk.DokumentTypeResource
import org.springframework.stereotype.Service

@Service
class DokumenttypeSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<DokumentTypeResource>(seederRepository, DokumentTypeResource::class.java) {

    fun generateEntitiesForTest(): List<DokumentTypeResource> = generateEntities()


    override fun generateEntities(): List<DokumentTypeResource> {
        return listOf(
            DokumentTypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1" }
                kode = "Brev"
                navn = "Brev"
                addSelf(link<DokumentTypeResource>("1"))
            },
            DokumentTypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "2" }
                kode = "Notat"
                navn = "Notat"
                addSelf(link<DokumentTypeResource>("2"))
            },
            DokumentTypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "O" }
                kode = "O"
                navn = "Ordrebekreftelse"
                addSelf(link<DokumentTypeResource>("O"))
            },
            DokumentTypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "B" }
                kode = "B"
                navn = "Brev"
                addSelf(link<DokumentTypeResource>("B"))
            },
            DokumentTypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "R" }
                kode = "R"
                navn = "Rundskriv"
                addSelf(link<DokumentTypeResource>("R"))
            },
            DokumentTypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "F" }
                kode = "F"
                navn = "Faktura"
                addSelf(link<DokumentTypeResource>("F"))
            }
        )
    }
}
