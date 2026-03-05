package no.fint.provider.springer.seeder.felles.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.felles.kodeverk.FylkeResource
import org.springframework.stereotype.Service

@Service
class FylkeSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<FylkeResource>(seederRepository, FylkeResource::class.java) {

    fun generateEntitiesForTest(): List<FylkeResource> = generateEntities()

    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<FylkeResource> {
        return listOf(
            FylkeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "42" }
                kode = "42"
                navn = "Haugaland"
                addSelf(link<FylkeResource>("42"))
            }
        )
    }
}
