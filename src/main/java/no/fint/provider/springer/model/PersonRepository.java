package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.felles.FellesActions;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.felles.PersonResource;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class PersonRepository extends SpringerRepository {

    @Override
    public void accept(Event<FintLinks> response) {
        switch (FellesActions.valueOf(response.getAction())) {
            case GET_ALL_PERSON:
                query(PersonResource.class, response);
                break;
            case UPDATE_PERSON:
            default:
                response.setStatus(Status.ADAPTER_REJECTED);
                response.setResponseStatus(ResponseStatus.REJECTED);
                response.setStatusCode("INVALID_ACTION");
                response.setMessage("Invalid action");
        }
    }

    @Override
    public Set<String> actions() {
        return Stream.of(
                FellesActions.GET_ALL_PERSON,
                FellesActions.UPDATE_PERSON
        ).map(Enum::name).collect(Collectors.toSet());
    }

}
