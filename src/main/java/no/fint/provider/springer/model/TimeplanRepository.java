package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.utdanning.timeplan.FagResource;
import no.fint.model.resource.utdanning.timeplan.RomResource;
import no.fint.model.resource.utdanning.timeplan.TimeResource;
import no.fint.model.resource.utdanning.timeplan.UndervisningsgruppeResource;
import no.fint.model.utdanning.timeplan.TimeplanActions;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class TimeplanRepository extends SpringerRepository {

    @Override
    public void accept(Event<FintLinks> response) {
        switch (TimeplanActions.valueOf(response.getAction())) {
            case GET_ALL_FAG:
                query(FagResource.class, response);
                break;
            case GET_ALL_ROM:
                query(RomResource.class, response);
                break;
            case GET_ALL_TIME:
                query(TimeResource.class, response);
                break;
            case GET_ALL_UNDERVISNINGSGRUPPE:
                query(UndervisningsgruppeResource.class, response);
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
            .of(TimeplanActions.values())
            .map(Enum::name)
            .filter(s -> s.startsWith("GET_ALL_"))
            .collect(Collectors.toSet());
    }
}
