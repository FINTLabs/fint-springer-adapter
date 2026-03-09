package no.fint.provider.springer.seeder.administrasjon.personal

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.administrasjon.kodeverk.LonnsartResource
import no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource
import no.novari.fint.model.resource.administrasjon.personal.FasttilleggResource
import no.novari.fint.model.resource.administrasjon.personal.PersonalressursResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class FasttilleggSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<FasttilleggResource>(seederRepository, FasttilleggResource::class.java) {

    fun generateEntitiesForTest(): List<FasttilleggResource> = generateEntities()


    override fun generateEntities(): List<FasttilleggResource> {
        return listOf(
            FasttilleggResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "FAKE" }
                anvist = Date(1522939880000L) // 2018-04-05T14:51:20Z
                attestert = Date(1522939870000L) // 2018-04-05T14:51:10Z
                kontert = null
                periode = Periode().apply {
                    beskrivelse = null
                    slutt = null
                    start = Date(1522939880000L) // 2018-04-05T14:51:20Z
                }
                beskrivelse = "Kontaktlærertillegg"
                belop = 1000000
                opptjent = Periode().apply {
                    beskrivelse = null
                    slutt = null
                    start = Date(1522939880000L) // 2018-04-05T14:51:20Z
                }
                
                addSelf(link<FasttilleggResource>("FAKE"))
                addLonnsart(link<LonnsartResource>("391818"))
                addArbeidsforhold(link<ArbeidsforholdResource>("100003_1"))
                addAttestant(link<PersonalressursResource>("100003", "ansattnummer"))
                addAnviser(link<PersonalressursResource>("100000", "ansattnummer"))
            }
        )
    }
}
