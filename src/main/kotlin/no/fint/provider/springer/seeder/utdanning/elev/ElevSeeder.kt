package no.fint.provider.springer.seeder.utdanning.elev

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.felles.PersonResource
import no.novari.fint.model.resource.utdanning.elev.ElevResource
import no.novari.fint.model.resource.utdanning.elev.ElevforholdResource
import org.springframework.stereotype.Service
import java.util.Date

@Service
class ElevSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<ElevResource>(seederRepository, ElevResource::class.java) {

    fun generateEntitiesForTest(): List<ElevResource> = generateEntities()


    override fun generateEntities(): List<ElevResource> {
        return listOf(
            ElevResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "a0611475-f278-4e5f-ba1b-bdfe440b6f71" }
                elevnummer = Identifikator().apply { identifikatorverdi = "500001" }
                brukernavn = Identifikator().apply {
                    identifikatorverdi = "Yougung"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1275609600000L)
                        slutt = Date(4102444800000L)
                    }
                }
                feidenavn = Identifikator().apply { identifikatorverdi = "leonahansen@sundetvgs.haugfk.no" }
                addSelf(link<ElevResource>("Yougung", "brukernavn"))
                addPerson(link<PersonResource>("14029923273", "fodselsnummer"))
                addElevforhold(link<ElevforholdResource>("E_500001_1"))
            },
            ElevResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "d0df6fa9-e058-40d9-aa8c-46fc6c8c7ac9" }
                elevnummer = Identifikator().apply { identifikatorverdi = "500002" }
                brukernavn = Identifikator().apply {
                    identifikatorverdi = "Pulithey1933"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1275609600000L)
                        slutt = Date(4102444800000L)
                    }
                }
                addSelf(link<ElevResource>("d0df6fa9-e058-40d9-aa8c-46fc6c8c7ac9"))
                addPerson(link<PersonResource>("18010197461", "fodselsnummer"))
                addElevforhold(link<ElevforholdResource>("E_500002_1"))
            },
            ElevResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "0482e5cc-d95c-4d6d-b0b9-6bd103b8f57b" }
                elevnummer = Identifikator().apply { identifikatorverdi = "500003" }
                brukernavn = Identifikator().apply {
                    identifikatorverdi = "Daref1968"
                    gyldighetsperiode = Periode().apply {
                        start = Date(1275609600000L)
                        slutt = Date(4102444800000L)
                    }
                }
                addSelf(link<ElevResource>("0482e5cc-d95c-4d6d-b0b9-6bd103b8f57b"))
                addPerson(link<PersonResource>("11010159115", "fodselsnummer"))
                addElevforhold(link<ElevforholdResource>("E_500003_1"))
            },
            ElevResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "500011" }
                elevnummer = Identifikator().apply { identifikatorverdi = "500011" }
                brukernavn = Identifikator().apply { identifikatorverdi = "oleduck" }
                feidenavn = Identifikator().apply { identifikatorverdi = "ole@andebymail.no" }
                addSelf(link<ElevResource>("500011"))
                addPerson(link<PersonResource>("11011112345", "fodselsnummer"))
                addElevforhold(link<ElevforholdResource>("500011-1"))
            },
            ElevResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "500012" }
                elevnummer = Identifikator().apply { identifikatorverdi = "500012" }
                brukernavn = Identifikator().apply { identifikatorverdi = "doleduck" }
                feidenavn = Identifikator().apply { identifikatorverdi = "dole@andebymail.no" }
                addSelf(link<ElevResource>("500012"))
                addPerson(link<PersonResource>("12011112345", "fodselsnummer"))
                addElevforhold(link<ElevforholdResource>("500012-1"))
            },
            ElevResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "500013" }
                elevnummer = Identifikator().apply { identifikatorverdi = "500013" }
                brukernavn = Identifikator().apply { identifikatorverdi = "doffenduck" }
                feidenavn = Identifikator().apply { identifikatorverdi = "doffen@andebymail.no" }
                addSelf(link<ElevResource>("500013"))
                addPerson(link<PersonResource>("13011112345", "fodselsnummer"))
                addElevforhold(link<ElevforholdResource>("500013-1"))
            },
            ElevResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = "500014" }
                elevnummer = Identifikator().apply { identifikatorverdi = "500014" }
                brukernavn = Identifikator().apply { identifikatorverdi = "hettiduck" }
                feidenavn = Identifikator().apply { identifikatorverdi = "hetti@andebymail.no" }
                addSelf(link<ElevResource>("500014"))
                addPerson(link<PersonResource>("14011112345", "fodselsnummer"))
                addElevforhold(link<ElevforholdResource>("500014-1"))
            }
        )
    }
}
