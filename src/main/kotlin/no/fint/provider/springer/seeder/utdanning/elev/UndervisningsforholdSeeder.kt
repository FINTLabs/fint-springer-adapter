package no.fint.provider.springer.seeder.utdanning.elev

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource
import no.novari.fint.model.resource.utdanning.elev.KlasseResource
import no.novari.fint.model.resource.utdanning.elev.KontaktlarergruppeResource
import no.novari.fint.model.resource.utdanning.elev.SkoleressursResource
import no.novari.fint.model.resource.utdanning.elev.UndervisningsforholdResource
import no.novari.fint.model.resource.utdanning.timeplan.UndervisningsgruppeResource
import no.novari.fint.model.resource.utdanning.utdanningsprogram.SkoleResource
import org.springframework.stereotype.Service

@Service
class UndervisningsforholdSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<UndervisningsforholdResource>(seederRepository, UndervisningsforholdResource::class.java) {

    fun generateEntitiesForTest(): List<UndervisningsforholdResource> = generateEntities()


    override fun generateEntities(): List<UndervisningsforholdResource> {
        return listOf(
            UndervisningsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "L_100003_1" }
                beskrivelse = "Faglærer norsk"
                addSelf(link<UndervisningsforholdResource>("L_100003_1"))
                addArbeidsforhold(link<ArbeidsforholdResource>("100003_1"))
                addSkole(link<SkoleResource>("123456", "skolenummer"))
                addSkoleressurs(link<SkoleressursResource>("ABCD213"))
                addKlasse(link<KlasseResource>("BG_1STA-2018"))
                addKontaktlarergruppe(link<KontaktlarergruppeResource>("KG_1STA-1"))
                addUndervisningsgruppe(link<UndervisningsgruppeResource>("UG_2018_1STA_NOR1Z41"))
            },
            UndervisningsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "7024200-93838-0-1-20211001000000" }
                beskrivelse = "Adjunkt m/tilleggsutd."
                addSelf(link<UndervisningsforholdResource>("7024200-93838-0-1-20211001000000"))
            },
            UndervisningsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "7024200-93838-0-1-20211001000001" }
                beskrivelse = "Adjunkt m/tilleggsutd."
                addSelf(link<UndervisningsforholdResource>("7024200-93838-0-1-20211001000001"))
            },
            UndervisningsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "664b9b6b-8b1e-439d-87b3-82e0fedbbc7c" }
                beskrivelse = "Adjunkt m/tilleggsutd."
                addSelf(link<UndervisningsforholdResource>("664b9b6b-8b1e-439d-87b3-82e0fedbbc7c"))
            },
            UndervisningsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "UF_NORA" }
                beskrivelse = "Undervisningsforhold Nora"
                addSelf(link<UndervisningsforholdResource>("UF_NORA"))
            },
            UndervisningsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "UF_ANNA" }
                beskrivelse = "Undervisningsforhold Anna"
                addSelf(link<UndervisningsforholdResource>("UF_ANNA"))
            }
        )
    }
}
