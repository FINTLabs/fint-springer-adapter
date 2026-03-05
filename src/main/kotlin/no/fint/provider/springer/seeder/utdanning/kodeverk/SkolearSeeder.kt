package no.fint.provider.springer.seeder.utdanning.kodeverk

import no.fint.provider.springer.seeder.AbstractSeeder
import no.fint.provider.springer.storage.SeederRepository
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.utdanning.kodeverk.SkolearResource
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date

@Service
class SkolearSeeder(
    seederRepository: SeederRepository
) : AbstractSeeder<SkolearResource>(seederRepository, SkolearResource::class.java) {

    fun generateEntitiesForTest(): List<SkolearResource> = generateEntities()

    fun yearsFrom(startYear: Int): IntRange =
        startYear..LocalDate.now(ZoneOffset.UTC).year

    override fun generateEntities(): List<SkolearResource> {
        return yearsFrom(2021)
            .map { skolearResource(it) }
    }

    private fun skolearResource(startAr: Int): SkolearResource {
        val startYear = if (startAr < 100) 2000 + startAr else startAr
        val endYear = startYear + 1
        val systemIdValue = "%02d-%02d".format(startYear % 100, endYear % 100)

        return SkolearResource().apply {
            systemId = Identifikator().apply { identifikatorverdi = systemIdValue }
            kode = "$startYear$endYear"
            navn = "$startYear$endYear"
            gyldighetsperiode = Periode().apply {
                start = Date.from(LocalDate.of(startYear, 8, 1).atStartOfDay().toInstant(ZoneOffset.UTC))
                slutt = Date.from(LocalDate.of(endYear, 7, 31).atStartOfDay().toInstant(ZoneOffset.UTC))
            }
            addSelf(link<SkolearResource>(systemIdValue))
        }
    }
}
