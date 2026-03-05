package no.fint.provider.springer.seeder.administrasjon.personal

import no.fint.provider.springer.seeder.AbstractSeeder
import no.novari.fint.model.resource.Link
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.administrasjon.personal.PersonalressursResource
import no.novari.fint.model.resource.administrasjon.kodeverk.PersonalressurskategoriResource
import no.novari.fint.model.resource.felles.PersonResource
import no.novari.fint.model.resource.utdanning.elev.SkoleressursResource
import no.fint.provider.springer.storage.SeederRepository
import org.springframework.stereotype.Service
import java.util.Date

@Service
class PersonalressursSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<PersonalressursResource>(seederRepository, PersonalressursResource::class.java) {
    
    // Public method for testing
    fun generateEntitiesForTest(): List<PersonalressursResource> = generateEntities()
    
    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<PersonalressursResource> {
        return listOf(
            // 100000 - Harald Sund
            PersonalressursResource().apply {
                ansattnummer = Identifikator().apply { identifikatorverdi = "100000" }
                ansettelsesperiode = Periode().apply {
                    start = Date(1275628800000L) // 2010-06-04
                    slutt = Date(4102444800000L)  // 2099-12-31
                }
                brukernavn = Identifikator().apply {
                    identifikatorverdi = "hsund"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1275628800000L) // 2010-06-04
                        slutt = Date(4102444800000L)  // 2099-12-31
                    }
                }
                kontaktinformasjon = no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon().apply {
                    epostadresse = "harald.sund@rogfk.no"
                    mobiltelefonnummer = "41414141"
                    nettsted = "http://www.rogfk.no"
                    sip = "harald.sund@rogfk.no"
                    telefonnummer = "51516278"
                }
                systemId = Identifikator().apply { identifikatorverdi = "8AC2A702-3E88-4D83-B37E-9DBFB79DD82C" }
                
                addPerson(link<PersonResource>("22057812345", "fodselsnummer"))
                addPersonalressurskategori(link<PersonalressurskategoriResource>("382752"))
                addArbeidsforhold(link<no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource>("100000_1"))
                addFullmakt(link<no.novari.fint.model.resource.administrasjon.fullmakt.FullmaktResource>("ABC123"))
                addFullmakt(link<no.novari.fint.model.resource.administrasjon.fullmakt.FullmaktResource>("XYZ456"))
                addSelf(link<PersonalressursResource>("100000", "ansattnummer"))
            },
            
            // 100001 - Kari Haug
            PersonalressursResource().apply {
                ansattnummer = Identifikator().apply { identifikatorverdi = "100001" }
                ansettelsesperiode = Periode().apply {
                    start = Date(1275628800000L) // 2010-06-04
                    slutt = Date(4102444800000L)  // 2099-12-31
                }
                brukernavn = Identifikator().apply {
                    identifikatorverdi = "khaug"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1275628800000L) // 2010-06-04
                        slutt = Date(4102444800000L)  // 2099-12-31
                    }
                }
                kontaktinformasjon = no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon().apply {
                    epostadresse = "kari.haug@rogfk.no"
                    mobiltelefonnummer = "42424242"
                    nettsted = "http://www.rogfk.no"
                    sip = "kari.haug@rogfk.no"
                    telefonnummer = "51516062"
                }
                systemId = Identifikator().apply { identifikatorverdi = "D93DE741-FD6E-4DD4-BF5C-7398CECF3712" }
                
                addPerson(link<PersonResource>("01116423456", "fodselsnummer"))
                addPersonalressurskategori(link<PersonalressurskategoriResource>("382752"))
                addArbeidsforhold(link<no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource>("100001_1"))
                addSelf(link<PersonalressursResource>("100001", "ansattnummer"))
            },
            
            // 100002 - Kay Erik Gundersen
            PersonalressursResource().apply {
                ansattnummer = Identifikator().apply { identifikatorverdi = "100002" }
                ansettelsesperiode = Periode().apply {
                    start = Date(1275628800000L) // 2010-06-04
                    slutt = Date(4102444800000L)  // 2099-12-31
                }
                brukernavn = Identifikator().apply {
                    identifikatorverdi = "kegudnersen"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1275628800000L) // 2010-06-04
                        slutt = Date(4102444800000L)  // 2099-12-31
                    }
                }
                kontaktinformasjon = no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon().apply {
                    epostadresse = "kay.erik.gundersen@rogfk.no"
                    mobiltelefonnummer = "43434343"
                    nettsted = "http://www.rogfk.no"
                    sip = "kay.erik.gundersen@rogfk.no"
                }
                systemId = Identifikator().apply { identifikatorverdi = "0385AC48-342D-430B-97C4-2059FD7213D7" }
                
                addPerson(link<PersonResource>("12077134567", "fodselsnummer"))
                addPersonalressurskategori(link<PersonalressurskategoriResource>("450782"))
                addArbeidsforhold(link<no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource>("100002_1"))
                addSelf(link<PersonalressursResource>("100002", "ansattnummer"))
            },
            
            // 100003 - Gudrun Haraldseid
            PersonalressursResource().apply {
                ansattnummer = Identifikator().apply { identifikatorverdi = "100003" }
                ansettelsesperiode = Periode().apply {
                    start = Date(1275628800000L) // 2010-06-04
                    slutt = Date(4102444800000L)  // 2099-12-31
                }
                brukernavn = Identifikator().apply {
                    identifikatorverdi = "gharaldseid"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1275628800000L) // 2010-06-04
                        slutt = Date(4102444800000L)  // 2099-12-31
                    }
                }
                kontaktinformasjon = no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon().apply {
                    epostadresse = "gudrun.haraldseid@rogfk.no"
                    mobiltelefonnummer = "44444444"
                    nettsted = "http://www.rogfk.no"
                    sip = "gudrun.haraldseid@rogfk.no"
                }
                systemId = Identifikator().apply { identifikatorverdi = "B3B2A996-BD1E-4058-9B69-94B0FD9F75C5" }
                
                addPerson(link<PersonResource>("16055445678", "fodselsnummer"))
                addPersonalressurskategori(link<PersonalressurskategoriResource>("382752"))
                addArbeidsforhold(link<no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource>("100003_1"))
                addSkoleressurs(link<SkoleressursResource>("ABCD213"))
                addSelf(link<PersonalressursResource>("100003", "ansattnummer"))
            },
            
            // 100004 - Cathrine Tollaksen
            PersonalressursResource().apply {
                ansattnummer = Identifikator().apply { identifikatorverdi = "100004" }
                ansettelsesperiode = Periode().apply {
                    start = Date(1275628800000L) // 2010-06-04
                    slutt = Date(4102444800000L)  // 2099-12-31
                }
                brukernavn = Identifikator().apply {
                    identifikatorverdi = "ctollaksen"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1275628800000L) // 2010-06-04
                        slutt = Date(4102444800000L)  // 2099-12-31
                    }
                }
                kontaktinformasjon = no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon().apply {
                    epostadresse = "cathrine.tollaksen@rogfk.no"
                    mobiltelefonnummer = "45454545"
                    nettsted = "http://www.rogfk.no"
                    sip = "cathrine.tollaksen@rogfk.no"
                }
                systemId = Identifikator().apply { identifikatorverdi = "70F3F121-C7CF-42D8-8311-9A5FF0AB090B" }
                
                addPerson(link<PersonResource>("12018156789", "fodselsnummer"))
                addPersonalressurskategori(link<PersonalressurskategoriResource>("382752"))
                addArbeidsforhold(link<no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource>("100004_1"))
                addArbeidsforhold(link<no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource>("100004_2"))
                addSelf(link<PersonalressursResource>("100004", "ansattnummer"))
            },
            
            // 100005 - Anton Duck
            PersonalressursResource().apply {
                ansattnummer = Identifikator().apply { identifikatorverdi = "100005" }
                ansettelsesperiode = Periode().apply {
                    start = Date(1622784000000L) // 2021-06-04
                    slutt = Date(4102444800000L)  // 2099-12-31
                }
                brukernavn = Identifikator().apply {
                    identifikatorverdi = "anton"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1622784000000L) // 2021-06-04
                        slutt = Date(4102444800000L)  // 2099-12-31
                    }
                }
                kontaktinformasjon = no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon().apply {
                    epostadresse = "anton.duck@andeby.vgs.no"
                    mobiltelefonnummer = "45454545"
                    nettsted = "http://andeby.vgs.no"
                    sip = "anton.duck@andeby.vgs.no"
                }
                systemId = Identifikator().apply { identifikatorverdi = "100005" }
                
                addPerson(link<PersonResource>("13018156789", "fodselsnummer"))
                addPersonalressurskategori(link<PersonalressurskategoriResource>("382752"))
                addArbeidsforhold(link<no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource>("100005_1"))
                addSelf(link<PersonalressursResource>("100005", "systemid"))
                addSelf(link<PersonalressursResource>("100005", "ansattnummer"))
            },
            
            // 100006 - Dolly Duck
            PersonalressursResource().apply {
                ansattnummer = Identifikator().apply { identifikatorverdi = "100006" }
                ansettelsesperiode = Periode().apply {
                    start = Date(1622784000000L) // 2021-06-04
                    slutt = Date(4102444800000L)  // 2099-12-31
                }
                brukernavn = Identifikator().apply {
                    identifikatorverdi = "dolly"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1622784000000L) // 2021-06-04
                        slutt = Date(4102444800000L)  // 2099-12-31
                    }
                }
                kontaktinformasjon = no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon().apply {
                    epostadresse = "dolly.duck@andeby.vgs.no"
                    mobiltelefonnummer = "87878787"
                    nettsted = "http://andeby.vgs.no"
                    sip = "dolly.duck@andeby.vgs.no"
                }
                systemId = Identifikator().apply { identifikatorverdi = "100006" }
                
                addPerson(link<PersonResource>("14018956789", "fodselsnummer"))
                addPersonalressurskategori(link<PersonalressurskategoriResource>("382752"))
                addArbeidsforhold(link<no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource>("100006_1"))
                addArbeidsforhold(link<no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource>("100006_2"))
                addSkoleressurs(link<SkoleressursResource>("9b0205c3-0cba-485a-ac32-dba70500fe55"))
                addSelf(link<PersonalressursResource>("100006", "systemid"))
                addSelf(link<PersonalressursResource>("100006", "ansattnummer"))
            },
            
            // 100007 - Bestemor Duck
            PersonalressursResource().apply {
                ansattnummer = Identifikator().apply { identifikatorverdi = "100007" }
                ansettelsesperiode = Periode().apply {
                    start = Date(1622784000000L) // 2021-06-04
                    slutt = Date(4102444800000L)  // 2099-12-31
                }
                brukernavn = Identifikator().apply {
                    identifikatorverdi = "bestemor"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1622784000000L) // 2021-06-04
                        slutt = Date(4102444800000L)  // 2099-12-31
                    }
                }
                kontaktinformasjon = no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon().apply {
                    epostadresse = "bestemor.duck@andeby.vgs.no"
                    mobiltelefonnummer = "87878787"
                    nettsted = "https://andeby.vgs.no"
                    sip = "bestemor.duck@andeby.vgs.no"
                }
                systemId = Identifikator().apply { identifikatorverdi = "100007" }
                
                addPerson(link<PersonResource>("14013956789", "fodselsnummer"))
                addPersonalressurskategori(link<PersonalressurskategoriResource>("382752"))
                addArbeidsforhold(link<no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource>("100007_1"))
                addSkoleressurs(link<SkoleressursResource>("d9a7860f-cb4b-4198-a9d9-840399fcddec"))
                addSelf(link<PersonalressursResource>("100007", "systemid"))
                addSelf(link<PersonalressursResource>("100007", "ansattnummer"))
            }
        )
    }
}
