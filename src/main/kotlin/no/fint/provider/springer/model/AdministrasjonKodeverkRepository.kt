package no.fint.provider.springer.model

import jakarta.annotation.PostConstruct
import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.storage.SpringerRepository
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.administrasjon.kodeverk.KodeverkActions
import no.novari.fint.model.resource.FintLinks
import no.novari.fint.model.resource.administrasjon.kodeverk.*
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

@Repository
class AdministrasjonKodeverkRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper
) : SpringerRepository(mongoTemplate, wrapper) {

    private val actionsMap: EnumMap<KodeverkActions, Class<out FintLinks>> = EnumMap(KodeverkActions::class.java)

    @PostConstruct
    fun init() {
        actionsMap[KodeverkActions.GET_ALL_ARBEIDSFORHOLDSTYPE] = ArbeidsforholdstypeResource::class.java
        actionsMap[KodeverkActions.GET_ALL_PERSONALRESSURSKATEGORI] = PersonalressurskategoriResource::class.java
        actionsMap[KodeverkActions.GET_ALL_STILLINGSKODE] = StillingskodeResource::class.java
        actionsMap[KodeverkActions.GET_ALL_ART] = ArtResource::class.java
        actionsMap[KodeverkActions.GET_ALL_ANSVAR] = AnsvarResource::class.java
        actionsMap[KodeverkActions.GET_ALL_FUNKSJON] = FunksjonResource::class.java
        actionsMap[KodeverkActions.GET_ALL_PROSJEKT] = ProsjektResource::class.java
        actionsMap[KodeverkActions.GET_ALL_LONNSART] = LonnsartResource::class.java
        actionsMap[KodeverkActions.GET_ALL_FRAVARSGRUNN] = FravarsgrunnResource::class.java
        actionsMap[KodeverkActions.GET_ALL_FRAVARSTYPE] = FravarstypeResource::class.java
        actionsMap[KodeverkActions.GET_ALL_UKETIMETALL] = UketimetallResource::class.java
    }

    override fun accept(response: Event<FintLinks>) {
        if (!response.source.contains("administrasjon")) {
            return
        }
        val action = KodeverkActions.valueOf(response.action)
        val type = actionsMap[action]
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
        Stream.of(*KodeverkActions.values())
            .map { it.name }
            .filter { it.startsWith("GET_ALL_") }
            .collect(Collectors.toSet())
}
