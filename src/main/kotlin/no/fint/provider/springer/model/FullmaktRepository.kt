package no.fint.provider.springer.model

import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.storage.SpringerRepository
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.administrasjon.fullmakt.FullmaktActions
import no.novari.fint.model.resource.FintLinks
import no.novari.fint.model.resource.administrasjon.fullmakt.FullmaktResource
import no.novari.fint.model.resource.administrasjon.fullmakt.RolleResource
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.stream.Collectors
import java.util.stream.Stream

@Repository
class FullmaktRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper
) : SpringerRepository(mongoTemplate, wrapper) {

    override fun accept(response: Event<FintLinks>) {
        if (response.source != "administrasjon-fullmakt") {
            return
        }
        when (FullmaktActions.valueOf(response.action)) {
            FullmaktActions.GET_ALL_FULLMAKT -> query(FullmaktResource::class.java, response)
            FullmaktActions.GET_ALL_ROLLE -> query(RolleResource::class.java, response)
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
        Stream.of(*FullmaktActions.values())
            .map { it.name }
            .filter { it.startsWith("GET_ALL_") }
            .collect(Collectors.toSet())
}
