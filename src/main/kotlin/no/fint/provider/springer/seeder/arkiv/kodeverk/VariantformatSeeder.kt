package no.fint.provider.springer.seeder.arkiv.kodeverk

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.arkiv.kodeverk.VariantformatResource
import org.springframework.stereotype.Service

@Service
class VariantformatSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<VariantformatResource>(seederRepository, VariantformatResource::class.java) {

    fun generateEntitiesForTest(): List<VariantformatResource> = generateEntities()


    override fun generateEntities(): List<VariantformatResource> {
        return listOf(
            VariantformatResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "O" }
                kode = "O"
                navn = "Dokument hvor deler av innholdet er skjermet"
                addSelf(link<VariantformatResource>("O"))
            },
            VariantformatResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "P" }
                kode = "P"
                navn = "Produksjonsformat"
                addSelf(link<VariantformatResource>("P"))
            },
            VariantformatResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "4" }
                kode = "V"
                navn = "Visningsformat"
                addSelf(link<VariantformatResource>("4"))
            },
            VariantformatResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "2" }
                kode = "O"
                navn = "Offentlig format"
                addSelf(link<VariantformatResource>("2"))
            },
            VariantformatResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1" }
                kode = "A"
                navn = "Arkivformat"
                addSelf(link<VariantformatResource>("1"))
            },
            VariantformatResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "A" }
                kode = "A"
                navn = "Arkivformat"
                addSelf(link<VariantformatResource>("A"))
            },
            VariantformatResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "0" }
                kode = "P"
                navn = "Produksjonsformat"
                addSelf(link<VariantformatResource>("0"))
            },
            VariantformatResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "3" }
                kode = "S"
                navn = "Signert dokument"
                addSelf(link<VariantformatResource>("3"))
            }
        )
    }
}
