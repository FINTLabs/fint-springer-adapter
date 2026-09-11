package no.fint.provider.springer.model

import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.storage.SpringerRepository
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.resource.FintLinks
import no.novari.fint.model.resource.utdanning.timeplan.FagResource
import no.novari.fint.model.resource.utdanning.timeplan.RomResource
import no.novari.fint.model.resource.utdanning.timeplan.TimeResource
import no.novari.fint.model.resource.utdanning.timeplan.UndervisningsgruppeResource
import no.novari.fint.model.resource.utdanning.timeplan.UndervisningsgruppemedlemskapResource
import no.novari.fint.model.utdanning.timeplan.TimeplanActions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class TimeplanRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper
) : SpringerRepository(mongoTemplate, wrapper) {

    override fun accept(response: Event<FintLinks>) {
        when (TimeplanActions.valueOf(response.action)) {
            TimeplanActions.GET_ALL_FAG -> query(FagResource::class.java, response)
            TimeplanActions.GET_ALL_ROM -> query(RomResource::class.java, response)
            TimeplanActions.GET_ALL_TIME -> query(TimeResource::class.java, response)
            TimeplanActions.GET_ALL_UNDERVISNINGSGRUPPE -> query(UndervisningsgruppeResource::class.java, response)
            TimeplanActions.GET_ALL_UNDERVISNINGSGRUPPEMEDLEMSKAP -> query(UndervisningsgruppemedlemskapResource::class.java, response)
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
        TimeplanActions.entries
            .map { it.name }
            .filter { it.startsWith("GET_ALL_") }
            .toSet()
}
