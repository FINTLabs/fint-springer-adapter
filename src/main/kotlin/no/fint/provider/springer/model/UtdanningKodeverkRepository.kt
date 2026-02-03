package no.fint.provider.springer.model

import jakarta.annotation.PostConstruct
import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.storage.SpringerRepository
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.resource.FintLinks
import no.novari.fint.model.resource.utdanning.kodeverk.*
import no.novari.fint.model.utdanning.kodeverk.KodeverkActions
import org.apache.commons.lang3.StringUtils
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Collectors

@Repository
class UtdanningKodeverkRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper
) : SpringerRepository(mongoTemplate, wrapper) {

    private val actions: EnumMap<KodeverkActions, Class<out FintLinks>> = EnumMap(KodeverkActions::class.java)

    @PostConstruct
    fun init() {
        actions[KodeverkActions.GET_ALL_ELEVKATEGORI] = ElevkategoriResource::class.java
        actions[KodeverkActions.GET_ALL_FRAVARSTYPE] = FravarstypeResource::class.java
        actions[KodeverkActions.GET_ALL_KARAKTERSKALA] = KarakterskalaResource::class.java
        actions[KodeverkActions.GET_ALL_SKOLEEIERTYPE] = SkoleeiertypeResource::class.java
        actions[KodeverkActions.GET_ALL_SKOLEAR] = SkolearResource::class.java
        actions[KodeverkActions.GET_ALL_TERMIN] = TerminResource::class.java
    }

    override fun accept(response: Event<FintLinks>) {
        if (!StringUtils.contains(response.source, "utdanning")) {
            return
        }
        val action = KodeverkActions.valueOf(response.action)
        val type = actions[action]
        if (type != null) {
            query(type, response)
        } else {
            response.status = Status.ADAPTER_REJECTED
            response.responseStatus = ResponseStatus.REJECTED
            response.statusCode = "INVALID_ACTION"
            response.message = "Invalid action: $action"
        }
    }

    override fun actions(): Set<String> =
        actions.keys.stream()
            .map { it.name }
            .collect(Collectors.toSet())
}
