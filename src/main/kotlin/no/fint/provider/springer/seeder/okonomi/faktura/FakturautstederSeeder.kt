package no.fint.provider.springer.seeder.okonomi.faktura

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.okonomi.faktura.FakturautstederResource
import no.novari.fint.model.resource.okonomi.kodeverk.VareResource
import org.springframework.stereotype.Service

@Service
class FakturautstederSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<FakturautstederResource>(seederRepository, FakturautstederResource::class.java) {

    fun generateEntitiesForTest(): List<FakturautstederResource> = generateEntities()


    override fun generateEntities(): List<FakturautstederResource> {
        return listOf(
            FakturautstederResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "1" }
                navn = "S. Vindel & B. Drag AS"
                addSelf(link<FakturautstederResource>("1"))
                addVare(link<VareResource>("1234566"))
                addVare(link<VareResource>("007"))
            },
            FakturautstederResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "10" }
                navn = "Visma Enterprise TEST"
                addSelf(link<FakturautstederResource>("10"))
            },
            FakturautstederResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "299" }
                navn = "Møre og Romsdal Fylkeskommune - TEST - FINT PROSJEKTET"
                addSelf(link<FakturautstederResource>("299"))
            },
            FakturautstederResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "11" }
                navn = "Andeby vgs"
                addSelf(link<FakturautstederResource>("11"))
            },
            FakturautstederResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "12" }
                navn = "Sundet vgs"
                addSelf(link<FakturautstederResource>("12"))
            }
        )
    }
}
