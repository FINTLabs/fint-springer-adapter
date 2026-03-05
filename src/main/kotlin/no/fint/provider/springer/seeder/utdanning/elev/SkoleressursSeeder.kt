package no.fint.provider.springer.seeder.utdanning.elev

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.administrasjon.personal.PersonalressursResource
import no.novari.fint.model.resource.felles.PersonResource
import no.novari.fint.model.resource.utdanning.elev.SkoleressursResource
import no.novari.fint.model.resource.utdanning.elev.UndervisningsforholdResource
import no.novari.fint.model.resource.utdanning.utdanningsprogram.SkoleResource
import org.springframework.stereotype.Service

@Service
class SkoleressursSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<SkoleressursResource>(seederRepository, SkoleressursResource::class.java) {

    fun generateEntitiesForTest(): List<SkoleressursResource> = generateEntities()


    override fun generateEntities(): List<SkoleressursResource> {
        return listOf(
            SkoleressursResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "SR_ANNA" }
                feidenavn = Identifikator().apply { identifikatorverdi = "anna.mørk@osloskole.no" }
                addSelf(link<SkoleressursResource>("anna.mørk@osloskole.no", "feidenavn"))
                addSelf(link<SkoleressursResource>("SR_ANNA"))
                addPersonalressurs(link<PersonalressursResource>("1111ANNA", "ansattnummer"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("UF_ANNA"))
            },
            SkoleressursResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "SR_NORA" }
                feidenavn = Identifikator().apply { identifikatorverdi = "nora.lys@osloskole.no" }
                addSelf(link<SkoleressursResource>("nora.lys@osloskole.no", "feidenavn"))
                addSelf(link<SkoleressursResource>("SR_NORA"))
                addPersonalressurs(link<PersonalressursResource>("9999NORA", "ansattnummer"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("UF_NORA"))
            },
            SkoleressursResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "ABCD213" }
                feidenavn = Identifikator().apply { identifikatorverdi = "gudrun.haraldseid@sundetvgs.haugfk.no" }
                addSelf(link<SkoleressursResource>("gudrun.haraldseid@sundetvgs.haugfk.no", "feidenavn"))
                addSelf(link<SkoleressursResource>("ABCD213"))
                addPersonalressurs(link<PersonalressursResource>("100003", "ansattnummer"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("L_100003_1"))
            },
            SkoleressursResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "9b0205c3-0cba-485a-ac32-dba70500fe55" }
                feidenavn = Identifikator().apply { identifikatorverdi = "dolly.duck@andeby.vgs.no" }
                addSelf(link<SkoleressursResource>("dolly.duck@andeby.vgs.no", "feidenavn"))
                addSelf(link<SkoleressursResource>("9b0205c3-0cba-485a-ac32-dba70500fe55"))
                addPersonalressurs(link<PersonalressursResource>("100006", "ansattnummer"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("7024200-93838-0-1-20211001000000"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("7024200-93838-0-1-20211001000001"))
                addPerson(link<PersonResource>("14018956789", "fodselsnummer"))
                addSkole(link<SkoleResource>("1579"))
                addSkole(link<SkoleResource>("XX1234"))
            },
            SkoleressursResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "d9a7860f-cb4b-4198-a9d9-840399fcddec" }
                feidenavn = Identifikator().apply { identifikatorverdi = "bestemor.duck@andeby.vgs.no" }
                addSelf(link<SkoleressursResource>("bestemor.duck@andeby.vgs.no", "feidenavn"))
                addSelf(link<SkoleressursResource>("d9a7860f-cb4b-4198-a9d9-840399fcddec"))
                addPersonalressurs(link<PersonalressursResource>("100007", "ansattnummer"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("664b9b6b-8b1e-439d-87b3-82e0fedbbc7c"))
                addPerson(link<PersonResource>("14013956789", "fodselsnummer"))
                addSkole(link<SkoleResource>("1579"))
            }
        )
    }
}
