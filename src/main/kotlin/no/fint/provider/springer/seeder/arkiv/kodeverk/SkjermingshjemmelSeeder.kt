package no.fint.provider.springer.seeder.arkiv.kodeverk

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.arkiv.kodeverk.SkjermingshjemmelResource
import org.springframework.stereotype.Service

@Service
class SkjermingshjemmelSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<SkjermingshjemmelResource>(seederRepository, SkjermingshjemmelResource::class.java) {

    fun generateEntitiesForTest(): List<SkjermingshjemmelResource> = generateEntities()


    override fun generateEntities(): List<SkjermingshjemmelResource> {
        return listOf(
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "34" }
                kode = "Offl. § 2"
                navn = "Offl. § 2 (virkeområdet til loven)"
                addSelf(link<SkjermingshjemmelResource>("34"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "55" }
                kode = "Offl. § 23 tredje ledd"
                navn = "Offl. § 23 tredje ledd (unntak for innsyn for tilbud og protokoll om offentlig innkjøp)"
                addSelf(link<SkjermingshjemmelResource>("55"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "79" }
                kode = "Offl. § 26 første ledd"
                navn = "Offl. § 26 første ledd (besvarelse av eksamen e.l. prøve samt utkast til konkurranse e.l.)"
                addSelf(link<SkjermingshjemmelResource>("79"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "80" }
                kode = "Offl. § 26 andre ledd"
                navn = "Offl. § 26 andre ledd (priser, hederstegn o.l. inntil tildeling er gjort)"
                addSelf(link<SkjermingshjemmelResource>("80"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "57" }
                kode = "Offl. § 24 første ledd"
                navn = "Offl. § 24 første ledd (motvirke offentlige kontroll- eller reguleringstiltak)"
                addSelf(link<SkjermingshjemmelResource>("57"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "36" }
                kode = "Offl. § 4"
                navn = "Offl. § 4 (definisjoner)"
                addSelf(link<SkjermingshjemmelResource>("36"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "46" }
                kode = "Offl. § 16"
                navn = "Offl. § 16 (innsyn i interne dokumenter i kommunen og fylkeskommunen)"
                addSelf(link<SkjermingshjemmelResource>("46"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "53" }
                kode = "Offl. § 23 første ledd"
                navn = "Offl. § 23 første ledd (forsvarlig gjennomføring av økonomi, lønns- og personalforvaltning)"
                addSelf(link<SkjermingshjemmelResource>("53"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "48" }
                kode = "Offl. § 18"
                navn = "Offl. § 18 (rettsaksdokumenter)"
                addSelf(link<SkjermingshjemmelResource>("48"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "59" }
                kode = "Offl. § 24 tredje ledd"
                navn = "Offl. § 24 tredje ledd (opplysninger for å unngå straffbare handlinger)"
                addSelf(link<SkjermingshjemmelResource>("59"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "38" }
                kode = "Offl. § 8"
                navn = "Offl. § 8 (hovedregel om rett til gratis innsyn)"
                addSelf(link<SkjermingshjemmelResource>("38"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "82" }
                kode = "Offl. § 26 femte ledd"
                navn = "Offl. § 26 femte ledd (unntak for fødselsnr og nummer med tilsvarende funksjon)"
                addSelf(link<SkjermingshjemmelResource>("82"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "43" }
                kode = "Offl. § 13 jf. fvl. § 13 (1) nr.1"
                navn = "Offl. § 13 jf. fvl. § 13 (1) nr.1 (opplysninger som er underlagt taushetsplikt)"
                addSelf(link<SkjermingshjemmelResource>("43"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "35" }
                kode = "Offl. § 3"
                navn = "Offl. § 3 (hovedregel)"
                addSelf(link<SkjermingshjemmelResource>("35"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "37" }
                kode = "Offl. § 5"
                navn = "Offl. § 5 (utsatt innsyn)"
                addSelf(link<SkjermingshjemmelResource>("37"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "61" }
                kode = "Offl. § 26 tredje ledd"
                navn = "Offl. § 26 tredje ledd (unntak for opplysninger i personregister)"
                addSelf(link<SkjermingshjemmelResource>("61"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "51" }
                kode = "Offl. § 21"
                navn = "Offl. § 21 (nasjonale forsvars- og sikkerhetsinteresser)"
                addSelf(link<SkjermingshjemmelResource>("51"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "45" }
                kode = "Offl. § 15"
                navn = "Offl. § 15 (interne dokumenter utenfra)"
                addSelf(link<SkjermingshjemmelResource>("45"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "68" }
                kode = "Offl. § 13 jf. fvl. § 13 (1) nr.2"
                navn = "Offl. § 13 jf. fvl. § 13 (1) nr.2 (opplysninger av konkurransemessig betydning)"
                addSelf(link<SkjermingshjemmelResource>("68"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "47" }
                kode = "Offl. § 17"
                navn = "Offl. § 17 (dokumenter som i visse tilfeller gjelder Det kongl. Hoff)"
                addSelf(link<SkjermingshjemmelResource>("47"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "60" }
                kode = "Offl. § 25"
                navn = "Offl. § 25 (dokument i sak om ansettelse eller forfremmelse i off. tjeneste)"
                addSelf(link<SkjermingshjemmelResource>("60"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "42" }
                kode = "Offl. § 12"
                navn = "Offl. § 12 (unntak for resten av dokumentet)"
                addSelf(link<SkjermingshjemmelResource>("42"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "56" }
                kode = "Offl. § 23 fjerde ledd"
                navn = "Offl. § 23 fjerde ledd (unntak for dokumenter med eierinteresser)"
                addSelf(link<SkjermingshjemmelResource>("56"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "58" }
                kode = "Offl. § 24 andre ledd"
                navn = "Offl. § 24 andre ledd (anmeldelse, rapport og annet dokument om lovovertredelse)"
                addSelf(link<SkjermingshjemmelResource>("58"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "44" }
                kode = "Offl. § 14"
                navn = "Offl. § 14 (organinterne dokumenter)"
                addSelf(link<SkjermingshjemmelResource>("44"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "83" }
                kode = "Offl. § 26 fjerde ledd"
                navn = "Offl. § 26 fjerde ledd (unntak for opplysninger om forskingsideer og forskingsprosjekt)"
                addSelf(link<SkjermingshjemmelResource>("83"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "54" }
                kode = "Offl. § 23 andre ledd"
                navn = "Offl. § 23 andre ledd (rammeavtaler med landbruks-, fiskeri- og reindriftsorganisasjoner)"
                addSelf(link<SkjermingshjemmelResource>("54"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "81" }
                kode = "Offl. § 27"
                navn = "Offl. § 27 (forskrifthjemmel)"
                addSelf(link<SkjermingshjemmelResource>("81"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "41" }
                kode = "Offl. § 11"
                navn = "Offl. § 11 (unntatt med hjemmel i forskrift)"
                addSelf(link<SkjermingshjemmelResource>("41"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "39" }
                kode = "Offl. § 9"
                navn = "Offl. § 9 (rett til innsyn i sammenstilling fra databaser)"
                addSelf(link<SkjermingshjemmelResource>("39"))
            },
            SkjermingshjemmelResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "40" }
                kode = "Offl. § 10"
                navn = "Offl. § 10 (plikt til å føre journal og tilgjengeliggjøring av journal på internett)"
                addSelf(link<SkjermingshjemmelResource>("40"))
            }
        )
    }
}
