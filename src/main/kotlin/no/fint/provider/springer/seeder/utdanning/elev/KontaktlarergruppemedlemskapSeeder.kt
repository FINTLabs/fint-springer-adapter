package no.fint.provider.springer.seeder.utdanning.elev

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.utdanning.elev.ElevforholdResource
import no.novari.fint.model.resource.utdanning.elev.KontaktlarergruppeResource
import no.novari.fint.model.resource.utdanning.elev.KontaktlarergruppemedlemskapResource
import org.springframework.stereotype.Service

@Service
class KontaktlarergruppemedlemskapSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<KontaktlarergruppemedlemskapResource>(
    seederRepository,
    KontaktlarergruppemedlemskapResource::class.java
) {

    fun generateEntitiesForTest(): List<KontaktlarergruppemedlemskapResource> = generateEntities()

    override fun generateEntities(): List<KontaktlarergruppemedlemskapResource> {
        return listOf(
            gruppemedlemskap("KGM_E_500003_1_KG_1STA-1", "E_500003_1", "KG_1STA-1"),
            gruppemedlemskap("KGM_500011-1_1321122_1IDA", "500011-1", "1321122_1IDA"),
            gruppemedlemskap("KGM_500011-2_1321122_1IDA", "500011-2", "1321122_1IDA"),
            gruppemedlemskap("KGM_500013-1_1321122_2IDA", "500013-1", "1321122_2IDA")
        )
    }

    private fun gruppemedlemskap(
        systemIdValue: String,
        elevforholdSystemId: String,
        kontaktlarergruppeSystemId: String
    ) = KontaktlarergruppemedlemskapResource().apply {
        systemId = Identifikator().apply { identifikatorverdi = systemIdValue }
        addSelf(link<KontaktlarergruppemedlemskapResource>(systemIdValue))
        addElevforhold(link<ElevforholdResource>(elevforholdSystemId))
        addKontaktlarergruppe(link<KontaktlarergruppeResource>(kontaktlarergruppeSystemId))
    }
}
