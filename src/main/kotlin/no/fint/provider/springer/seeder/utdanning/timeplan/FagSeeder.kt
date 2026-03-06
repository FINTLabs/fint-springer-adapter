package no.fint.provider.springer.seeder.utdanning.timeplan

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.utdanning.timeplan.FagResource
import no.novari.fint.model.resource.utdanning.timeplan.UndervisningsgruppeResource
import no.novari.fint.model.resource.utdanning.utdanningsprogram.SkoleResource
import org.springframework.stereotype.Service

@Service
class FagSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<FagResource>(seederRepository, FagResource::class.java) {

    fun generateEntitiesForTest(): List<FagResource> = generateEntities()


    override fun generateEntities(): List<FagResource> {
        return listOf(
            FagResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "FA_NOR1Z41_2018" }
                navn = "NOR1Z41"
                beskrivelse = "Norsk Vg1 studieforberedende utdanningsprogram"
                
                addSelf(link<FagResource>("FA_NOR1Z41_2018"))
                addSkole(link<SkoleResource>("XX1234"))
                addUndervisningsgruppe(link<UndervisningsgruppeResource>("UG_2018_1STA_NOR1Z41"))
            },
            FagResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "NOR1264" }
                navn = "NOR1264"
                beskrivelse = "Norsk for Andeby VGS"

                addSelf(link<FagResource>("NOR1264"))
                addSkole(link<SkoleResource>("1579"))
                addUndervisningsgruppe(link<UndervisningsgruppeResource>("10128458_1IDA"))
                addUndervisningsgruppe(link<UndervisningsgruppeResource>("10128458_2IDA"))
            },
            FagResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "NOR0207" }
                navn = "NOR0207"
                beskrivelse = "Norsk"

                addSelf(link<FagResource>("NOR0207"))
            },
            FagResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "ENGK007" }
                navn = "ENGK007"
                beskrivelse = "Engelsk"

                addSelf(link<FagResource>("ENGK007"))
            }
        )
    }
}
