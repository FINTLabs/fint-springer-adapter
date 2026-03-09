package no.fint.provider.springer.seeder.administrasjon.personal

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.administrasjon.kodeverk.LonnsartResource
import no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource
import no.novari.fint.model.resource.administrasjon.personal.FastlonnResource
import no.novari.fint.model.resource.administrasjon.personal.PersonalressursResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class FastlonnSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<FastlonnResource>(seederRepository, FastlonnResource::class.java) {

    fun generateEntitiesForTest(): List<FastlonnResource> = generateEntities()


    override fun generateEntities(): List<FastlonnResource> {
        return listOf(
            FastlonnResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "FAKE" }
                anvist = Date(1522939880000L) // 2018-04-05T14:51:20Z
                attestert = Date(1522939870000L) // 2018-04-05T14:51:10Z
                kontert = null
                periode = Periode().apply {
                    beskrivelse = null
                    slutt = null
                    start = Date(1522939880000L) // 2018-04-05T14:51:20Z
                }
                beskrivelse = "Test"
                opptjent = Periode().apply {
                    beskrivelse = null
                    slutt = null
                    start = Date(1522939880000L) // 2018-04-05T14:51:20Z
                }
                prosent = 10000
                
                addSelf(link<FastlonnResource>("FAKE"))
                addLonnsart(link<LonnsartResource>("391818"))
                addArbeidsforhold(link<ArbeidsforholdResource>("100003_1"))
                addAttestant(link<PersonalressursResource>("100003", "ansattnummer"))
                addAnviser(link<PersonalressursResource>("100000", "ansattnummer"))
            },
            FastlonnResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "FL_DFRTIYUGO" }
                anvist = Date(1522939880000L)
                attestert = Date(1522939870000L)
                kontert = null
                periode = Periode().apply {
                    beskrivelse = null
                    slutt = null
                    start = Date(1522939880000L)
                }
                beskrivelse = "Fastlønn for arbeidsforhold 100003_1"
                opptjent = Periode().apply {
                    beskrivelse = null
                    slutt = null
                    start = Date(1522939880000L)
                }
                prosent = 10000

                addSelf(link<FastlonnResource>("FL_DFRTIYUGO"))
                addLonnsart(link<LonnsartResource>("391818"))
                addArbeidsforhold(link<ArbeidsforholdResource>("100003_1"))
                addAttestant(link<PersonalressursResource>("100003", "ansattnummer"))
                addAnviser(link<PersonalressursResource>("100000", "ansattnummer"))
            }
        )
    }
}
