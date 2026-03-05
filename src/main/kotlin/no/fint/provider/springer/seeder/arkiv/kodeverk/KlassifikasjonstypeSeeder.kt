package no.fint.provider.springer.seeder.arkiv.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.arkiv.kodeverk.KlassifikasjonstypeResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class KlassifikasjonstypeSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<KlassifikasjonstypeResource>(seederRepository, KlassifikasjonstypeResource::class.java) {

    fun generateEntitiesForTest(): List<KlassifikasjonstypeResource> = generateEntities()

    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<KlassifikasjonstypeResource> {
        return listOf(
            KlassifikasjonstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1" }
                kode = "grbr"
                navn = "Gårds- og bruksnummer"
                gyldighetsperiode = Periode().apply {
                    start = Date(1576748512000L)
                }
                addSelf(link<KlassifikasjonstypeResource>("1"))
            },
            KlassifikasjonstypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "2" }
                kode = "kkode"
                navn = "K-koder"
                gyldighetsperiode = Periode().apply {
                    start = Date(1576748512000L)
                }
                addSelf(link<KlassifikasjonstypeResource>("2"))
            }
        )
    }
}
