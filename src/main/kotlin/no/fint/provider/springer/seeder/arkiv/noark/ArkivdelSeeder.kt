package no.fint.provider.springer.seeder.arkiv.noark

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.arkiv.noark.ArkivdelResource
import org.springframework.stereotype.Service

@Service
class ArkivdelSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<ArkivdelResource>(seederRepository, ArkivdelResource::class.java) {

    fun generateEntitiesForTest(): List<ArkivdelResource> = generateEntities()


    override fun generateEntities(): List<ArkivdelResource> {
        return listOf(
            ArkivdelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "3" }
                tittel = "Skoleskyss"
                addSelf(link<ArkivdelResource>("3"))
            },
            ArkivdelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "200021" }
                tittel = "Anskaffelser"
                addSelf(link<ArkivdelResource>("200021"))
            },
            ArkivdelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "200019" }
                tittel = "Løyver"
                addSelf(link<ArkivdelResource>("200019"))
            },
            ArkivdelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "200016" }
                tittel = "Varsling"
                addSelf(link<ArkivdelResource>("200016"))
            },
            ArkivdelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1" }
                tittel = "Personal"
                addSelf(link<ArkivdelResource>("1"))
            },
            ArkivdelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "200023" }
                tittel = "Mobbeombud"
                addSelf(link<ArkivdelResource>("200023"))
            },
            ArkivdelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "200014" }
                tittel = "Transportordningen"
                addSelf(link<ArkivdelResource>("200014"))
            },
            ArkivdelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "200015" }
                tittel = "Pasientbehandling"
                addSelf(link<ArkivdelResource>("200015"))
            },
            ArkivdelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "200022" }
                tittel = "Grunnskule"
                addSelf(link<ArkivdelResource>("200022"))
            },
            ArkivdelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "200020" }
                tittel = "Vidaregåande skule"
                addSelf(link<ArkivdelResource>("200020"))
            },
            ArkivdelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "200018" }
                tittel = "Tilskot"
                addSelf(link<ArkivdelResource>("200018"))
            },
            ArkivdelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "200024" }
                tittel = "Elev- og lærlingombud"
                addSelf(link<ArkivdelResource>("200024"))
            },
            ArkivdelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "4" }
                tittel = "Elev"
                addSelf(link<ArkivdelResource>("4"))
            },
            ArkivdelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "60001" }
                tittel = "Sakarkiv"
                addSelf(link<ArkivdelResource>("60001"))
            }
        )
    }
}
