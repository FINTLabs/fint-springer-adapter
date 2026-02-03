package no.fint.provider.springer.model

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
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
import no.novari.fint.model.resource.administrasjon.personal.FasttilleggResource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.io.IOException
import java.util.concurrent.ConcurrentLinkedQueue

@Repository
class FasttilleggRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper,
    private val objectMapper: ObjectMapper,
    private val identifikatorFactory: IdentifikatorFactory,
    private val behaviours: List<Behaviour<FasttilleggResource>>
) : LonnRepository(mongoTemplate, wrapper) {

    private val repository: MutableCollection<FasttilleggResource> = ConcurrentLinkedQueue()

    @PostConstruct
    @Throws(IOException::class)
    fun init() {
        for (r in PathMatchingResourcePatternResolver(javaClass.classLoader).getResources("classpath*:/fasttillegg*.json")) {
            repository.add(objectMapper.readValue(r.inputStream, FasttilleggResource::class.java))
        }
    }

    override fun actions(): Set<String> = setOf(PersonalActions.UPDATE_FASTTILLEGG.name, PersonalActions.GET_ALL_FASTTILLEGG.name)

    override fun accept(response: Event<FintLinks>) {
        try {
            when (PersonalActions.valueOf(response.action)) {
                PersonalActions.UPDATE_FASTTILLEGG -> {
                    val data: List<FasttilleggResource> = objectMapper.convertValue(
                        response.data,
                        objectMapper.typeFactory.constructCollectionType(List::class.java, FasttilleggResource::class.java)
                    )
                    val conflicts = findConflicts(data, FasttilleggResource::class.java)
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
                        data.forEach { r -> repository.removeIf { i -> i.systemId?.identifikatorverdi == r.systemId?.identifikatorverdi } }
                        repository.addAll(data)
                    }
                }
                PersonalActions.GET_ALL_FASTTILLEGG -> {
                    response.responseStatus = ResponseStatus.ACCEPTED
                    response.data = ArrayList(repository)
                }
                else -> invalid(response)
            }
        } catch (e: Exception) {
            response.responseStatus = ResponseStatus.ERROR
            response.message = e.message
        }
    }

    private fun invalid(response: Event<FintLinks>) {
        response.status = Status.ADAPTER_REJECTED
        response.responseStatus = ResponseStatus.REJECTED
        response.statusCode = "INVALID_ACTION"
        response.message = "Invalid action"
    }
}
