package no.fint.provider.springer.model

import com.fasterxml.jackson.databind.ObjectMapper
import no.fint.event.model.Event
import no.fint.event.model.Operation
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.behaviour.Behaviour
import no.fint.provider.springer.service.IdentifikatorFactory
import no.fint.provider.springer.storage.LonnRepository
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.administrasjon.personal.PersonalActions
import no.novari.fint.model.resource.FintLinks
import no.novari.fint.model.resource.administrasjon.personal.FastlonnResource
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class FastlonnRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper,
    private val objectMapper: ObjectMapper,
    private val identifikatorFactory: IdentifikatorFactory,
    private val behaviours: List<Behaviour<FastlonnResource>>
) : LonnRepository(mongoTemplate, wrapper) {

    private val log = LoggerFactory.getLogger(FastlonnRepository::class.java)

    override fun actions(): Set<String> = setOf(PersonalActions.UPDATE_FASTLONN.name)

    override fun accept(response: Event<FintLinks>) {
        log.debug("Handling {} ...", response)
        log.trace("Event data: {}", response.data)
        try {
            when (PersonalActions.valueOf(response.action)) {
                PersonalActions.UPDATE_FASTLONN -> {
                    val data: List<FastlonnResource> = objectMapper.convertValue(
                        response.data,
                        objectMapper.typeFactory.constructCollectionType(List::class.java, FastlonnResource::class.java)
                    )
                    log.trace("Converted data: {}", data)
                    val conflicts = findConflicts(data, FastlonnResource::class.java)
                    if (conflicts.isNotEmpty() && response.operation == Operation.CREATE) {
                        response.status = Status.ADAPTER_REJECTED
                        response.responseStatus = ResponseStatus.CONFLICT
                        response.data = ArrayList(conflicts)
                        return
                    }
                    data.filter { it.systemId?.identifikatorverdi == null }
                        .forEach { it.systemId = identifikatorFactory.create() }
                    response.responseStatus = ResponseStatus.ACCEPTED
                    response.data = null
                    behaviours.forEach { b -> data.forEach { b.accept(response, it) } }
                    if (response.responseStatus == ResponseStatus.ACCEPTED) {
                        response.data = ArrayList(data)
                        data.map(wrapper.wrapper(FastlonnResource::class.java)).forEach(mongoTemplate::insert)
                    }
                }
                else -> {
                    response.status = Status.ADAPTER_REJECTED
                    response.responseStatus = ResponseStatus.REJECTED
                    response.statusCode = "INVALID_ACTION"
                    response.message = "Invalid action"
                }
            }
        } catch (e: Exception) {
            log.error("Error!", e)
            response.responseStatus = ResponseStatus.ERROR
            response.message = e.message
        }
    }
}
