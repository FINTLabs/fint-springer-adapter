package no.fint.provider.springer.seeder.felles

import net.datafaker.Faker
import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon
import no.novari.fint.model.felles.kompleksedatatyper.Personnavn
import no.novari.fint.model.resource.Link
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
import java.time.LocalDate
import java.time.ZoneId

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

    private fun fakeKontaktinformasjon(fnr: String, isPersonalressurs: Boolean): Kontaktinformasjon {
        val faker = fakerFor("$fnr:kontakt")
        val fornavn = faker.name().firstName().lowercase()
        val etternavn = faker.name().lastName().lowercase()
        val domain = if (isPersonalressurs) "oslo.vgs.no" else "elev.oslo.vgs.no"
        val mobil = (90000000 + Random(fnr.hashCode().toLong()).nextInt(9999999)).toString()

        return Kontaktinformasjon().apply {
            epostadresse = "$fornavn.$etternavn@$domain"
            mobiltelefonnummer = mobil
            if (isPersonalressurs) {
                nettsted = "https://$domain"
            }
        }
    }

    private fun fakeFodselsdato(fnr: String, isPersonalressurs: Boolean): Date {
        val random = Random((fnr + ":fodselsdato").hashCode().toLong())
        val (minAlder, maxAlder) = if (isPersonalressurs) {
            25 to 67
        } else {
            16 to 20
        }

        val alder = minAlder + random.nextInt(maxAlder - minAlder + 1)
        val dagarTilbake = random.nextInt(365)
        val dato = LocalDate.now()
            .minusYears(alder.toLong())
            .minusDays(dagarTilbake.toLong())

        return Date.from(dato.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    private fun fakeKjonnKode(fnr: String): String {
        return if (Random((fnr + ":kjonn").hashCode().toLong()).nextBoolean()) "1" else "2"
    }

    private fun buildPerson(
        fnr: String,
        personalressursAnsattnummer: String? = null,
        elevIdentifikatorverdi: String? = null,
        elevIdentifikatorNavn: String = "elevnummer"
    ): PersonResource {
        val isPersonalressurs = personalressursAnsattnummer != null

        return PersonResource().apply {
            fodselsnummer = Identifikator().apply { identifikatorverdi = fnr }
            fodselsdato = fakeFodselsdato(fnr, isPersonalressurs)
            navn = fakePersonnavn(fnr)
            kontaktinformasjon = fakeKontaktinformasjon(fnr, isPersonalressurs)
            postadresse = fakeAdresse(fnr, "postadresse")
            bostedsadresse = fakeAdresse(fnr, "bostedsadresse")

            personalressursAnsattnummer?.let {
                addPersonalressurs(link<PersonalressursResource>(it, "ansattnummer"))
            }

            elevIdentifikatorverdi?.let {
                addElev(link<ElevResource>(it, elevIdentifikatorNavn))
            }

            addMorsmal(link<SprakResource>("nn"))
            addMalform(link<SprakResource>("nb"))
            addKjonn(link<KjonnResource>(fakeKjonnKode(fnr)))
            addStatsborgerskap(link<LandkodeResource>("NO"))

            addSelf(link<PersonResource>(fnr, "fodselsnummer"))
        }
    }

    override fun generateEntities(): List<PersonResource> {
        val ansatte = mapOf(
            "100000" to "22057812345",
            "100001" to "01116423456",
            "100002" to "12077134567",
            "100003" to "16055445678",
            "100004" to "12018156789",
            "100005" to "13018156789",
            "100006" to "14018956789",
            "100007" to "14013956789"
        ).map { (ansattnummer, fnr) ->
            buildPerson(fnr = fnr, personalressursAnsattnummer = ansattnummer)
        }

        val elever = mapOf(
            "500001" to "14029923273",
            "500002" to "18010197461",
            "500003" to "11010159115",
            "500011" to "11011112345",
            "500012" to "12011112345",
            "500013" to "13011112345",
            "500014" to "14011112345"
        ).map { (elevnummer, fnr) ->
            val identifikatorNavn = if (elevnummer in setOf("500011", "500012", "500013", "500014")) "systemid" else "elevnummer"
            buildPerson(
                fnr = fnr,
                elevIdentifikatorverdi = elevnummer,
                elevIdentifikatorNavn = identifikatorNavn
            )
        }

        return ansatte + elever
    }
}