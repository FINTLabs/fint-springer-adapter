package no.fint.provider.springer.seeder.okonomi.kodeverk

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.administrasjon.kodeverk.AnsvarResource
import no.novari.fint.model.resource.administrasjon.kodeverk.ArtResource
import no.novari.fint.model.resource.administrasjon.kodeverk.FunksjonResource
import no.novari.fint.model.resource.administrasjon.kompleksedatatyper.KontostrengResource
import no.novari.fint.model.resource.okonomi.faktura.FakturautstederResource
import no.novari.fint.model.resource.okonomi.kodeverk.MerverdiavgiftResource
import no.novari.fint.model.resource.okonomi.kodeverk.VareResource
import org.springframework.stereotype.Service

@Service
class VareSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<VareResource>(seederRepository, VareResource::class.java) {

    fun generateEntitiesForTest(): List<VareResource> = generateEntities()


    override fun generateEntities(): List<VareResource> {
        val varer = listOf(
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1234566" }
                kode = "1234566"
                navn = "Doble fustasjeopphengsforkoblinger"
                enhet = "stykk"
                pris = 12999
                addSelf(link<VareResource>("1234566"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "007" }
                kode = "007"
                navn = "Kjip PC"
                enhet = "stykk"
                pris = 120000
                addSelf(link<VareResource>("007"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1181" }
                kode = "1181"
                navn = "Apple MacBook A1181 13.3\" Laptop (Early 2009) - 2.00GHz, 4GB RAM, 750GB HDD"
                enhet = "stykk"
                pris = 53200
                addSelf(link<VareResource>("1181"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "925023" }
                kode = "925023"
                navn = "Apple MacBook Pro 13\" Retina Space Gray Dual-core i5 2.3GHz, 8GB RAM, 256GB PCIe SSD, Intel Iris Graphics"
                enhet = "stykk"
                pris = 1239200
                addSelf(link<VareResource>("925023"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "925006" }
                kode = "925006"
                navn = "Apple MacBook Air 13.3\" Dual Core i5 1.8GHz, 8GB RAM, 256GB Flash Storage"
                enhet = "stykk"
                pris = 999120
                addSelf(link<VareResource>("925006"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "925002" }
                kode = "925002"
                navn = "Apple MacBook 12\" Retina Silver Dual-Core i5 1.3GHz, 8GB, 512GB Flash Storage"
                enhet = "stykk"
                pris = 1279200
                addSelf(link<VareResource>("925002"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1068534" }
                kode = "1068534"
                navn = "Lenovo ThinkPad X1 Carbon gen. 6 14.0\", Core i7-8550U, 8GB RAM, 1TB SSD"
                enhet = "stykk"
                pris = 1599200
                addSelf(link<VareResource>("1068534"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "10001" }
                kode = "10001"
                navn = "Kontorutstyr Pakke Grafisk Bachelor"
                enhet = "stykk"
                pris = 64900
                addSelf(link<VareResource>("10001"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "10002" }
                kode = "10002"
                navn = "Kontorutstyr Pakke Interiør"
                enhet = "stykk"
                pris = 89000
                addSelf(link<VareResource>("10002"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "10003" }
                kode = "10003"
                navn = "Kontorutstyr Pakke Illustrasjon"
                enhet = "stykk"
                pris = 89000
                addSelf(link<VareResource>("10003"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "10004" }
                kode = "10004"
                navn = "Kontorutstyr Pakke ESMOD"
                enhet = "stykk"
                pris = 59900
                addSelf(link<VareResource>("10004"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "20001" }
                kode = "1"
                navn = "TEST FINT VARE 1"
                enhet = "stykk"
                pris = 100
                addSelf(link<VareResource>("20001"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "20002" }
                kode = "2"
                navn = "TEST FINT VARE 2"
                enhet = "stykk"
                pris = 100
                addSelf(link<VareResource>("20002"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "20003" }
                kode = "3"
                navn = "TEST FINT VARE 3"
                enhet = "stykk"
                pris = 100
                addSelf(link<VareResource>("20003"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "100" }
                kode = "100"
                navn = "TEST VISMA VARE 1"
                enhet = "stykk"
                pris = 100
                addSelf(link<VareResource>("100"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "101" }
                kode = "101"
                navn = "TEST VISMA VARE 2"
                enhet = "stykk"
                pris = 100
                addSelf(link<VareResource>("101"))
            },
            VareResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "102" }
                kode = "102"
                navn = "TEST VISMA VARE 3"
                enhet = "stykk"
                pris = 123456
                addSelf(link<VareResource>("102"))
            }
        )

        varer.forEach { vare ->
            val (art, ansvar, funksjon, merverdiavgift, fakturautsteder) = when (vare.systemId?.identifikatorverdi) {
                "20001" -> RelationIds("16249", "13300", "4200", "205", "299")
                "20002" -> RelationIds("16006", "13300", "4200", "205", "299")
                "20003" -> RelationIds("16004", "13300", "4200", "205", "299")
                "100", "101", "102" -> RelationIds("16249", "13300", "4200", "205", "10")
                else -> RelationIds("1", "2", "3", "25", "1")
            }

            vare.kontering = KontostrengResource().apply {
                addArt(link<ArtResource>(art))
                addAnsvar(link<AnsvarResource>(ansvar))
                addFunksjon(link<FunksjonResource>(funksjon))
            }
            vare.addMerverdiavgift(link<MerverdiavgiftResource>(merverdiavgift))
            vare.addFakturautsteder(link<FakturautstederResource>(fakturautsteder))
        }

        return varer
    }

    private data class RelationIds(
        val art: String,
        val ansvar: String,
        val funksjon: String,
        val merverdiavgift: String,
        val fakturautsteder: String
    )
}
