package no.fint.provider.springer.seeder.utdanning.elev

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.utdanning.elev.KlasseResource
import no.novari.fint.model.resource.utdanning.elev.KontaktlarergruppeResource
import no.novari.fint.model.resource.utdanning.elev.UndervisningsforholdResource
import no.novari.fint.model.resource.utdanning.kodeverk.TerminResource
import no.novari.fint.model.resource.utdanning.utdanningsprogram.SkoleResource
import org.springframework.stereotype.Service

@Service
class KontaktlarergruppeSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<KontaktlarergruppeResource>(seederRepository, KontaktlarergruppeResource::class.java) {

    fun generateEntitiesForTest(): List<KontaktlarergruppeResource> = generateEntities()

    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<KontaktlarergruppeResource> {
        return listOf(
            KontaktlarergruppeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "KG_1STA-1" }
                navn = "1STA-1"
                beskrivelse = "Kontaktlærergruppe 1, 1. trinn Studiespesialisering 2018-2019"
                addSelf(link<KontaktlarergruppeResource>("KG_1STA-1"))
                addSkole(link<SkoleResource>("123456", "skolenummer"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("L_100003_1"))
                addKlasse(link<KlasseResource>("BG_1STA-2018"))
                addTermin(link<TerminResource>("H21"))
            },
            KontaktlarergruppeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1321122_1IDA" }
                navn = "1IDA"
                beskrivelse = "1IDA"
                addSelf(link<KontaktlarergruppeResource>("1321122_1IDA"))
            },
            KontaktlarergruppeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1321122_2IDA" }
                navn = "2IDA"
                beskrivelse = "2IDA"
                addSelf(link<KontaktlarergruppeResource>("1321122_2IDA"))
            }
        )
    }
}
