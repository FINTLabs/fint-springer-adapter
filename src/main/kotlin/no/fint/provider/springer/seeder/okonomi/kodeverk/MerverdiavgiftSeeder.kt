package no.fint.provider.springer.seeder.okonomi.kodeverk

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.okonomi.kodeverk.MerverdiavgiftResource
import org.springframework.stereotype.Service

@Service
class MerverdiavgiftSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<MerverdiavgiftResource>(seederRepository, MerverdiavgiftResource::class.java) {

    fun generateEntitiesForTest(): List<MerverdiavgiftResource> = generateEntities()


    override fun generateEntities(): List<MerverdiavgiftResource> {
        return listOf(
            MerverdiavgiftResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "25" }
                kode = "25"
                navn = "25%"
                sats = 250
                addSelf(link<MerverdiavgiftResource>("25"))
            },
            MerverdiavgiftResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "205" }
                kode = "205"
                navn = "OMSETNING UNNTATT MVA"
                sats = 0
                addSelf(link<MerverdiavgiftResource>("205"))
            }
        )
    }
}
