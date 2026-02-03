package no.fint.provider.springer.model

import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.storage.SpringerRepository
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.felles.kodeverk.KodeverkActions
import no.novari.fint.model.felles.kodeverk.iso.IsoActions
import no.novari.fint.model.resource.FintLinks
import no.novari.fint.model.resource.felles.kodeverk.FylkeResource
import no.novari.fint.model.resource.felles.kodeverk.KommuneResource
import no.novari.fint.model.resource.felles.kodeverk.iso.KjonnResource
import no.novari.fint.model.resource.felles.kodeverk.iso.LandkodeResource
import no.novari.fint.model.resource.felles.kodeverk.iso.SprakResource
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.stream.Collectors
import java.util.stream.Stream

@Repository
class FellesKodeverkRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper
) : SpringerRepository(mongoTemplate, wrapper) {

    override fun accept(response: Event<FintLinks>) {
        when {
            KodeverkActions.getActions().contains(response.action) -> when (KodeverkActions.valueOf(response.action)) {
                KodeverkActions.GET_ALL_FYLKE -> query(FylkeResource::class.java, response)
                KodeverkActions.GET_ALL_KOMMUNE -> query(KommuneResource::class.java, response)
                else -> invalid(response)
            }
            IsoActions.getActions().contains(response.action) -> when (IsoActions.valueOf(response.action)) {
                IsoActions.GET_ALL_KJONN -> query(KjonnResource::class.java, response)
                IsoActions.GET_ALL_LANDKODE -> query(LandkodeResource::class.java, response)
                IsoActions.GET_ALL_SPRAK -> query(SprakResource::class.java, response)
                else -> invalid(response)
            }
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
        Stream.concat(
            Stream.of(*KodeverkActions.values()).map { it.name },
            Stream.of(*IsoActions.values()).map { it.name }
        )
            .filter { it.startsWith("GET_ALL_") }
            .collect(Collectors.toSet())
}
