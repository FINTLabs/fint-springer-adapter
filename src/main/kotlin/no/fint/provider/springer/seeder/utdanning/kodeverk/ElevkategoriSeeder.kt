package no.fint.provider.springer.seeder.utdanning.kodeverk

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.utdanning.kodeverk.ElevkategoriResource
import org.springframework.stereotype.Service

@Service
class ElevkategoriSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<ElevkategoriResource>(seederRepository, ElevkategoriResource::class.java) {

    fun generateEntitiesForTest(): List<ElevkategoriResource> = generateEntities()


    override fun generateEntities(): List<ElevkategoriResource> {
        return listOf(
            ElevkategoriResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "heltid" }
                kode = "heltid"
                navn = "Heltid"
                addSelf(link<ElevkategoriResource>("heltid"))
            },
            ElevkategoriResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "overtid" }
                kode = "overtid"
                navn = "Overtid"
                addSelf(link<ElevkategoriResource>("overtid"))
            }
        )
    }
}
