package no.fint.provider.springer.seeder.utdanning.elev

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.utdanning.elev.KlasseResource
import no.novari.fint.model.resource.utdanning.elev.KontaktlarergruppeResource
import no.novari.fint.model.resource.utdanning.elev.UndervisningsforholdResource
import no.novari.fint.model.resource.utdanning.kodeverk.SkolearResource
import no.novari.fint.model.resource.utdanning.kodeverk.TerminResource
import no.novari.fint.model.resource.utdanning.utdanningsprogram.ArstrinnResource
import no.novari.fint.model.resource.utdanning.utdanningsprogram.SkoleResource
import org.springframework.stereotype.Service

@Service
class KlasseSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<KlasseResource>(seederRepository, KlasseResource::class.java) {

    fun generateEntitiesForTest(): List<KlasseResource> = generateEntities()

    override fun generateEntities(): List<KlasseResource> {
        return listOf(
            KlasseResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "BG_1STA-2018" }
                navn = "1STA"
                beskrivelse = "1. trinn Studiespesialisering 2018-2019"
                addSelf(link<KlasseResource>("BG_1STA-2018"))
                addTrinn(link<ArstrinnResource>("vg1"))
                addSkole(link<SkoleResource>("123456", "skolenummer"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("L_100003_1"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("7024200-93838-0-1-20211001000001"))
                addKontaktlarergruppe(link<KontaktlarergruppeResource>("KG_1STA-1"))
                addSkolear(link<SkolearResource>("21-22"))
                addTermin(link<TerminResource>("H1"))
                addTermin(link<TerminResource>("H2"))
            },
            KlasseResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1321121" }
                navn = "1IDA"
                beskrivelse = "1IDA"
                addSelf(link<KlasseResource>("1321121"))
                addTrinn(link<ArstrinnResource>("vg1"))
                addSkole(link<SkoleResource>("1579"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("7024200-93838-0-1-20211001000000"))
                addKontaktlarergruppe(link<KontaktlarergruppeResource>("1321122_1IDA"))
                addSkolear(link<SkolearResource>("21-22"))
                addTermin(link<TerminResource>("H1"))
                addTermin(link<TerminResource>("H2"))
            },
            KlasseResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1321122" }
                navn = "2IDA"
                beskrivelse = "2IDA"
                addSelf(link<KlasseResource>("1321122"))
                addTrinn(link<ArstrinnResource>("vg2"))
                addSkole(link<SkoleResource>("1579"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("7024200-93838-0-1-20211001000000"))
                addKontaktlarergruppe(link<KontaktlarergruppeResource>("1321122_2IDA"))
                addSkolear(link<SkolearResource>("21-22"))
                addTermin(link<TerminResource>("H1"))
                addTermin(link<TerminResource>("H2"))
            },
            KlasseResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1321123" }
                navn = "3IDA"
                beskrivelse = "3IDA"
                addSelf(link<KlasseResource>("1321123"))
                addTrinn(link<ArstrinnResource>("vg2"))
                addSkole(link<SkoleResource>("1579"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("664b9b6b-8b1e-439d-87b3-82e0fedbbc7c"))
                addSkolear(link<SkolearResource>("21-22"))
                addTermin(link<TerminResource>("H1"))
                addTermin(link<TerminResource>("H2"))
            }
        )
    }
}
