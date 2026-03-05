package no.fint.provider.springer.seeder.utdanning.elev

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.utdanning.elev.ElevforholdResource
import no.novari.fint.model.resource.utdanning.elev.KlasseResource
import no.novari.fint.model.resource.utdanning.elev.KlassemedlemskapResource
import org.springframework.stereotype.Service

@Service
class KlassemedlemskapSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<KlassemedlemskapResource>(seederRepository, KlassemedlemskapResource::class.java) {

    fun generateEntitiesForTest(): List<KlassemedlemskapResource> = generateEntities()

    override fun generateEntities(): List<KlassemedlemskapResource> {
        return listOf(
            klassemedlemskap("BGM_E_500001_1_BG_1STA-2018", "E_500001_1", "BG_1STA-2018"),
            klassemedlemskap("BGM_E_500002_1_BG_1STA-2018", "E_500002_1", "BG_1STA-2018"),
            klassemedlemskap("BGM_E_500003_1_BG_1STA-2018", "E_500003_1", "BG_1STA-2018"),
            klassemedlemskap("BGM_500011-1_1321121", "500011-1", "1321121"),
            klassemedlemskap("BGM_500012-1_1321121", "500012-1", "1321121"),
            klassemedlemskap("BGM_500013-1_1321122", "500013-1", "1321122"),
            klassemedlemskap("BGM_500014-1_1321122", "500014-1", "1321122"),
            klassemedlemskap("BGM_500014-1_1321123", "500014-1", "1321123")
        )
    }

    private fun klassemedlemskap(systemIdValue: String, elevforholdSystemId: String, klasseSystemId: String) =
        KlassemedlemskapResource().apply {
            systemId = Identifikator().apply { identifikatorverdi = systemIdValue }
            addSelf(link<KlassemedlemskapResource>(systemIdValue))
            addElevforhold(link<ElevforholdResource>(elevforholdSystemId))
            addKlasse(link<KlasseResource>(klasseSystemId))
        }
}
