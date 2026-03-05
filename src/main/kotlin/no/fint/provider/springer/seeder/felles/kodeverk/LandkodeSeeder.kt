package no.fint.provider.springer.seeder.felles.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.felles.kodeverk.iso.LandkodeResource
import org.springframework.stereotype.Service

@Service
class LandkodeSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<LandkodeResource>(seederRepository, LandkodeResource::class.java) {

    fun generateEntitiesForTest(): List<LandkodeResource> = generateEntities()

    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<LandkodeResource> {
        return listOf(
            LandkodeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "NO" }
                kode = "NO"
                navn = "Norge"
                addSelf(link<LandkodeResource>("NO"))
            }
        )
    }
}
