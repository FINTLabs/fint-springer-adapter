package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.administrasjon.okonomi.OkonomiActions;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.administrasjon.okonomi.MvakodeResource;
import no.fint.model.resource.administrasjon.okonomi.OppdragsgiverResource;
import no.fint.model.resource.administrasjon.okonomi.VarelinjeResource;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class BetalingRepository extends SpringerRepository {

    @Override
    public void accept(Event<FintLinks> response) {
        switch (OkonomiActions.valueOf(response.getAction())) {
            case GET_ALL_MVAKODE:
                query(MvakodeResource.class, response);
                break;
            case GET_ALL_OPPDRAGSGIVER:
                query(OppdragsgiverResource.class, response);
                break;
            case GET_ALL_VARELINJE:
                query(VarelinjeResource.class, response);
                break;
            default:
                response.setStatus(Status.ADAPTER_REJECTED);
                response.setResponseStatus(ResponseStatus.REJECTED);
                response.setStatusCode("INVALID_ACTION");
                response.setMessage("Invalid action");
        }
    }

    @Override
    public Set<String> actions() {
        return Stream
                .of(OkonomiActions.values())
                .map(Enum::name)
                .filter(s -> s.startsWith("GET_ALL_"))
                .collect(Collectors.toSet());
    }
}
