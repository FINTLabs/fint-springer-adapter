package no.fint.provider.springer.seeder.administrasjon.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.kodeverk.ArbeidsforholdstypeResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class ArbeidsforholdstypeSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<ArbeidsforholdstypeResource>(seederRepository, ArbeidsforholdstypeResource::class.java) {

    fun generateEntitiesForTest(): List<ArbeidsforholdstypeResource> = generateEntities()

    override fun generateEntities(): List<ArbeidsforholdstypeResource> {
        return listOf(
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401261" }
                kode = "U"
                navn = "Beredskap Brann"
                gyldighetsperiode = Periode().apply {
                    start = Date(1405940912000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401261"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401242" }
                kode = "B"
                navn = "Timelønnet"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401242"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401241" }
                kode = "A"
                navn = "Fast"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401241"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401258" }
                kode = "R"
                navn = "Vikar - foreldreperm. 100%"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401258"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401256" }
                kode = "P"
                navn = "Vikar - ferie"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401256"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401246" }
                kode = "F"
                navn = "Engasjement < 6 mnd"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401246"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401251" }
                kode = "K"
                navn = "Foreldreperm. 80%"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401251"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "436560" }
                kode = "W"
                navn = "Eksamensvakt / Sensor"
                gyldighetsperiode = Periode().apply {
                    start = Date(1405941242000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("436560"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401254" }
                kode = "N"
                navn = "Permisjon ulønnet"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401254"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401253" }
                kode = "M"
                navn = "Permisjon lønnet"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401253"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401243" }
                kode = "C"
                navn = "Lærlinger"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401243"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401244" }
                kode = "D"
                navn = "Folkevalgt/politiker"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401244"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "403753" }
                kode = "V"
                navn = "Hospitering/sysselsetting/praksisplass"
                gyldighetsperiode = Periode().apply {
                    start = Date(1418825330000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("403753"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "436561" }
                kode = "Y"
                navn = "Konstituert"
                gyldighetsperiode = Periode().apply {
                    start = Date(1405941242000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("436561"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401259" }
                kode = "S"
                navn = "Vikar - sykdom"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401259"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401262" }
                kode = "Z"
                navn = "Annet"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401262"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401248" }
                kode = "H"
                navn = "Godtgjørelse"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401248"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401255" }
                kode = "O"
                navn = "Vikar - diverse"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401255"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401250" }
                kode = "J"
                navn = "Pensjonistlønn"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401250"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401257" }
                kode = "Q"
                navn = "Vikar - foreldreperm. 80%"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401257"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401260" }
                kode = "T"
                navn = "Vikar - ulønnet permisjon"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401260"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401247" }
                kode = "G"
                navn = "Engasjement > 6 mnd"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401247"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401249" }
                kode = "I"
                navn = "Oppdragstaker"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401249"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401245" }
                kode = "E"
                navn = "Midlertidig"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401245"))
            },
            ArbeidsforholdstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "401252" }
                kode = "L"
                navn = "Foreldreperm. 100%"
                gyldighetsperiode = Periode().apply {
                    start = Date(1251727700000L)
                    slutt = Date(4099766400000L)
                }
                addSelf(link<ArbeidsforholdstypeResource>("401252"))
            }
        )
    }
}
