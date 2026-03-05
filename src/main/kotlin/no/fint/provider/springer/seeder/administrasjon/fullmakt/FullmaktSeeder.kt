package no.fint.provider.springer.seeder.administrasjon.fullmakt

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.fullmakt.FullmaktResource
import no.novari.fint.model.resource.administrasjon.fullmakt.RolleResource
import no.novari.fint.model.resource.administrasjon.kodeverk.AnsvarResource
import no.novari.fint.model.resource.administrasjon.personal.PersonalressursResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class FullmaktSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<FullmaktResource>(seederRepository, FullmaktResource::class.java) {

    fun generateEntitiesForTest(): List<FullmaktResource> = generateEntities()

    private inline fun <reified T> link(systemid: String, param: String = "systemid") =
        Link.with(T::class.java, param, systemid)

    override fun generateEntities(): List<FullmaktResource> {
        return listOf(
            FullmaktResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "ABC123" }
                gyldighetsperiode = Periode().apply {
                    start = Date(1581672191000L) // 2020-02-14T09:43:11Z
                    slutt = Date(1613207391000L)  // 2021-02-13T09:43:11Z
                }
                
                addSelf(link<FullmaktResource>("ABC123"))
                addFullmektig(link<PersonalressursResource>("100000", "ansattnummer"))
                addRolle(link<RolleResource>("attest", "navn"))
                addAnsvar(link<AnsvarResource>("391693"))
            },
            FullmaktResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "XYZ456" }
                gyldighetsperiode = Periode().apply {
                    start = Date(1581672192000L) // 2020-02-14T09:43:12Z
                    slutt = Date(1613207392000L)  // 2021-02-13T09:43:12Z
                }
                
                addSelf(link<FullmaktResource>("XYZ456"))
                addFullmektig(link<PersonalressursResource>("100000", "ansattnummer"))
                addRolle(link<RolleResource>("anvis", "navn"))
                addAnsvar(link<AnsvarResource>("391693"))
            }
        )
    }
}
