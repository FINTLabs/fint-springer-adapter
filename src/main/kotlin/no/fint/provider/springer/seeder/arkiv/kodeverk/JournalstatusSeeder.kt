package no.fint.provider.springer.seeder.arkiv.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.arkiv.kodeverk.JournalStatusResource
import org.springframework.stereotype.Service

@Service
class JournalstatusSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<JournalStatusResource>(seederRepository, JournalStatusResource::class.java) {

    fun generateEntitiesForTest(): List<JournalStatusResource> = generateEntities()

    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<JournalStatusResource> {
        return listOf(
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "7" }
                kode = "A"
                navn = "Arkivert"
                addSelf(link<JournalStatusResource>("7"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "R" }
                kode = "R"
                navn = "Reservert dokument"
                addSelf(link<JournalStatusResource>("R"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "11" }
                kode = "I"
                navn = "Ikke godkjent"
                addSelf(link<JournalStatusResource>("11"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "10" }
                kode = "G"
                navn = "Godkjent"
                addSelf(link<JournalStatusResource>("10"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "6" }
                kode = "J"
                navn = "Journalført"
                addSelf(link<JournalStatusResource>("6"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "E" }
                kode = "E"
                navn = "Ekspedert"
                addSelf(link<JournalStatusResource>("E"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "2" }
                kode = "M"
                navn = "Midlertidig journalført"
                addSelf(link<JournalStatusResource>("2"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "17" }
                kode = "RD"
                navn = "Ready for destruction"
                addSelf(link<JournalStatusResource>("17"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "14" }
                kode = "Signert"
                navn = "Signert"
                addSelf(link<JournalStatusResource>("14"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "U" }
                kode = "U"
                navn = "Utgår"
                addSelf(link<JournalStatusResource>("U"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "9" }
                kode = "T"
                navn = "Til godkjenning"
                addSelf(link<JournalStatusResource>("9"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "12" }
                kode = "K"
                navn = "Til kommentering"
                addSelf(link<JournalStatusResource>("12"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "J" }
                kode = "J"
                navn = "Journalført"
                addSelf(link<JournalStatusResource>("J"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "4" }
                kode = "F"
                navn = "Ferdig fra ansvarlig"
                addSelf(link<JournalStatusResource>("4"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "8" }
                kode = "U"
                navn = "Utgår"
                addSelf(link<JournalStatusResource>("8"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "16" }
                kode = "K"
                navn = "Kassert"
                addSelf(link<JournalStatusResource>("16"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "A" }
                kode = "A"
                navn = "Arkivert"
                addSelf(link<JournalStatusResource>("A"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "18" }
                kode = "US"
                navn = "Under signering"
                addSelf(link<JournalStatusResource>("18"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "F" }
                kode = "F"
                navn = "Ferdigstilt fra saksbehandler"
                addSelf(link<JournalStatusResource>("F"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "3" }
                kode = "S"
                navn = "Registrert av ansvarlig person/leder"
                addSelf(link<JournalStatusResource>("3"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "G" }
                kode = "G"
                navn = "Godkjent av leder"
                addSelf(link<JournalStatusResource>("G"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1" }
                kode = "R"
                navn = "Reservert"
                addSelf(link<JournalStatusResource>("1"))
            },
            JournalStatusResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "5" }
                kode = "E"
                navn = "Ekspedert"
                addSelf(link<JournalStatusResource>("5"))
            }
        )
    }
}
