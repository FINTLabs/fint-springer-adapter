package no.fint.provider.springer.seeder.utdanning.timeplan

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.utdanning.elev.UndervisningsforholdResource
import no.novari.fint.model.resource.utdanning.kodeverk.SkolearResource
import no.novari.fint.model.resource.utdanning.kodeverk.TerminResource
import no.novari.fint.model.resource.utdanning.timeplan.FagResource
import no.novari.fint.model.resource.utdanning.timeplan.TimeResource
import no.novari.fint.model.resource.utdanning.timeplan.UndervisningsgruppeResource
import no.novari.fint.model.resource.utdanning.utdanningsprogram.SkoleResource
import org.springframework.stereotype.Service

@Service
class UndervisningsgruppeSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<UndervisningsgruppeResource>(seederRepository, UndervisningsgruppeResource::class.java) {

    fun generateEntitiesForTest(): List<UndervisningsgruppeResource> = generateEntities()


    override fun generateEntities(): List<UndervisningsgruppeResource> {
        return listOf(
            UndervisningsgruppeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "UG_2018_1STA_NOR1Z41" }
                navn = "1STA NOR1"
                beskrivelse = "1STA 2018 NOR1Z41"
                addSelf(link<UndervisningsgruppeResource>("UG_2018_1STA_NOR1Z41"))
                addSkole(link<SkoleResource>("123456", "skolenummer"))
                addFag(link<FagResource>("FA_NOR1Z41_2018"))
                addTime(link<TimeResource>("T_NOR1_2018"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("L_100003_1"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("7024200-93838-0-1-20211001000001"))
                addSkolear(link<SkolearResource>("21-22"))
                addTermin(link<TerminResource>("H1"))
                addTermin(link<TerminResource>("H2"))
            },
            UndervisningsgruppeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "10128458_1IDA" }
                navn = "1IDA/NOR1264"
                beskrivelse = "1IDA/NOR1264"
                addSelf(link<UndervisningsgruppeResource>("10128458_1IDA"))
                addFag(link<FagResource>("NOR1264"))
                addSkole(link<SkoleResource>("1579"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("7024200-93838-0-1-20211001000000"))
                addSkolear(link<SkolearResource>("21-22"))
                addTermin(link<TerminResource>("H1"))
                addTermin(link<TerminResource>("H2"))
            },
            UndervisningsgruppeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "10128458_2IDA" }
                navn = "2IDA/NOR1264"
                beskrivelse = "2IDA/NOR1264"
                addSelf(link<UndervisningsgruppeResource>("10128458_2IDA"))
                addFag(link<FagResource>("NOR1264"))
                addSkole(link<SkoleResource>("1579"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("7024200-93838-0-1-20211001000000"))
                addSkolear(link<SkolearResource>("21-22"))
                addTermin(link<TerminResource>("H1"))
                addTermin(link<TerminResource>("H2"))
            }
        )
    }
}
