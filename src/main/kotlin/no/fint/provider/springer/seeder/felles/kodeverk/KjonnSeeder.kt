package no.fint.provider.springer.seeder.felles.kodeverk

import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.felles.kodeverk.iso.KjonnResource
import org.springframework.stereotype.Service

@Service
class KjonnSeeder(
    seederRepository: SeederRepository
) : BaseSeeder<KjonnResource>(seederRepository, KjonnResource::class.java) {

    fun generateEntitiesForTest(): List<KjonnResource> = generateEntities()

    private val kjonnData = listOf(
        "5" to "transkjønnet mann",
        "9" to "gjelder ikke",
        "0" to "uvisst",
        "4" to "transkjønnet kvinne",
        "1" to "mann",
        "2" to "kvinne",
        "8" to "ikke oppgitt"
    )

    override fun generateEntities(): List<KjonnResource> {
        return kjonnData.map { (kodeverdi, navnverdi) ->
            KjonnResource().apply {
                systemId = Identifikator().apply { identifikatorverdi = kodeverdi }
                kode = kodeverdi
                navn = navnverdi
                addSelf(link<KjonnResource>(kodeverdi))
            }
        }
    }
}
