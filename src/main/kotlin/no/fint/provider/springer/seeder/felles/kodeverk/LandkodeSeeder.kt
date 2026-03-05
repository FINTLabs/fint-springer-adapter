package no.fint.provider.springer.seeder.felles.kodeverk

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.felles.kodeverk.iso.LandkodeResource
import org.springframework.stereotype.Service

@Service
class LandkodeSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<LandkodeResource>(seederRepository, LandkodeResource::class.java) {

    fun generateEntitiesForTest(): List<LandkodeResource> = generateEntities()


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
