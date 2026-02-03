package no.fint.provider.springer.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import no.fint.event.model.Event
import no.fint.event.model.Operation
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.service.Handler
import no.fint.provider.springer.storage.Springer
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.FintLinks
import org.apache.commons.beanutils.PropertyUtils
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.CriteriaDefinition
import java.beans.PropertyDescriptor
import java.util.*
import java.util.function.Consumer
import java.util.stream.Stream

abstract class UpdateHandler<T : FintLinks>(
    protected val mongoTemplate: MongoTemplate,
    protected val wrapper: Wrapper,
    protected val mapper: ObjectMapper,
    private val type: Class<T>
) : Handler {

    private val log = LoggerFactory.getLogger(UpdateHandler::class.java)

    protected fun validQuery(query: String): Boolean =
        Arrays.stream(PropertyUtils.getPropertyDescriptors(type))
            .filter { it.propertyType == Identifikator::class.java }
            .map(PropertyDescriptor::getName)
            .anyMatch { query.startsWith("$it/", ignoreCase = true) }

    protected fun reject(event: Event<FintLinks>, statusCode: String) {
        event.status = Status.ADAPTER_REJECTED
        event.responseStatus = ResponseStatus.REJECTED
        event.statusCode = statusCode
    }

    protected fun stream(criteria: CriteriaDefinition): Stream<T> =
        mongoTemplate.stream(wrapper.query(type).addCriteria(criteria), Springer::class.java)
            .map(wrapper.unwrapper(type))

    protected fun createCriteria(query: String): CriteriaDefinition {
        val split = StringUtils.split(query, '/')
        val field = Arrays.stream(PropertyUtils.getPropertyDescriptors(type))
            .filter { it.propertyType == Identifikator::class.java }
            .map(PropertyDescriptor::getName)
            .filter { it.equals(split[0], ignoreCase = true) }
            .findFirst()
            .orElseThrow { IllegalArgumentException() }
        return Criteria.where("value.%s.identifikatorverdi".format(field)).`is`(split[1])
    }

    protected fun <S> copy(target: S): Consumer<S> = Consumer { source ->
        Arrays.stream(PropertyUtils.getPropertyDescriptors(type))
            .map(PropertyDescriptor::getName)
            .forEach { name ->
                try {
                    if (!PropertyUtils.isReadable(source, name) || !PropertyUtils.isWriteable(target, name)) return@forEach
                    val value = PropertyUtils.getProperty(source, name)
                    if (value != null) {
                        PropertyUtils.setProperty(target, name, value)
                    }
                } catch (e: Exception) {
                    log.warn("Copy failed for property {}", name, e)
                }
            }
    }

    override fun accept(response: Event<FintLinks>) {
        if (response.operation != Operation.UPDATE) {
            reject(response, "INVALID_OPERATION")
            return
        }
        if (!validQuery(response.query)) {
            reject(response, "INVALID_QUERY")
            return
        }
        val updates: List<T> = mapper.convertValue(response.data, mapper.typeFactory.constructCollectionType(List::class.java, type))
        response.data = ArrayList()
        stream(createCriteria(response.query))
            .peek { existing -> updates.forEach(copy(existing)) }
            .forEach { response.addObject(it) }
        if (response.data.isEmpty()) {
            reject(response, "NOT_FOUND")
            return
        }
        response.responseStatus = ResponseStatus.ACCEPTED
        response.status = Status.ADAPTER_ACCEPTED
    }
}
