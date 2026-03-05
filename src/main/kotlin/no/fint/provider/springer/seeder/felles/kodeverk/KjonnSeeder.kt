package no.fint.provider.springer.seeder.felles.kodeverk

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.felles.kodeverk.iso.KjonnResource
import org.springframework.stereotype.Service

@Service
class KjonnSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<KjonnResource>(seederRepository, KjonnResource::class.java) {

    fun generateEntitiesForTest(): List<KjonnResource> = generateEntities()


    override fun generateEntities(): List<KjonnResource> {
        return listOf(
            KjonnResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "5" }
                kode = "5"
                navn = "transkjønnet mann"
                addSelf(link<KjonnResource>("5"))
            },
            KjonnResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "9" }
                kode = "9"
                navn = "gjelder ikke"
                addSelf(link<KjonnResource>("9"))
            },
            KjonnResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "0" }
                kode = "0"
                navn = "uvisst"
                addSelf(link<KjonnResource>("0"))
            },
            KjonnResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "4" }
                kode = "4"
                navn = "transkjønnet kvinne"
                addSelf(link<KjonnResource>("4"))
            },
            KjonnResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1" }
                kode = "1"
                navn = "mann"
                addSelf(link<KjonnResource>("1"))
            },
            KjonnResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "2" }
                kode = "2"
                navn = "kvinne"
                addSelf(link<KjonnResource>("2"))
            },
            KjonnResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "8" }
                kode = "8"
                navn = "ikke oppgitt"
                addSelf(link<KjonnResource>("8"))
            },
            KjonnResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "7" }
                kode = "7"
                navn = "hermafroditt"
                addSelf(link<KjonnResource>("7"))
            }
        )
    }
}
