package no.fint.provider.springer.seeder.arkiv.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.arkiv.kodeverk.TilgangsrestriksjonResource
import org.springframework.stereotype.Service

@Service
class TilgangsrestriksjonSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<TilgangsrestriksjonResource>(seederRepository, TilgangsrestriksjonResource::class.java) {

    fun generateEntitiesForTest(): List<TilgangsrestriksjonResource> = generateEntities()


    override fun generateEntities(): List<TilgangsrestriksjonResource> {
        return listOf(
            TilgangsrestriksjonResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "0" }
                kode = "U"
                navn = "Ugradert"
                addSelf(link<TilgangsrestriksjonResource>("0"))
            },
            TilgangsrestriksjonResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "18" }
                kode = "UO"
                navn = "Unntatt offentlighet"
                addSelf(link<TilgangsrestriksjonResource>("18"))
            }
        )
    }
}
