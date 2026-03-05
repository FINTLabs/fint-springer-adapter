package no.fint.provider.springer.seeder.felles.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.felles.kodeverk.iso.SprakResource
import org.springframework.stereotype.Service

@Service
class SprakSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<SprakResource>(seederRepository, SprakResource::class.java) {

    fun generateEntitiesForTest(): List<SprakResource> = generateEntities()

    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<SprakResource> {
        return listOf(
            SprakResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "no" }
                kode = "no"
                navn = "Norsk"
                addSelf(link<SprakResource>("no"))
            },
            SprakResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "nn" }
                kode = "nn"
                navn = "Nynorsk"
                addSelf(link<SprakResource>("nn"))
            },
            SprakResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "nb" }
                kode = "nb"
                navn = "Bokmål"
                addSelf(link<SprakResource>("nb"))
            }
        )
    }
}
