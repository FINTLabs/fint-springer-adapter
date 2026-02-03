package no.fint.provider.springer.storage

import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.BasicDBObject
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class Wrapper(
    private val objectMapper: ObjectMapper
) {
    fun wrapper(type: Class<*>): (Any?) -> Springer = { content: Any? ->
        val basicDBObject = BasicDBObject.parse(objectMapper.writeValueAsString(content))
        Springer(null, type.canonicalName, basicDBObject)
    }

    fun <T> unwrapper(type: Class<T>): (Springer) -> T = { springer ->
        objectMapper.convertValue(springer.value, type)
    }

    fun <T> query(type: Class<T>): Query = Query().addCriteria(Criteria.where("type").`is`(type.canonicalName))

    fun <T> update(springer: Springer, content: T): Springer {
        springer.value = BasicDBObject.parse(objectMapper.writeValueAsString(content))
        return springer
    }
}
