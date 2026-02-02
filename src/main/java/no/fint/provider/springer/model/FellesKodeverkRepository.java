package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.novari.fint.model.felles.kodeverk.KodeverkActions;
import no.novari.fint.model.felles.kodeverk.iso.IsoActions;
import no.novari.fint.model.resource.FintLinks;
import no.novari.fint.model.resource.felles.kodeverk.FylkeResource;
import no.novari.fint.model.resource.felles.kodeverk.KommuneResource;
import no.novari.fint.model.resource.felles.kodeverk.iso.KjonnResource;
import no.novari.fint.model.resource.felles.kodeverk.iso.LandkodeResource;
import no.novari.fint.model.resource.felles.kodeverk.iso.SprakResource;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class FellesKodeverkRepository extends SpringerRepository {

    @Override
    public void accept(Event<FintLinks> response) {
        if (KodeverkActions.getActions().contains(response.getAction())) {
            switch (KodeverkActions.valueOf(response.getAction())) {
                case GET_ALL_FYLKE:
                    query(FylkeResource.class, response);
                    break;
                case GET_ALL_KOMMUNE:
                    query(KommuneResource.class, response);
                    break;
                default:
                    response.setStatus(Status.ADAPTER_REJECTED);
                    response.setResponseStatus(ResponseStatus.REJECTED);
                    response.setStatusCode("INVALID_ACTION");
                    response.setMessage("Invalid action");
            }
        } else if (IsoActions.getActions().contains(response.getAction())) {
            switch (IsoActions.valueOf(response.getAction())) {
                case GET_ALL_KJONN:
                    query(KjonnResource.class, response);
                    break;
                case GET_ALL_LANDKODE:
                    query(LandkodeResource.class, response);
                    break;
                case GET_ALL_SPRAK:
                    query(SprakResource.class, response);
                    break;
                default:
                    response.setStatus(Status.ADAPTER_REJECTED);
                    response.setResponseStatus(ResponseStatus.REJECTED);
                    response.setStatusCode("INVALID_ACTION");
                    response.setMessage("Invalid action");

            }
        } else {
            response.setStatus(Status.ADAPTER_REJECTED);
            response.setResponseStatus(ResponseStatus.REJECTED);
            response.setStatusCode("INVALID_ACTION");
            response.setMessage("Invalid action");

        }
    }

    @Override
    public Set<String> actions() {
        return Stream.concat(Stream.of(KodeverkActions.values()).map(Enum::name), Stream.of(IsoActions.values()).map(Enum::name))
                .filter(s -> s.startsWith("GET_ALL_"))
                .collect(Collectors.toSet());
    }
}
