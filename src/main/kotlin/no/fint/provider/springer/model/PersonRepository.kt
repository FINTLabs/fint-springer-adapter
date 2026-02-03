package no.fint.provider.springer.model

import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.storage.SpringerRepository
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.felles.FellesActions
import no.novari.fint.model.resource.FintLinks
import no.novari.fint.model.resource.felles.KontaktpersonResource
import no.novari.fint.model.resource.felles.PersonResource
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.stream.Collectors
import java.util.stream.Stream

@Repository
class PersonRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper
) : SpringerRepository(mongoTemplate, wrapper) {

    override fun accept(response: Event<FintLinks>) {
        when (FellesActions.valueOf(response.action)) {
            FellesActions.GET_ALL_PERSON -> query(PersonResource::class.java, response)
            FellesActions.GET_ALL_KONTAKTPERSON -> query(KontaktpersonResource::class.java, response)
            else -> {
                response.status = Status.ADAPTER_REJECTED
                response.responseStatus = ResponseStatus.REJECTED
                response.statusCode = "INVALID_ACTION"
                response.message = "Invalid action"
            }
        }
    }

    override fun actions(): Set<String> =
        Stream.of(*FellesActions.values())
            .map { it.name }
            .filter { it.startsWith("GET_ALL_") }
            .collect(Collectors.toSet())
}
