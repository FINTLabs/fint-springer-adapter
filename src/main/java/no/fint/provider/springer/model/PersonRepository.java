package no.fint.provider.springer.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.Operation;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.felles.FellesActions;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.felles.KontaktpersonResource;
import no.fint.model.resource.felles.PersonResource;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class PersonRepository extends SpringerRepository {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void accept(Event<FintLinks> response) {
        switch (FellesActions.valueOf(response.getAction())) {
            case GET_ALL_PERSON:
            case GET_PERSON:
                query(PersonResource.class, response);
                break;
            case GET_ALL_KONTAKTPERSON:
            case GET_KONTAKTPERSON:
                query(KontaktpersonResource.class, response);
                break;
            case UPDATE_PERSON:
                if (response.getOperation() == Operation.CREATE && response.getData().size() == 1) {
                    PersonResource resource = objectMapper.convertValue(response.getData().get(0), PersonResource.class);
                    mongoTemplate.insert(wrapper.wrapper(PersonResource.class).apply(resource));
                    break;
                }
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
                .of(FellesActions.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

}
