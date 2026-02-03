package no.fint.provider.springer.model

import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.storage.SpringerRepository
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.resource.FintLinks
import no.novari.fint.model.arkiv.kodeverk.KodeverkActions
import no.novari.fint.model.resource.arkiv.kodeverk.*
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.stream.Collectors
import java.util.stream.Stream

@Repository
class ArkivKodeverkRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper
) : SpringerRepository(mongoTemplate, wrapper) {

    override fun accept(response: Event<FintLinks>) {
        when {
            KodeverkActions.getActions().contains(response.action) -> when (KodeverkActions.valueOf(response.action)) {
                KodeverkActions.GET_ALL_SAKSSTATUS -> query(SaksstatusResource::class.java, response)
                KodeverkActions.GET_ALL_SKJERMINGSHJEMMEL -> query(SkjermingshjemmelResource::class.java, response)
                KodeverkActions.GET_ALL_TILGANGSRESTRIKSJON -> query(TilgangsrestriksjonResource::class.java, response)
                KodeverkActions.GET_ALL_KLASSIFIKASJONSTYPE -> query(KlassifikasjonstypeResource::class.java, response)
                KodeverkActions.GET_ALL_DOKUMENTSTATUS -> query(DokumentStatusResource::class.java, response)
                KodeverkActions.GET_ALL_DOKUMENTTYPE -> query(DokumentTypeResource::class.java, response)
                KodeverkActions.GET_ALL_JOURNALSTATUS -> query(JournalStatusResource::class.java, response)
                KodeverkActions.GET_ALL_VARIANTFORMAT -> query(VariantformatResource::class.java, response)
                KodeverkActions.GET_ALL_ROLLE -> query(RolleResource::class.java, response)
                else -> invalid(response)
            }
            else -> invalid(response)
        }
    }

    private fun invalid(response: Event<FintLinks>) {
        response.status = Status.ADAPTER_REJECTED
        response.responseStatus = ResponseStatus.REJECTED
        response.statusCode = "INVALID_ACTION"
        response.message = "Invalid action"
    }

    override fun actions(): Set<String> =
        Stream.of(*KodeverkActions.values())
            .map { it.name }
            .filter { it.startsWith("GET_ALL_") }
            .collect(Collectors.toSet())
}
