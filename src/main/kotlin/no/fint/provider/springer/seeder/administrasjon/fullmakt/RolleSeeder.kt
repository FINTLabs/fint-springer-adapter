package no.fint.provider.springer.seeder.administrasjon.fullmakt

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.fullmakt.FullmaktResource
import no.novari.fint.model.resource.administrasjon.fullmakt.RolleResource
import org.springframework.stereotype.Service

@Service
class RolleSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<RolleResource>(seederRepository, RolleResource::class.java) {

    fun generateEntitiesForTest(): List<RolleResource> = generateEntities()

    override fun generateEntities(): List<RolleResource> {
        return listOf(
            RolleResource().apply {
                navn = Identifikator().apply { identifikatorverdi = "attest" }
                beskrivelse = "Attestasjon"
                
                addSelf(link<RolleResource>("attest", "navn"))
                addFullmakt(link<FullmaktResource>("ABC123"))
            },
            RolleResource().apply {
                navn = Identifikator().apply { identifikatorverdi = "anvis" }
                beskrivelse = "Anvisning"
                
                addSelf(link<RolleResource>("anvis", "navn"))
                addFullmakt(link<FullmaktResource>("XYZ456"))
            }
        )
    }
}
