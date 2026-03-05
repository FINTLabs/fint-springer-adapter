package no.fint.provider.springer.seeder.utdanning.utdanningsprogram

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon
import no.novari.fint.model.resource.administrasjon.organisasjon.OrganisasjonselementResource
import no.novari.fint.model.resource.felles.kompleksedatatyper.AdresseResource
import no.novari.fint.model.resource.utdanning.timeplan.FagResource
import no.novari.fint.model.resource.utdanning.utdanningsprogram.SkoleResource
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
            }
        )
    }
}
