package no.fint.provider.springer.seeder.administrasjon.personal

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.kodeverk.*
import no.novari.fint.model.resource.administrasjon.organisasjon.OrganisasjonselementResource
import no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource
import no.novari.fint.model.resource.administrasjon.personal.FastlonnResource
import no.novari.fint.model.resource.administrasjon.personal.FravarResource
import no.novari.fint.model.resource.administrasjon.personal.PersonalressursResource
import no.novari.fint.model.resource.administrasjon.personal.VariabellonnResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class ArbeidsforholdSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<ArbeidsforholdResource>(seederRepository, ArbeidsforholdResource::class.java) {

    fun generateEntitiesForTest(): List<ArbeidsforholdResource> = generateEntities()


    override fun generateEntities(): List<ArbeidsforholdResource> {
        return listOf(
            // 100000_1 - Fylkesrådmann
            ArbeidsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "100000_1" }
                ansettelsesprosent = 10000
                gyldighetsperiode = Periode().apply {
                    start = Date(1496605416000L) // 2017-06-04T19:53:36Z
                    slutt = Date(4070908800000L)  // 2099-01-01T00:00:00Z
                }
                hovedstilling = true
                lonnsprosent = 10000
                stillingsnummer = "1"
                stillingstittel = "Fylkesrådmann"
                tilstedeprosent = 10000
                arslonn = 50300000
                
                addSelf(link<ArbeidsforholdResource>("100000_1"))
                addPersonalressurs(link<PersonalressursResource>("100000", "ansattnummer"))
                addArbeidssted(link<OrganisasjonselementResource>("550000", "organisasjonsid"))
                addAnsvar(link<AnsvarResource>("416607"))
                addFunksjon(link<FunksjonResource>("400573"))
                addArbeidsforholdstype(link<ArbeidsforholdstypeResource>("401241"))
                addStillingskode(link<StillingskodeResource>("411814"))
                addTimerPerUke(link<UketimetallResource>("394634"))
            },
            
            // 100001_1 - Utdanningssjef
            ArbeidsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "100001_1" }
                ansettelsesprosent = 10000
                gyldighetsperiode = Periode().apply {
                    start = Date(1496605416000L) // 2017-06-04T19:53:36Z
                    slutt = Date(4070908800000L)  // 2099-01-01T00:00:00Z
                }
                hovedstilling = true
                lonnsprosent = 10000
                stillingsnummer = "1"
                stillingstittel = "Utdanningssjef"
                tilstedeprosent = 10000
                arslonn = 48000000
                
                addSelf(link<ArbeidsforholdResource>("100001_1"))
                addPersonalressurs(link<PersonalressursResource>("100001", "ansattnummer"))
                addArbeidssted(link<OrganisasjonselementResource>("550001", "organisasjonsid"))
                addAnsvar(link<AnsvarResource>("416607"))
                addFunksjon(link<FunksjonResource>("400573"))
                addArbeidsforholdstype(link<ArbeidsforholdstypeResource>("401241"))
                addStillingskode(link<StillingskodeResource>("898830"))
                addTimerPerUke(link<UketimetallResource>("394634"))
            },
            
            // 100002_1 - Rektor (med fravær)
            ArbeidsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "100002_1" }
                ansettelsesprosent = 10000
                gyldighetsperiode = Periode().apply {
                    start = Date(1496605416000L) // 2017-06-04T19:53:36Z
                    slutt = Date(4070908800000L)  // 2099-01-01T00:00:00Z
                }
                hovedstilling = true
                lonnsprosent = 10000
                stillingsnummer = "1"
                stillingstittel = "Rektor"
                tilstedeprosent = 10000
                arslonn = 62300000
                
                addSelf(link<ArbeidsforholdResource>("100002_1"))
                addPersonalressurs(link<PersonalressursResource>("100002", "ansattnummer"))
                addArbeidssted(link<OrganisasjonselementResource>("560000", "organisasjonsid"))
                addAnsvar(link<AnsvarResource>("437640"))
                addFunksjon(link<FunksjonResource>("400602"))
                addArbeidsforholdstype(link<ArbeidsforholdstypeResource>("401245"))
                addStillingskode(link<StillingskodeResource>("400552"))
                addTimerPerUke(link<UketimetallResource>("394634"))
                addFravar(link<FravarResource>("F75J843W7J029"))
            },
            
            // 100003_1 - Adjunkt (med lønn)
            ArbeidsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "100003_1" }
                ansettelsesprosent = 10000
                gyldighetsperiode = Periode().apply {
                    start = Date(1496605416000L) // 2017-06-04T19:53:36Z
                    slutt = Date(4070908800000L)  // 2099-01-01T00:00:00Z
                }
                hovedstilling = true
                lonnsprosent = 10000
                stillingsnummer = "1"
                stillingstittel = "Adjunkt"
                tilstedeprosent = 10000
                arslonn = 47300000
                
                addSelf(link<ArbeidsforholdResource>("100003_1"))
                addPersonalressurs(link<PersonalressursResource>("100003", "ansattnummer"))
                addArbeidssted(link<OrganisasjonselementResource>("562000", "organisasjonsid"))
                addAnsvar(link<AnsvarResource>("437639"))
                addFunksjon(link<FunksjonResource>("400602"))
                addArbeidsforholdstype(link<ArbeidsforholdstypeResource>("401241"))
                addStillingskode(link<StillingskodeResource>("380713"))
                addTimerPerUke(link<UketimetallResource>("394634"))
                addFastlonn(link<FastlonnResource>("FL_DFRTIYUGO"))
                addVariabellonn(link<VariabellonnResource>("VL_JIOPTPUSO"))

            },
            
            // 100004_1 - Fagarbeider (hovedstilling)
            ArbeidsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "100004_1" }
                ansettelsesprosent = 6000
                gyldighetsperiode = Periode().apply {
                    start = Date(1496605416000L) // 2017-06-04T19:53:36Z
                    slutt = Date(4070908800000L)  // 2099-01-01T00:00:00Z
                }
                hovedstilling = true
                lonnsprosent = 6000
                stillingsnummer = "1"
                stillingstittel = "Fagarbeider"
                tilstedeprosent = 2000
                arslonn = 57100000
                
                addSelf(link<ArbeidsforholdResource>("100004_1"))
                addPersonalressurs(link<PersonalressursResource>("100004", "ansattnummer"))
                addArbeidssted(link<OrganisasjonselementResource>("560000", "organisasjonsid"))
                addAnsvar(link<AnsvarResource>("437640"))
                addFunksjon(link<FunksjonResource>("400602"))
                addArbeidsforholdstype(link<ArbeidsforholdstypeResource>("401241"))
                addStillingskode(link<StillingskodeResource>("453496"))
                addTimerPerUke(link<UketimetallResource>("394634"))
            },
            
            // 100004_2 - Fagarbeider (bistilling)
            ArbeidsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "100004_2" }
                ansettelsesprosent = 4000
                gyldighetsperiode = Periode().apply {
                    start = Date(1496605416000L) // 2017-06-04T19:53:36Z
                    slutt = Date(4070908800000L)  // 2099-01-01T00:00:00Z
                }
                hovedstilling = false
                lonnsprosent = 4000
                stillingsnummer = "2"
                stillingstittel = "Fagarbeider"
                tilstedeprosent = 4000
                arslonn = 57100000
                
                addSelf(link<ArbeidsforholdResource>("100004_2"))
                addPersonalressurs(link<PersonalressursResource>("100004", "ansattnummer"))
                addArbeidssted(link<OrganisasjonselementResource>("568000", "organisasjonsid"))
                addAnsvar(link<AnsvarResource>("437640"))
                addFunksjon(link<FunksjonResource>("400602"))
                addArbeidsforholdstype(link<ArbeidsforholdstypeResource>("401241"))
                addStillingskode(link<StillingskodeResource>("453496"))
                addTimerPerUke(link<UketimetallResource>("394634"))
            },
            
            // 100005_1 - Rektor
            ArbeidsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "100005_1" }
                ansettelsesprosent = 10000
                gyldighetsperiode = Periode().apply {
                    start = Date(1622835216000L) // 2021-06-04T19:53:36Z
                    slutt = Date(4070908800000L)  // 2099-01-01T00:00:00Z
                }
                hovedstilling = true
                lonnsprosent = 10000
                stillingsnummer = "1"
                stillingstittel = "Rektor"
                tilstedeprosent = 10000
                arslonn = 90000000
                
                addSelf(link<ArbeidsforholdResource>("100005_1"))
                addPersonalressurs(link<PersonalressursResource>("100005", "ansattnummer"))
                addArbeidssted(link<OrganisasjonselementResource>("570000", "organisasjonsid"))
                addAnsvar(link<AnsvarResource>("33044"))
                addFunksjon(link<FunksjonResource>("400602"))
                addArbeidsforholdstype(link<ArbeidsforholdstypeResource>("401241"))
                addStillingskode(link<StillingskodeResource>("400552"))
                addTimerPerUke(link<UketimetallResource>("394634"))
            },
            
            // 100006_1 - Adjunkt med opprykk (20%)
            ArbeidsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "100006_1" }
                ansettelsesprosent = 2000
                gyldighetsperiode = Periode().apply {
                    start = Date(1622835216000L) // 2021-06-04T19:53:36Z
                    slutt = Date(4070908800000L)  // 2099-01-01T00:00:00Z
                }
                hovedstilling = false
                lonnsprosent = 2000
                stillingsnummer = "1"
                stillingstittel = "Adjunkt med opprykk"
                tilstedeprosent = 2000
                arslonn = 64000000
                
                addSelf(link<ArbeidsforholdResource>("100006_1"))
                addPersonalressurs(link<PersonalressursResource>("100006", "ansattnummer"))
                addArbeidssted(link<OrganisasjonselementResource>("562000", "organisasjonsid"))
                addAnsvar(link<AnsvarResource>("33044"))
                addFunksjon(link<FunksjonResource>("400602"))
                addArbeidsforholdstype(link<ArbeidsforholdstypeResource>("401241"))
                addStillingskode(link<StillingskodeResource>("22407999"))
                addTimerPerUke(link<UketimetallResource>("394634"))
            },
            
            // 100006_2 - Adjunkt med opprykk (80%)
            ArbeidsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "100006_2" }
                ansettelsesprosent = 8000
                gyldighetsperiode = Periode().apply {
                    start = Date(1622835216000L) // 2021-06-04T19:53:36Z
                    slutt = Date(4070908800000L)  // 2099-01-01T00:00:00Z
                }
                hovedstilling = false
                lonnsprosent = 8000
                stillingsnummer = "1"
                stillingstittel = "Adjunkt med opprykk"
                tilstedeprosent = 8000
                arslonn = 64000000
                
                addSelf(link<ArbeidsforholdResource>("100006_2"))
                addPersonalressurs(link<PersonalressursResource>("100006", "ansattnummer"))
                addArbeidssted(link<OrganisasjonselementResource>("570000", "organisasjonsid"))
                addAnsvar(link<AnsvarResource>("33044"))
                addFunksjon(link<FunksjonResource>("400602"))
                addArbeidsforholdstype(link<ArbeidsforholdstypeResource>("401241"))
                addStillingskode(link<StillingskodeResource>("22407999"))
                addTimerPerUke(link<UketimetallResource>("394634"))
            },
            
            // 100007_1 - Adjunkt med opprykk
            ArbeidsforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "100007_1" }
                ansettelsesprosent = 10000
                gyldighetsperiode = Periode().apply {
                    start = Date(1622835216000L) // 2021-06-04T19:53:36Z
                    slutt = Date(4070908800000L)  // 2099-01-01T00:00:00Z
                }
                hovedstilling = false
                lonnsprosent = 10000
                stillingsnummer = "1"
                stillingstittel = "Adjunkt med opprykk"
                tilstedeprosent = 8000
                arslonn = 60000000
                
                addSelf(link<ArbeidsforholdResource>("100007_1"))
                addPersonalressurs(link<PersonalressursResource>("100007", "ansattnummer"))
                addArbeidssted(link<OrganisasjonselementResource>("570000", "organisasjonsid"))
                addAnsvar(link<AnsvarResource>("33044"))
                addFunksjon(link<FunksjonResource>("400602"))
                addArbeidsforholdstype(link<ArbeidsforholdstypeResource>("401241"))
                addStillingskode(link<StillingskodeResource>("22407999"))
                addTimerPerUke(link<UketimetallResource>("394634"))
            }
        )
    }
}
