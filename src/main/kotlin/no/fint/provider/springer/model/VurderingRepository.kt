package no.fint.provider.springer.model

import jakarta.annotation.PostConstruct
import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.storage.SpringerRepository
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.resource.FintLinks
import no.novari.fint.model.resource.utdanning.vurdering.EksamensgruppeResource
import no.novari.fint.model.resource.utdanning.vurdering.KarakterverdiResource
import no.novari.fint.model.utdanning.vurdering.VurderingActions
import org.apache.commons.lang3.StringUtils
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Collectors

@Repository
class VurderingRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper
) : SpringerRepository(mongoTemplate, wrapper) {

    private val actions: EnumMap<VurderingActions, Class<out FintLinks>> = EnumMap(VurderingActions::class.java)

    @PostConstruct
    fun init() {
        actions[VurderingActions.GET_ALL_EKSAMENSGRUPPE] = EksamensgruppeResource::class.java
        actions[VurderingActions.GET_ALL_KARAKTERVERDI] = KarakterverdiResource::class.java
    }

    override fun accept(response: Event<FintLinks>) {
        if (!StringUtils.contains(response.source, "utdanning")) {
            return
        }
        val action = VurderingActions.valueOf(response.action)
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

    override fun actions(): Set<String> = actions.keys.stream()
        .map { it.name }
        .collect(Collectors.toSet())
}
