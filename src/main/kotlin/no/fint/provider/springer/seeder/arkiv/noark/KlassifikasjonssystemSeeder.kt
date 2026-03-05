package no.fint.provider.springer.seeder.arkiv.noark

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.arkiv.noark.KlassifikasjonssystemResource
import org.springframework.stereotype.Service

@Service
class KlassifikasjonssystemSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<KlassifikasjonssystemResource>(seederRepository, KlassifikasjonssystemResource::class.java) {

    fun generateEntitiesForTest(): List<KlassifikasjonssystemResource> = generateEntities()


    override fun generateEntities(): List<KlassifikasjonssystemResource> {
        return listOf(
            KlassifikasjonssystemResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "60001" }
                tittel = "Emneorientert arkivnøkkel"
                beskrivelse = "Emneorientert hierarkisk arkivnøkkel"
                addSelf(link<KlassifikasjonssystemResource>("60001"))
            },
            KlassifikasjonssystemResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "200001" }
                tittel = "Arkivnøkkel"
                beskrivelse = "Arkivnøkkel"
                addSelf(link<KlassifikasjonssystemResource>("200001"))
            },
            KlassifikasjonssystemResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "200007" }
                tittel = "ORG"
                beskrivelse = "Organisasjonsnummer"
                addSelf(link<KlassifikasjonssystemResource>("200007"))
            },
            KlassifikasjonssystemResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "60007" }
                tittel = "FAGKLASSE PRINSIPP"
                beskrivelse = "Fagklasse"
                addSelf(link<KlassifikasjonssystemResource>("60007"))
            },
            KlassifikasjonssystemResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "60006" }
                tittel = "FELLESKLASSE PRINSIPP"
                beskrivelse = "Felleskode"
                addSelf(link<KlassifikasjonssystemResource>("60006"))
            },
            KlassifikasjonssystemResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1" }
                tittel = "FNR"
                beskrivelse = "Fødselsnummer"
                addSelf(link<KlassifikasjonssystemResource>("1"))
            },
            KlassifikasjonssystemResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "60008" }
                tittel = "TILLEGGSKODE PRINSIPP"
                beskrivelse = "Tilleggskode"
                addSelf(link<KlassifikasjonssystemResource>("60008"))
            },
            KlassifikasjonssystemResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "2" }
                tittel = "PlanID"
                beskrivelse = "Planidentifikasjon"
                addSelf(link<KlassifikasjonssystemResource>("2"))
            },
            KlassifikasjonssystemResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "99003" }
                tittel = "Gnr/bnr"
                beskrivelse = "Gnr/bnr"
                addSelf(link<KlassifikasjonssystemResource>("99003"))
            }
        )
    }
}
