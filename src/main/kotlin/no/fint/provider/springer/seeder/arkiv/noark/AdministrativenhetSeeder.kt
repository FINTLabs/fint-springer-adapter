package no.fint.provider.springer.seeder.arkiv.noark

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.administrasjon.organisasjon.OrganisasjonselementResource
import no.novari.fint.model.resource.arkiv.noark.AdministrativEnhetResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AdministrativenhetSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<AdministrativEnhetResource>(seederRepository, AdministrativEnhetResource::class.java) {

    fun generateEntitiesForTest(): List<AdministrativEnhetResource> = generateEntities()


    override fun generateEntities(): List<AdministrativEnhetResource> {
        return listOf(
            AdministrativEnhetResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "200079" }
                navn = "Haugaland fylkeskommune"
                gyldighetsperiode = Periode().apply {
                    start = Date(1566485105000L)
                }
                
                addSelf(link<AdministrativEnhetResource>("200079"))
                addOrganisasjonselement(link<OrganisasjonselementResource>("550000", "organisasjonsid"))
            },
            AdministrativEnhetResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "204229" }
                navn = "Sundet videregående skole"
                gyldighetsperiode = Periode().apply {
                    start = Date(1576749712000L)
                }
                
                addSelf(link<AdministrativEnhetResource>("204229"))
                addOrganisasjonselement(link<OrganisasjonselementResource>("560000", "organisasjonsid"))
            }
        )
    }
}
