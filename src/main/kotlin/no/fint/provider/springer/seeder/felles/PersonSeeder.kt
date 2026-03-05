package no.fint.provider.springer.seeder.felles

import net.datafaker.Faker
import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon
import no.novari.fint.model.felles.kompleksedatatyper.Personnavn
import no.novari.fint.model.resource.administrasjon.personal.PersonalressursResource
import no.novari.fint.model.resource.felles.PersonResource
import no.novari.fint.model.resource.felles.kodeverk.iso.KjonnResource
import no.novari.fint.model.resource.felles.kodeverk.iso.LandkodeResource
import no.novari.fint.model.resource.felles.kodeverk.iso.SprakResource
import no.novari.fint.model.resource.felles.kompleksedatatyper.AdresseResource
import no.novari.fint.model.resource.utdanning.elev.ElevResource
import org.springframework.stereotype.Service
import java.util.Date
import java.util.Random

@Service
class PersonSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<PersonResource>(seederRepository, PersonResource::class.java) {

    // Public method for testing
    fun generateEntitiesForTest(): List<PersonResource> = generateEntities()


    private fun fakerFor(seedKey: String): Faker {
        val seed = seedKey.filter { it.isDigit() }.toLongOrNull() ?: seedKey.hashCode().toLong()
        return Faker(Random(seed))
    }

    private fun fakePersonnavn(fodselsnummer: String): Personnavn {
        val faker = fakerFor(fodselsnummer)
        return Personnavn().apply {
            fornavn = faker.name().firstName()
            etternavn = faker.name().lastName()
        }
    }

    private fun fakeAdresse(fodselsnummer: String, salt: String): AdresseResource {
        val faker = fakerFor("$fodselsnummer:$salt")
        return AdresseResource().apply {
            adresselinje = listOf(faker.address().streetAddress())
            postnummer = faker.address().zipCode()
            poststed = faker.address().city()
        }
    }

    override fun generateEntities(): List<PersonResource> {
        return listOf(
            // 100000 - Harald Sund (Personale)
            PersonResource().apply {
                val fnr = "22057812345"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(259737600000L) // 1978-05-22
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "harald.sund@haugnet.no"
                    mobiltelefonnummer = "41414141"
                    nettsted = "http://harald.sund.no"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addPersonalressurs(link<PersonalressursResource>("100000", "ansattnummer"))
                addMorsmal(link<SprakResource>("no"))
                addMalform(link<SprakResource>("nb"))
                addKjonn(link<KjonnResource>("1"))
                addStatsborgerskap(link<LandkodeResource>("NO"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            },

            // 100001 - Kari Haug (Personale)
            PersonResource().apply {
                val fnr = "01116423456"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(-184102400000L) // 1964-11-01
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "kari.haug@gmail.com"
                    mobiltelefonnummer = "42424242"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addPersonalressurs(link<PersonalressursResource>("100001", "ansattnummer"))
                addMorsmal(link<SprakResource>("no"))
                addMalform(link<SprakResource>("nb"))
                addKjonn(link<KjonnResource>("2"))
                addStatsborgerskap(link<LandkodeResource>("NO"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            },

            // 100002 - Kay Erik Gundersen (Personale)
            PersonResource().apply {
                val fnr = "12077134567"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(475603200000L) // 1971-07-12
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "kayerik.gundersen@online.no"
                    mobiltelefonnummer = "43434343"
                    nettsted = "http://online.no/~kguners"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addPersonalressurs(link<PersonalressursResource>("100002", "ansattnummer"))
                addMorsmal(link<SprakResource>("no"))
                addMalform(link<SprakResource>("nb"))
                addKjonn(link<KjonnResource>("1"))
                addStatsborgerskap(link<LandkodeResource>("NO"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            },

            // 100003 - Gudrun Haraldseid (Personale)
            PersonResource().apply {
                val fnr = "16055445678"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(-202636800000L) // 1953-05-16
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "gudrun53@hotmail.com"
                    mobiltelefonnummer = "44444444"
                    nettsted = "http://grudrun.blog.com"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addPersonalressurs(link<PersonalressursResource>("100003", "ansattnummer"))
                addMorsmal(link<SprakResource>("no"))
                addMalform(link<SprakResource>("nb"))
                addKjonn(link<KjonnResource>("2"))
                addStatsborgerskap(link<LandkodeResource>("NO"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            },

            // 100004 - Cathrine Tollaksen (Personale)
            PersonResource().apply {
                val fnr = "12018156789"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(414201600000L) // 1981-01-12
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "cathrine.tollaksen@gmail.com"
                    mobiltelefonnummer = "45454545"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addPersonalressurs(link<PersonalressursResource>("100004", "ansattnummer"))
                addMorsmal(link<SprakResource>("no"))
                addMalform(link<SprakResource>("nn"))
                addKjonn(link<KjonnResource>("2"))
                addStatsborgerskap(link<LandkodeResource>("NO"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            },

            // 100005 - Arne Marius Fjeld (Personale)
            PersonResource().apply {
                val fnr = "13018156789"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(414201600000L) // 1981-01-12
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "arne.fjeld@oslo.vgs.no"
                    mobiltelefonnummer = "33333333"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addPersonalressurs(link<PersonalressursResource>("100005", "ansattnummer"))
                addMorsmal(link<SprakResource>("no"))
                addMalform(link<SprakResource>("nb"))
                addKjonn(link<KjonnResource>("1"))
                addStatsborgerskap(link<LandkodeResource>("NO"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            },

            // 100006 - Siri Solberg (Personale)
            PersonResource().apply {
                val fnr = "14018956789"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(414201600000L) // 1981-01-12
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "siri.solberg@oslo.vgs.no"
                    mobiltelefonnummer = "87878787"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addPersonalressurs(link<PersonalressursResource>("100006", "ansattnummer"))
                addMorsmal(link<SprakResource>("no"))
                addMalform(link<SprakResource>("nb"))
                addKjonn(link<KjonnResource>("2"))
                addStatsborgerskap(link<LandkodeResource>("NO"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            },

            // 100007 - Ingrid Hansen (Personale)
            PersonResource().apply {
                val fnr = "14013956789"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(414201600000L) // 1981-01-12
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "ingrid.hansen@oslo.vgs.no"
                    mobiltelefonnummer = "87878787"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addPersonalressurs(link<PersonalressursResource>("100007", "ansattnummer"))
                addMorsmal(link<SprakResource>("no"))
                addMalform(link<SprakResource>("nb"))
                addKjonn(link<KjonnResource>("2"))
                addStatsborgerskap(link<LandkodeResource>("NO"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            },

            // 500002 - Leona Hansen (Elev)
            PersonResource().apply {
                val fnr = "18010197461"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(979603200000L) // 2001-01-18
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "LeonaHansen@dayrep.com"
                    mobiltelefonnummer = "41109815"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addElev(link<ElevResource>("500002", "elevnummer"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            },

            // 500001 - Rose Støa (Elev)
            PersonResource().apply {
                val fnr = "14029923273"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(918422400000L) // 1999-02-14
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "RoseSta@jourrapide.com"
                    mobiltelefonnummer = "48213268"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addElev(link<ElevResource>("500001", "elevnummer"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            },

            // 500003 - Gustav Hofseth (Elev)
            PersonResource().apply {
                val fnr = "11010159115"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(979516800000L) // 2001-01-11
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "GustavHofseth@rhyta.com"
                    mobiltelefonnummer = "94259236"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addElev(link<ElevResource>("500003", "elevnummer"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            },

            // 500011 - Ole Andersen (Elev)
            PersonResource().apply {
                val fnr = "11011112345"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(1294684800000L) // 2011-01-11
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "ole.andersen@oslo.vgs.no"
                    mobiltelefonnummer = "99911111"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addElev(link<ElevResource>("500011", "systemid"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            },

            // 500012 - Per Olsen (Elev)
            PersonResource().apply {
                val fnr = "12011112345"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(1294771200000L) // 2011-01-12
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "per.olsen@oslo.vgs.no"
                    mobiltelefonnummer = "99922222"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addElev(link<ElevResource>("500012", "systemid"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            },

            // 500013 - Henrik Strand (Elev)
            PersonResource().apply {
                val fnr = "13011112345"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(1294771200000L) // 2011-01-12
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "henrik.strand@oslo.vgs.no"
                    mobiltelefonnummer = "99933333"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addElev(link<ElevResource>("500013", "systemid"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            },

            // 500014 - Maja Nilsen (Elev)
            PersonResource().apply {
                val fnr = "14011112345"
                fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
                fodselsdato = Date(1294771200000L) // 2011-01-12
                navn = fakePersonnavn(fnr)
                kontaktinformasjon = Kontaktinformasjon().apply {
                    epostadresse = "maja.nilsen@oslo.vgs.no"
                    mobiltelefonnummer = "99944444"
                }
                postadresse = fakeAdresse(fnr, "postadresse")
                bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

                addElev(link<ElevResource>("500014", "systemid"))
                addSelf(link<PersonResource>(fnr, "fodselsnummer"))
            })
    }
}