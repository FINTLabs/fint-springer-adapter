package no.fint.provider.springer.seeder.utdanning.elev

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.utdanning.elev.ElevResource
import no.novari.fint.model.resource.utdanning.elev.ElevforholdResource
import no.novari.fint.model.resource.utdanning.elev.KlassemedlemskapResource
import no.novari.fint.model.resource.utdanning.elev.KontaktlarergruppemedlemskapResource
import no.novari.fint.model.resource.utdanning.kodeverk.ElevkategoriResource
import no.novari.fint.model.resource.utdanning.timeplan.UndervisningsgruppemedlemskapResource
import no.novari.fint.model.resource.utdanning.utdanningsprogram.SkoleResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class ElevforholdSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<ElevforholdResource>(seederRepository, ElevforholdResource::class.java) {

    fun generateEntitiesForTest(): List<ElevforholdResource> = generateEntities()


    override fun generateEntities(): List<ElevforholdResource> {
        val start = Date(1627776000000L)
        val slutt = Date(1659225600000L)

        return listOf(
            ElevforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "E_500001_1" }
                beskrivelse = "Flink elev"
                addSelf(link<ElevforholdResource>("E_500001_1"))
                addElev(link<ElevResource>("500001", "elevnummer"))
                addSkole(link<SkoleResource>("123456", "skolenummer"))
                addKategori(link<ElevkategoriResource>("heltid"))
                addKlassemedlemskap(link<KlassemedlemskapResource>("BGM_E_500001_1_BG_1STA-2018"))
                addUndervisningsgruppemedlemskap(link<UndervisningsgruppemedlemskapResource>("UGM_E_500001_1_UG_2018_1STA_NOR1Z41"))
            },
            ElevforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "E_500002_1" }
                beskrivelse = "Ikke så flink elev"
                addSelf(link<ElevforholdResource>("E_500002_1"))
                addElev(link<ElevResource>("500002", "elevnummer"))
                addSkole(link<SkoleResource>("123456", "skolenummer"))
                addKategori(link<ElevkategoriResource>("heltid"))
                addKlassemedlemskap(link<KlassemedlemskapResource>("BGM_E_500002_1_BG_1STA-2018"))
                addUndervisningsgruppemedlemskap(link<UndervisningsgruppemedlemskapResource>("UGM_E_500002_1_UG_2018_1STA_NOR1Z41"))
            },
            ElevforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "E_500003_1" }
                beskrivelse = "Dårlig elev"
                addSelf(link<ElevforholdResource>("E_500003_1"))
                addElev(link<ElevResource>("500003", "elevnummer"))
                addSkole(link<SkoleResource>("123456", "skolenummer"))
                addKategori(link<ElevkategoriResource>("heltid"))
                addKlassemedlemskap(link<KlassemedlemskapResource>("BGM_E_500003_1_BG_1STA-2018"))
                addUndervisningsgruppemedlemskap(link<UndervisningsgruppemedlemskapResource>("UGM_E_500003_1_UG_2018_1STA_NOR1Z41"))
                addKontaktlarergruppemedlemskap(link<KontaktlarergruppemedlemskapResource>("KGM_E_500003_1_KG_1STA-1"))
            },
            ElevforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "E_ZZZZZZ_2" }
                beskrivelse = "Historisk elevforhold"
                addSelf(link<ElevforholdResource>("E_ZZZZZZ_2"))
                addElev(link<ElevResource>("500003", "elevnummer"))
                addSkole(link<SkoleResource>("123456", "skolenummer"))
                addKategori(link<ElevkategoriResource>("heltid"))
            },
            ElevforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "E_ZZZZZZ_1" }
                beskrivelse = "Historisk elevforhold"
                addSelf(link<ElevforholdResource>("E_ZZZZZZ_1"))
                addElev(link<ElevResource>("500002", "elevnummer"))
                addSkole(link<SkoleResource>("123456", "skolenummer"))
                addKategori(link<ElevkategoriResource>("heltid"))
            },
            ElevforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "E_2341_1" }
                beskrivelse = "Historisk elevforhold"
                addSelf(link<ElevforholdResource>("E_2341_1"))
                addElev(link<ElevResource>("500001", "elevnummer"))
                addSkole(link<SkoleResource>("123456", "skolenummer"))
                addKategori(link<ElevkategoriResource>("heltid"))
            },
            ElevforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "500011-1" }
                beskrivelse = ""
                hovedskole = true
                gyldighetsperiode = Periode().apply {
                    this.start = start
                    this.slutt = slutt
                }
                addSelf(link<ElevforholdResource>("500011-1"))
                addElev(link<ElevResource>("500011"))
                addSkole(link<SkoleResource>("1579"))
                addKategori(link<ElevkategoriResource>("heltid"))
                addKlassemedlemskap(link<KlassemedlemskapResource>("BGM_500011-1_1321121"))
                addUndervisningsgruppemedlemskap(link<UndervisningsgruppemedlemskapResource>("UGM_500011-1_10128458_1IDA"))
                addKontaktlarergruppemedlemskap(link<KontaktlarergruppemedlemskapResource>("KGM_500011-1_1321122_1IDA"))
            },
            ElevforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "500011-2" }
                beskrivelse = ""
                hovedskole = true
                gyldighetsperiode = Periode().apply {
                    this.start = start
                    this.slutt = slutt
                }
                addSelf(link<ElevforholdResource>("500011-2"))
                addElev(link<ElevResource>("500011"))
                addSkole(link<SkoleResource>("1579"))
                addKategori(link<ElevkategoriResource>("heltid"))
                addKlassemedlemskap(link<KlassemedlemskapResource>("BGM_500011-2_1321121"))
                addUndervisningsgruppemedlemskap(link<UndervisningsgruppemedlemskapResource>("UGM_500011-2_10128458_1IDA"))
                addKontaktlarergruppemedlemskap(link<KontaktlarergruppemedlemskapResource>("KGM_500011-2_1321122_1IDA"))
            },
            ElevforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "500012-1" }
                beskrivelse = ""
                hovedskole = true
                gyldighetsperiode = Periode().apply {
                    this.start = start
                    this.slutt = slutt
                }
                addSelf(link<ElevforholdResource>("500012-1"))
                addElev(link<ElevResource>("500012"))
                addSkole(link<SkoleResource>("1579"))
                addKategori(link<ElevkategoriResource>("heltid"))
                addKlassemedlemskap(link<KlassemedlemskapResource>("BGM_500012-1_1321121"))
                addUndervisningsgruppemedlemskap(link<UndervisningsgruppemedlemskapResource>("UGM_500012-1_10128458_1IDA"))
            },
            ElevforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "500013-1" }
                beskrivelse = ""
                hovedskole = true
                gyldighetsperiode = Periode().apply {
                    this.start = start
                    this.slutt = slutt
                }
                addSelf(link<ElevforholdResource>("500013-1"))
                addElev(link<ElevResource>("500013"))
                addSkole(link<SkoleResource>("1579"))
                addKategori(link<ElevkategoriResource>("heltid"))
                addKlassemedlemskap(link<KlassemedlemskapResource>("BGM_500013-1_1321122"))
                addUndervisningsgruppemedlemskap(link<UndervisningsgruppemedlemskapResource>("UGM_500013-1_10128458_2IDA"))
                addKontaktlarergruppemedlemskap(link<KontaktlarergruppemedlemskapResource>("KGM_500013-1_1321122_2IDA"))
            },
            ElevforholdResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "500014-1" }
                beskrivelse = ""
                hovedskole = true
                gyldighetsperiode = Periode().apply {
                    this.start = start
                    this.slutt = slutt
                }
                addSelf(link<ElevforholdResource>("500014-1"))
                addElev(link<ElevResource>("500014"))
                addSkole(link<SkoleResource>("1579"))
                addKategori(link<ElevkategoriResource>("heltid"))
                addKlassemedlemskap(link<KlassemedlemskapResource>("BGM_500014-1_1321122"))
                addKlassemedlemskap(link<KlassemedlemskapResource>("BGM_500014-1_1321123"))
                addUndervisningsgruppemedlemskap(link<UndervisningsgruppemedlemskapResource>("UGM_500014-1_10128458_2IDA"))
            }
        )
    }
}
