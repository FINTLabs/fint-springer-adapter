package no.fint.provider.springer.model

import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.storage.SpringerRepository
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.resource.FintLinks
import no.novari.fint.model.resource.utdanning.elev.*
import no.novari.fint.model.utdanning.elev.ElevActions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.stream.Collectors
import java.util.stream.Stream

@Repository
class ElevRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper
) : SpringerRepository(mongoTemplate, wrapper) {

    override fun accept(response: Event<FintLinks>) {
        when (ElevActions.valueOf(response.action)) {
            ElevActions.GET_ALL_KLASSE -> query(KlasseResource::class.java, response)
            ElevActions.GET_ALL_KLASSEMEDLEMSKAP -> query(KlassemedlemskapResource::class.java, response)
            ElevActions.GET_ALL_ELEV -> query(ElevResource::class.java, response)
            ElevActions.GET_ALL_ELEVFORHOLD -> query(ElevforholdResource::class.java, response)
            ElevActions.GET_ALL_KONTAKTLARERGRUPPE -> query(KontaktlarergruppeResource::class.java, response)
            ElevActions.GET_ALL_SKOLERESSURS -> query(SkoleressursResource::class.java, response)
            ElevActions.GET_ALL_UNDERVISNINGSFORHOLD -> query(UndervisningsforholdResource::class.java, response)
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
        Stream.of(*ElevActions.values())
            .map { it.name }
            .filter { it.startsWith("GET_ALL_") }
            .collect(Collectors.toSet())
}
