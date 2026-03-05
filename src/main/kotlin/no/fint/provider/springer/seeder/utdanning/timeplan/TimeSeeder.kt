package no.fint.provider.springer.seeder.utdanning.timeplan

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.utdanning.elev.UndervisningsforholdResource
import no.novari.fint.model.resource.utdanning.timeplan.RomResource
import no.novari.fint.model.resource.utdanning.timeplan.TimeResource
import no.novari.fint.model.resource.utdanning.timeplan.UndervisningsgruppeResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class TimeSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<TimeResource>(seederRepository, TimeResource::class.java) {

    fun generateEntitiesForTest(): List<TimeResource> = generateEntities()

    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<TimeResource> {
        return listOf(
            TimeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "T_NOR1_2018" }
                navn = "Norsk"
                beskrivelse = "Norsk hovedmål"
                tidsrom = Periode().apply {
                    start = Date(1522749600000L)
                    slutt = Date(1522746000000L)
                }
                
                addSelf(link<TimeResource>("T_NOR1_2018"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("L_100003_1"))
                addRom(link<RomResource>("SUX202"))
                addUndervisningsgruppe(link<UndervisningsgruppeResource>("UG_2018_1STA_NOR1Z41"))
            }
        )
    }
}
