package no.fint.provider.springer.seeder.arkiv.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.arkiv.kodeverk.KorrespondansepartTypeResource
import org.springframework.stereotype.Service

@Service
class KorrespondanseparttypeSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<KorrespondansepartTypeResource>(seederRepository, KorrespondansepartTypeResource::class.java) {

    fun generateEntitiesForTest(): List<KorrespondansepartTypeResource> = generateEntities()


    override fun generateEntities(): List<KorrespondansepartTypeResource> {
        return listOf(
            KorrespondansepartTypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "A" }
                kode = "A"
                navn = "Avsender"
                addSelf(link<KorrespondansepartTypeResource>("A"))
            },
            KorrespondansepartTypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "M" }
                kode = "M"
                navn = "Mottaker"
                addSelf(link<KorrespondansepartTypeResource>("M"))
            },
            KorrespondansepartTypeResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "I" }
                kode = "I"
                navn = "Intern"
                addSelf(link<KorrespondansepartTypeResource>("I"))
            }
        )
    }
}
