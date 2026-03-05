package no.fint.provider.springer.seeder.administrasjon.personal

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.kodeverk.FravarsgrunnResource
import no.novari.fint.model.resource.administrasjon.kodeverk.FravarstypeResource
import no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource
import no.novari.fint.model.resource.administrasjon.personal.FravarResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class FravarSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<FravarResource>(seederRepository, FravarResource::class.java) {

    fun generateEntitiesForTest(): List<FravarResource> = generateEntities()

    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<FravarResource> {
        return listOf(
            FravarResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "F75J843W7J029" }
                periode = Periode().apply {
                    start = Date(1517443200000L) // 2018-01-31T23:00:00Z
                    slutt = Date(1523361645000L)  // 2018-04-10T12:00:45Z
                }
                prosent = 10000
                
                addSelf(link<FravarResource>("F75J843W7J029"))
                addArbeidsforhold(link<ArbeidsforholdResource>("100002_1"))
                addFravarsgrunn(link<FravarsgrunnResource>("915039"))
                addFravarstype(link<FravarstypeResource>("408396"))
            }
        )
    }
}
