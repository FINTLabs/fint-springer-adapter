package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.utdanning.utdanningsprogram.ArstrinnResource;
import no.fint.model.resource.utdanning.utdanningsprogram.ProgramomradeResource;
import no.fint.model.resource.utdanning.utdanningsprogram.SkoleResource;
import no.fint.model.resource.utdanning.utdanningsprogram.UtdanningsprogramResource;
import no.fint.model.utdanning.utdanningsprogram.UtdanningsprogramActions;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class UtdanningsprogramRepository extends SpringerRepository {

    @Override
    public void accept(Event<FintLinks> response) {
        switch (UtdanningsprogramActions.valueOf(response.getAction())) {
            case GET_ALL_ARSTRINN:
                query(ArstrinnResource.class, response);
                break;
            case GET_ALL_PROGRAMOMRADE:
                query(ProgramomradeResource.class, response);
                break;
            case GET_ALL_SKOLE:
                query(SkoleResource.class, response);
                break;
            case GET_ALL_UTDANNINGSPROGRAM:
                query(UtdanningsprogramResource.class, response);
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
            .of(UtdanningsprogramActions.values())
            .map(Enum::name)
            .filter(s -> s.startsWith("GET_ALL_"))
            .collect(Collectors.toSet());
    }
}
