package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.novari.fint.model.administrasjon.fullmakt.FullmaktActions;
import no.novari.fint.model.resource.FintLinks;
import no.novari.fint.model.resource.administrasjon.fullmakt.FullmaktResource;
import no.novari.fint.model.resource.administrasjon.fullmakt.RolleResource;
import no.fint.provider.springer.storage.SpringerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class FullmaktRepository extends SpringerRepository {

    @Override
    public void accept(Event<FintLinks> response) {
        if (!StringUtils.equals(response.getSource(), "administrasjon-fullmakt")) {
            log.info("Skipping {} from {}", response.getAction(), response.getSource());
            return;
        }

        switch (FullmaktActions.valueOf(response.getAction())) {
            case GET_ALL_FULLMAKT:
                query(FullmaktResource.class, response);
                break;
            case GET_ALL_ROLLE:
                query(RolleResource.class, response);
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
                .of(FullmaktActions.values())
                .map(Enum::name)
                .filter(s -> s.startsWith("GET_ALL_"))
                .collect(Collectors.toSet());
    }
}
