package no.fint.provider.springer.storage

import com.fasterxml.jackson.databind.ObjectMapper
import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.novari.fint.model.resource.FintLinks
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class SeederRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper,
    private val objectMapper: ObjectMapper
) : SpringerRepository(mongoTemplate, wrapper) {

    fun <T> exists(type: Class<T>): Boolean {
        return mongoTemplate.count(wrapper.query(type), Springer::class.java) > 0L
    }

    fun <T> save(entity: T, type: Class<T>) {
        mongoTemplate.insert(wrapper.wrapper(type).invoke(entity))
    }

    fun <T> firstSystemIdOrNull(type: Class<T>): String? {
        val first = mongoTemplate.findOne(wrapper.query(type), Springer::class.java)
            ?.let(wrapper.unwrapper(type))
            ?: return null

        val systemIdNode = objectMapper.valueToTree<com.fasterxml.jackson.databind.JsonNode>(first).get("systemId")
            ?: return null
        return systemIdNode.get("identifikatorverdi")?.asText()
    }

    fun <T> existingSystemIds(type: Class<T>, limit: Int = 1000): List<String> {
        return mongoTemplate.find(wrapper.query(type).limit(limit), Springer::class.java)
            .asSequence()
            .map(wrapper.unwrapper(type))
            .mapNotNull { resource ->
                val systemIdNode = objectMapper.valueToTree<com.fasterxml.jackson.databind.JsonNode>(resource).get("systemId")
                    ?: return@mapNotNull null
                systemIdNode.get("identifikatorverdi")?.asText()
            }
            .toList()
    }

    override fun accept(response: Event<FintLinks>) {
        response.responseStatus = ResponseStatus.REJECTED
    }
}
