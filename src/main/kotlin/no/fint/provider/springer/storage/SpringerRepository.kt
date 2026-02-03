package no.fint.provider.springer.storage

import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.fint.provider.springer.service.Handler
import no.novari.fint.model.resource.FintLinks
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.stream.Stream

@Repository
abstract class SpringerRepository(
    protected val mongoTemplate: MongoTemplate,
    protected val wrapper: Wrapper
) : Handler {

    protected fun query(type: Class<out FintLinks>, response: Event<FintLinks>) {
        stream(type).forEach { response.addData(it) }
        response.responseStatus = ResponseStatus.ACCEPTED
    }

    protected fun stream(type: Class<out FintLinks>): Stream<out FintLinks> =
        mongoTemplate.stream(wrapper.query(type), Springer::class.java)
            .map(wrapper.unwrapper(type))
}
