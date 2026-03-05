package no.fint.provider.springer.seeder.administrasjon.organisasjon

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Adresse
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.organisasjon.OrganisasjonselementResource
import no.novari.fint.model.resource.administrasjon.personal.PersonalressursResource
import no.novari.fint.model.resource.felles.kompleksedatatyper.AdresseResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class OrganisasjonselementSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<OrganisasjonselementResource>(seederRepository, OrganisasjonselementResource::class.java) {

    fun generateEntitiesForTest(): List<OrganisasjonselementResource> = generateEntities()


    override fun generateEntities(): List<OrganisasjonselementResource> {
        return listOf(
            OrganisasjonselementResource().apply {
                organisasjonsId = Identifikator().apply { identifikatorverdi = "568000" }
                organisasjonsKode = Identifikator().apply {
                    identifikatorverdi = "SVG-HE"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1496604816000L)
                    }
                }
                navn = "Avdeling for helsefag"
                kortnavn = "Helsefag"
                gyldighetsperiode = Periode().apply {
                    start = Date(1496604816000L)
                }
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
                addSelf(link<OrganisasjonselementResource>("568000", "organisasjonsid"))
                addOverordnet(link<OrganisasjonselementResource>("560000", "organisasjonsid"))
                addLeder(link<PersonalressursResource>("100003", "ansattnummer"))
            },
            OrganisasjonselementResource().apply {
                navn = "Haugaland fylkeskommune"
                organisasjonsId = Identifikator().apply { identifikatorverdi = "550000" }
                organisasjonsnummer = Identifikator().apply { identifikatorverdi = "970123456" }
                gyldighetsperiode = Periode().apply {
                    start = Date(1496606016000L)
                    slutt = Date(1496606016000L)
                }
                addSelf(link<OrganisasjonselementResource>("550000", "organisasjonsid"))
            },
            OrganisasjonselementResource().apply {
                navn = "Avdeling for musikk, dans og drama"
                organisasjonsId = Identifikator().apply { identifikatorverdi = "569000" }
                gyldighetsperiode = Periode().apply {
                    start = Date(1496606016000L)
                }
                addSelf(link<OrganisasjonselementResource>("569000", "organisasjonsid"))
            },
            OrganisasjonselementResource().apply {
                navn = "Avdeling for drift og miljø"
                organisasjonsId = Identifikator().apply { identifikatorverdi = "561000" }
                gyldighetsperiode = Periode().apply {
                    start = Date(1496606016000L)
                }
                addSelf(link<OrganisasjonselementResource>("561000", "organisasjonsid"))
            },
            OrganisasjonselementResource().apply {
                navn = "Sundet videregående skole"
                organisasjonsId = Identifikator().apply { identifikatorverdi = "560000" }
                organisasjonsnummer = Identifikator().apply { identifikatorverdi = "970123458" }
                gyldighetsperiode = Periode().apply {
                    start = Date(1496606016000L)
                    slutt = Date(1496606016000L)
                }
                addSelf(link<OrganisasjonselementResource>("560000", "organisasjonsid"))
            },
            OrganisasjonselementResource().apply {
                navn = "Avdeling for fellesfag"
                organisasjonsId = Identifikator().apply { identifikatorverdi = "562000" }
                gyldighetsperiode = Periode().apply {
                    start = Date(1496606016000L)
                }
                addSelf(link<OrganisasjonselementResource>("562000", "organisasjonsid"))
            },
            OrganisasjonselementResource().apply {
                navn = "Opplæringsavdelingen"
                organisasjonsId = Identifikator().apply { identifikatorverdi = "550001" }
                organisasjonsnummer = Identifikator().apply { identifikatorverdi = "970123457" }
                gyldighetsperiode = Periode().apply {
                    start = Date(1496606016000L)
                    slutt = Date(1496606016000L)
                }
                addSelf(link<OrganisasjonselementResource>("550001", "organisasjonsid"))
            },
            OrganisasjonselementResource().apply {
                navn = "Andeby vgs."
                organisasjonsId = Identifikator().apply { identifikatorverdi = "570000" }
                organisasjonsnummer = Identifikator().apply { identifikatorverdi = "313131313" }
                gyldighetsperiode = Periode().apply {
                    start = Date(1606648908000L)
                }
                addSelf(link<OrganisasjonselementResource>("570000", "organisasjonsid"))
            }
        )
    }
}
