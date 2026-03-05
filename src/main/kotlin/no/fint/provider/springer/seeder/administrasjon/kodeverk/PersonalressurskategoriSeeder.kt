package no.fint.provider.springer.seeder.administrasjon.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.kodeverk.PersonalressurskategoriResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class PersonalressurskategoriSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<PersonalressurskategoriResource>(seederRepository, PersonalressurskategoriResource::class.java) {

    fun generateEntitiesForTest(): List<PersonalressurskategoriResource> = generateEntities()

    override fun generateEntities(): List<PersonalressurskategoriResource> {
        return listOf(
            PersonalressurskategoriResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "425894" }
                kode = "6"
                navn = "Honorar/godtgjørelse"
                gyldighetsperiode = Periode().apply {
                    start = Date(1402930943000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<PersonalressurskategoriResource>("425894"))
            },
            PersonalressurskategoriResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "382752" }
                kode = "1"
                navn = "Fast"
                gyldighetsperiode = Periode().apply {
                    start = Date(1402930943000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<PersonalressurskategoriResource>("382752"))
            },
            PersonalressurskategoriResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "382755" }
                kode = "4"
                navn = "Sluttet"
                gyldighetsperiode = Periode().apply {
                    start = Date(1229441070000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<PersonalressurskategoriResource>("382755"))
            },
            PersonalressurskategoriResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "382753" }
                kode = "2"
                navn = "Oppdragstaker"
                gyldighetsperiode = Periode().apply {
                    start = Date(1402930943000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<PersonalressurskategoriResource>("382753"))
            },
            PersonalressurskategoriResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "436123" }
                kode = "8"
                navn = "Selvstendig næringsdrivende"
                gyldighetsperiode = Periode().apply {
                    start = Date(1458575474000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<PersonalressurskategoriResource>("436123"))
            },
            PersonalressurskategoriResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "382754" }
                kode = "3"
                navn = "Leverandørrepersentant"
                gyldighetsperiode = Periode().apply {
                    start = Date(1366842766000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<PersonalressurskategoriResource>("382754"))
            },
            PersonalressurskategoriResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "382756" }
                kode = "5"
                navn = "Nyansatt"
                gyldighetsperiode = Periode().apply {
                    start = Date(1420575566000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<PersonalressurskategoriResource>("382756"))
            },
            PersonalressurskategoriResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "450782" }
                kode = "10"
                navn = "Midlertidig"
                gyldighetsperiode = Periode().apply {
                    start = Date(1414588520000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<PersonalressurskategoriResource>("450782"))
            },
            PersonalressurskategoriResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "436122" }
                kode = "7"
                navn = "Politiker"
                gyldighetsperiode = Periode().apply {
                    start = Date(1402930943000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<PersonalressurskategoriResource>("436122"))
            }
        )
    }
}
