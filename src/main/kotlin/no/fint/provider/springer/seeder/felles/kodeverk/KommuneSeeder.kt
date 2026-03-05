package no.fint.provider.springer.seeder.felles.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.felles.kodeverk.KommuneResource
import org.springframework.stereotype.Service

@Service
class KommuneSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<KommuneResource>(seederRepository, KommuneResource::class.java) {

    fun generateEntitiesForTest(): List<KommuneResource> = generateEntities()

    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<KommuneResource> {
        return listOf(
            KommuneResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "4201" }
                kode = "4201"
                navn = "Haugasond"
                addSelf(link<KommuneResource>("4201"))
            }
        )
    }
}
