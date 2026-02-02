package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.novari.fint.model.okonomi.kodeverk.KodeverkActions;
import no.novari.fint.model.resource.FintLinks;
import no.novari.fint.model.resource.okonomi.kodeverk.MerverdiavgiftResource;
import no.novari.fint.model.resource.okonomi.kodeverk.VareResource;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class OkonomiKodeverkRepository extends SpringerRepository {

    @Override
    public void accept(Event<FintLinks> response) {
        log.debug("OkonomiKodeverk-handler request accept for " + response.getAction());

        switch (KodeverkActions.valueOf(response.getAction())) {
            case GET_ALL_MERVERDIAVGIFT:
                log.debug("Start fetching merverdiavgift");
                query(MerverdiavgiftResource.class, response);
                log.debug("Added " + response.getData().stream().count() + " merverdiavgif");
                break;
            case GET_ALL_VARE:
                log.debug("Start fetching vare");
                query(VareResource.class, response);
                log.debug("Added " + response.getData().stream().count() + " varer");
                break;
            default:
                log.debug("OkonomiKodeverk-handler request default - REJECTED");
                response.setStatus(Status.ADAPTER_REJECTED);
                response.setResponseStatus(ResponseStatus.REJECTED);
                response.setStatusCode("INVALID_ACTION");
                response.setMessage("Invalid action");
        }
    }

    @Override
    public Set<String> actions() {
        return Stream
                .of(KodeverkActions.values())
                .map(Enum::name)
                .filter(s -> s.startsWith("GET_ALL_"))
                .collect(Collectors.toSet());
    }
}
