package no.fint.provider.springer.seeder.utdanning.vurdering

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.utdanning.timeplan.FagResource
import no.novari.fint.model.resource.utdanning.utdanningsprogram.SkoleResource
import no.novari.fint.model.resource.utdanning.vurdering.EksamensgruppeResource
import org.springframework.stereotype.Service

@Service
class EksamensgruppeSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<EksamensgruppeResource>(seederRepository, EksamensgruppeResource::class.java) {

    fun generateEntitiesForTest(): List<EksamensgruppeResource> = generateEntities()

    override fun generateEntities(): List<EksamensgruppeResource> {
        return listOf(
            EksamensgruppeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "EG_NOR1Z41_2018" }
                navn = "NOR1Z41 eksamensgruppe"
                beskrivelse = "Eksamensgruppe for NOR1Z41"

                addSelf(link<EksamensgruppeResource>("EG_NOR1Z41_2018"))
                addFag(link<FagResource>("FA_NOR1Z41_2018"))
                addSkole(link<SkoleResource>("XX1234"))
            }
        )
    }
}
