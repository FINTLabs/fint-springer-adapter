package no.fint.provider.springer.model

import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.storage.SpringerRepository
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.resource.FintLinks
import no.novari.fint.model.resource.utdanning.utdanningsprogram.ArstrinnResource
import no.novari.fint.model.resource.utdanning.utdanningsprogram.SkoleResource
import no.novari.fint.model.utdanning.utdanningsprogram.UtdanningsprogramActions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class UtdanningsprogramRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper
) : SpringerRepository(mongoTemplate, wrapper) {

    override fun accept(response: Event<FintLinks>) {
        when (UtdanningsprogramActions.valueOf(response.action)) {
            UtdanningsprogramActions.GET_ALL_SKOLE -> query(SkoleResource::class.java, response)
            UtdanningsprogramActions.GET_ALL_ARSTRINN -> query(ArstrinnResource::class.java, response)
            else -> {
                response.status = Status.ADAPTER_REJECTED
                response.responseStatus = ResponseStatus.REJECTED
                response.statusCode = "INVALID_ACTION"
                response.message = "Invalid action"
            }
        }
    }

    override fun actions(): Set<String> =
    setOf(
        UtdanningsprogramActions.GET_ALL_SKOLE,
        UtdanningsprogramActions.GET_ALL_ARSTRINN
    ).map { it.name }
     .toSet()
}
