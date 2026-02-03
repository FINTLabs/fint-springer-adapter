package no.fint.provider.springer.model

import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.storage.SpringerRepository
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.administrasjon.organisasjon.OrganisasjonActions
import no.novari.fint.model.resource.FintLinks
import no.novari.fint.model.resource.administrasjon.organisasjon.OrganisasjonselementResource
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.stream.Collectors
import java.util.stream.Stream

@Repository
class OrganisasjonRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper
) : SpringerRepository(mongoTemplate, wrapper) {

    override fun accept(response: Event<FintLinks>) {
        when (OrganisasjonActions.valueOf(response.action)) {
            OrganisasjonActions.GET_ALL_ORGANISASJONSELEMENT -> query(OrganisasjonselementResource::class.java, response)
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
        Stream.of(OrganisasjonActions.GET_ALL_ORGANISASJONSELEMENT)
            .map { it.name }
            .collect(Collectors.toSet())
}
