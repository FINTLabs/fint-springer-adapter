package no.fint.provider.springer.seeder.utdanning.utdanningsprogram

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon
import no.novari.fint.model.resource.administrasjon.organisasjon.OrganisasjonselementResource
import no.novari.fint.model.resource.felles.kompleksedatatyper.AdresseResource
import no.novari.fint.model.resource.utdanning.elev.ElevforholdResource
import no.novari.fint.model.resource.utdanning.elev.KlasseResource
import no.novari.fint.model.resource.utdanning.elev.KontaktlarergruppeResource
import no.novari.fint.model.resource.utdanning.elev.SkoleressursResource
import no.novari.fint.model.resource.utdanning.elev.UndervisningsforholdResource
import no.novari.fint.model.resource.utdanning.timeplan.FagResource
import no.novari.fint.model.resource.utdanning.utdanningsprogram.SkoleResource
import no.novari.fint.model.utdanning.timeplan.Undervisningsgruppe
import org.springframework.stereotype.Service

@Service
class SkoleSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<SkoleResource>(seederRepository, SkoleResource::class.java) {

    fun generateEntitiesForTest(): List<SkoleResource> = generateEntities()


    override fun generateEntities(): List<SkoleResource> {
        return listOf(
            SkoleResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "XX1234" }
                skolenummer = Identifikator().apply { identifikatorverdi = "123456" }
                organisasjonsnummer = Identifikator().apply { identifikatorverdi = "970123458" }
                navn = "Sundet VGS"
                organisasjonsnavn = "Sundet Videregående Skole"
                juridiskNavn = "Sundet Videregående Skole"
                domenenavn = "sundetvgs.haugfk.no"
                
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "post@haugfk.no"
                    mobiltelefonnummer = "47474747"
                    telefonnummer = "52525252"
                    nettsted = "http://www.haugfk.no"
                    sip = "kundesenter@haugfk.no"
                }
                postadresse = AdresseResource().apply {
                    adresselinje = listOf("PB 69")
                    postnummer = "5501"
                    poststed = "Haugesund"
                }
                forretningsadresse = AdresseResource().apply {
                    adresselinje = listOf("Marilyn Monroe 1")
                    postnummer = "5520"
                    poststed = "Haugesund"
                }
                
                addSelf(link<SkoleResource>("XX1234"))
                addOrganisasjon(link<OrganisasjonselementResource>("970123458", "organisasjonsnummer"))
                addFag(link<FagResource>("FA_NOR1Z41_2018"))
                addKlasse(link<KlasseResource>("BG_1STA-2018"))
            },
            SkoleResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1579" }
                skolenummer = Identifikator().apply { identifikatorverdi = "1579" }
                organisasjonsnummer = Identifikator().apply { identifikatorverdi = "313131313" }
                navn = "Andeby videregående skole"
                organisasjonsnavn = "Andeby videregående skole"
                juridiskNavn = "Andeby videregående skole"

                addSelf(link<SkoleResource>("1579"))
                addOrganisasjon(link<OrganisasjonselementResource>("313131313", "organisasjonsnummer"))
                addFag(link<FagResource>("NOR1264"))
                addKlasse(link<KlasseResource>("1321121"))
                addKlasse(link<KlasseResource>("1321122"))
                addKlasse(link<KlasseResource>("1321123"))
                addElevforhold(link<ElevforholdResource>("500011-1"))
                addElevforhold(link<ElevforholdResource>("500012-1"))
                addElevforhold(link<ElevforholdResource>("500013-1"))
                addElevforhold(link<ElevforholdResource>("500014-1"))
                addKontaktlarergruppe(link<KontaktlarergruppeResource>("1321122_1IDA"))
                addKontaktlarergruppe(link<KontaktlarergruppeResource>("1321122_2IDA"))
                addSkoleressurs(link<SkoleressursResource>("9b0205c3-0cba-485a-ac32-dba70500fe55"))
                addSkoleressurs(link<SkoleressursResource>("d9a7860f-cb4b-4198-a9d9-840399fcddec"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("7024200-93838-0-1-20211001000000"))
                addUndervisningsforhold(link<UndervisningsforholdResource>("664b9b6b-8b1e-439d-87b3-82e0fedbbc7c"))
                addUndervisningsgruppe(link<Undervisningsgruppe>("10128458_1IDA"))
                addUndervisningsgruppe(link<Undervisningsgruppe>("10128458_2IDA"))
            }
        )
    }
}
