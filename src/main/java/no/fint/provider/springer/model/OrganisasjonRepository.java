package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.administrasjon.organisasjon.OrganisasjonActions;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.administrasjon.organisasjon.OrganisasjonselementResource;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class OrganisasjonRepository extends SpringerRepository {

    @Override
    public void accept(Event<FintLinks> response) {
        switch (OrganisasjonActions.valueOf(response.getAction())) {
            case GET_ALL_ORGANISASJONSELEMENT:
                query(OrganisasjonselementResource.class, response);
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
                .of(
                        OrganisasjonActions.GET_ALL_ORGANISASJONSELEMENT
                )
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
