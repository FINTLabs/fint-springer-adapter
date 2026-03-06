package no.fint.provider.springer.seeder.utdanning.utdanningsprogram

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.utdanning.utdanningsprogram.SkoleResource
import no.novari.fint.model.resource.utdanning.utdanningsprogram.UtdanningsprogramResource
import org.springframework.stereotype.Service

@Service
class UtdanningsprogramSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<UtdanningsprogramResource>(seederRepository, UtdanningsprogramResource::class.java) {

    fun generateEntitiesForTest(): List<UtdanningsprogramResource> = generateEntities()

    override fun generateEntities(): List<UtdanningsprogramResource> {
        return listOf(
            UtdanningsprogramResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "ST" }
                navn = "Studiespesialisering"
                beskrivelse = "Studiespesialiserende utdanningsprogram"

                addSelf(link<UtdanningsprogramResource>("ST"))
                addSkole(link<SkoleResource>("123456", "skolenummer"))
            },
            UtdanningsprogramResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "KD" }
                navn = "Kunst, design og arkitektur"
                beskrivelse = "Kunst, design og arkitektur"

                addSelf(link<UtdanningsprogramResource>("KD"))
                addSkole(link<SkoleResource>("123456", "skolenummer"))
            }
        )
    }
}
