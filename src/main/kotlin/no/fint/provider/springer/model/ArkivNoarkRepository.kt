package no.fint.provider.springer.model

import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.storage.SpringerRepository
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.arkiv.noark.NoarkActions
import no.novari.fint.model.resource.FintLinks
import no.novari.fint.model.resource.arkiv.noark.*
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.stream.Collectors
import java.util.stream.Stream

@Repository
class ArkivNoarkRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper
) : SpringerRepository(mongoTemplate, wrapper) {

    override fun accept(response: Event<FintLinks>) {
        when (NoarkActions.valueOf(response.action)) {
            NoarkActions.GET_ALL_KLASSIFIKASJONSSYSTEM -> query(KlassifikasjonssystemResource::class.java, response)
            NoarkActions.GET_ALL_ARKIVDEL -> query(ArkivdelResource::class.java, response)
            NoarkActions.GET_ALL_ADMINISTRATIVENHET -> query(AdministrativEnhetResource::class.java, response)
            NoarkActions.GET_ALL_ARKIVRESSURS -> query(ArkivressursResource::class.java, response)
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
        Stream.of(*NoarkActions.values())
            .map { it.name }
            .filter { it.startsWith("GET_ALL_") }
            .collect(Collectors.toSet())
}
