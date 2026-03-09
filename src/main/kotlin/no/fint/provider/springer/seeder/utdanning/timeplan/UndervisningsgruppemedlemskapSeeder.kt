package no.fint.provider.springer.seeder.utdanning.timeplan

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.utdanning.elev.ElevforholdResource
import no.novari.fint.model.resource.utdanning.timeplan.UndervisningsgruppeResource
import no.novari.fint.model.resource.utdanning.timeplan.UndervisningsgruppemedlemskapResource
import org.springframework.stereotype.Service

@Service
class UndervisningsgruppemedlemskapSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<UndervisningsgruppemedlemskapResource>(
    seederRepository,
    UndervisningsgruppemedlemskapResource::class.java
) {

    fun generateEntitiesForTest(): List<UndervisningsgruppemedlemskapResource> = generateEntities()

    override fun generateEntities(): List<UndervisningsgruppemedlemskapResource> {
        return listOf(
            undervisningsgruppemedlemskap("UGM_E_500001_1_UG_2018_1STA_NOR1Z41", "E_500001_1", "UG_2018_1STA_NOR1Z41"),
            undervisningsgruppemedlemskap("UGM_E_500002_1_UG_2018_1STA_NOR1Z41", "E_500002_1", "UG_2018_1STA_NOR1Z41"),
            undervisningsgruppemedlemskap("UGM_E_500003_1_UG_2018_1STA_NOR1Z41", "E_500003_1", "UG_2018_1STA_NOR1Z41"),
            undervisningsgruppemedlemskap("UGM_500011-1_10128458_1IDA", "500011-1", "10128458_1IDA"),
            undervisningsgruppemedlemskap("UGM_500011-2_10128458_1IDA", "500011-2", "10128458_1IDA"),
            undervisningsgruppemedlemskap("UGM_500012-1_10128458_1IDA", "500012-1", "10128458_1IDA"),
            undervisningsgruppemedlemskap("UGM_500013-1_10128458_2IDA", "500013-1", "10128458_2IDA"),
            undervisningsgruppemedlemskap("UGM_500014-1_10128458_2IDA", "500014-1", "10128458_2IDA")
        )
    }

    private fun undervisningsgruppemedlemskap(
        systemIdValue: String,
        elevforholdSystemId: String,
        undervisningsgruppeSystemId: String
    ) = UndervisningsgruppemedlemskapResource().apply {
        systemId = Identifikator().apply { identifikatorverdi = systemIdValue }
        addSelf(link<UndervisningsgruppemedlemskapResource>(systemIdValue))
        addElevforhold(link<ElevforholdResource>(elevforholdSystemId))
        addUndervisningsgruppe(link<UndervisningsgruppeResource>(undervisningsgruppeSystemId))
    }
}
