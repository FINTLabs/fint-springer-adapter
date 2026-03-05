package no.fint.provider.springer.seeder.administrasjon.personal

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.administrasjon.kodeverk.LonnsartResource
import no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource
import no.novari.fint.model.resource.administrasjon.personal.PersonalressursResource
import no.novari.fint.model.resource.administrasjon.personal.VariabellonnResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class VariabellonnSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<VariabellonnResource>(seederRepository, VariabellonnResource::class.java) {

    fun generateEntitiesForTest(): List<VariabellonnResource> = generateEntities()


    override fun generateEntities(): List<VariabellonnResource> {
        return listOf(
            VariabellonnResource().apply {
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
                antall = 10000
                
                addSelf(link<VariabellonnResource>("FAKE"))
                addLonnsart(link<LonnsartResource>("8"))
                addArbeidsforhold(link<ArbeidsforholdResource>("1234"))
                addAttestant(link<PersonalressursResource>("120000", "ansattnummer"))
                addAnviser(link<PersonalressursResource>("100000", "ansattnummer"))
            }
        )
    }
}
