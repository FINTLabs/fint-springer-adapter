package no.fint.provider.springer.seeder.arkiv.noark

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.personal.PersonalressursResource
import no.novari.fint.model.resource.arkiv.noark.ArkivressursResource
import org.springframework.stereotype.Service

@Service
class ArkivressursSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<ArkivressursResource>(seederRepository, ArkivressursResource::class.java) {

    fun generateEntitiesForTest(): List<ArkivressursResource> = generateEntities()


    override fun generateEntities(): List<ArkivressursResource> {
        return listOf(
            ArkivressursResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "12101" }
                addSelf(link<ArkivressursResource>("12101"))
                addPersonalressurs(link<PersonalressursResource>("kegudnersen", "brukernavn"))
            },
            ArkivressursResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "10413" }
                addSelf(link<ArkivressursResource>("10413"))
                addPersonalressurs(link<PersonalressursResource>("ctollaksen", "brukernavn"))
            }
        )
    }
}
