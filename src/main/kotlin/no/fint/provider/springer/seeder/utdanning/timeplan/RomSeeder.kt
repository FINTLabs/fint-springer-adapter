package no.fint.provider.springer.seeder.utdanning.timeplan

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.utdanning.timeplan.RomResource
import no.novari.fint.model.resource.utdanning.timeplan.TimeResource
import org.springframework.stereotype.Service

@Service
class RomSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<RomResource>(seederRepository, RomResource::class.java) {

    fun generateEntitiesForTest(): List<RomResource> = generateEntities()


    override fun generateEntities(): List<RomResource> {
        return listOf(
            RomResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "SUX202" }
                navn = "202"
                
                addSelf(link<RomResource>("SUX202"))
                addTime(link<TimeResource>("T_NOR1_2018"))
            }
        )
    }
}
