package no.fint.provider.springer.seeder.utdanning.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.utdanning.kodeverk.SkoleeiertypeResource
import org.springframework.stereotype.Service

@Service
class SkoleeiertypeSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<SkoleeiertypeResource>(seederRepository, SkoleeiertypeResource::class.java) {

    fun generateEntitiesForTest(): List<SkoleeiertypeResource> = generateEntities()


    override fun generateEntities(): List<SkoleeiertypeResource> {
        return listOf(
            SkoleeiertypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1" }
                kode = "1"
                navn = "Fylke"
                addSelf(link<SkoleeiertypeResource>("1"))
            },
            SkoleeiertypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "2" }
                kode = "2"
                navn = "Stat"
                addSelf(link<SkoleeiertypeResource>("2"))
            },
            SkoleeiertypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "3" }
                kode = "3"
                navn = "Kommune"
                addSelf(link<SkoleeiertypeResource>("3"))
            },
            SkoleeiertypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "4" }
                kode = "4"
                navn = "Privat"
                addSelf(link<SkoleeiertypeResource>("4"))
            }
        )
    }
}
