package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.administrasjon.personal.PersonalActions;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.administrasjon.personal.*;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class PersonalRepository extends SpringerRepository {

    @Override
    public void accept(Event<FintLinks> response) {
        switch (PersonalActions.valueOf(response.getAction())) {
            case GET_ALL_PERSONALRESSURS:
                query(PersonalressursResource.class, response);
                break;
            case GET_ALL_ARBEIDSFORHOLD:
                query(ArbeidsforholdResource.class, response);
                break;
            case GET_ALL_FRAVAR:
                query(FravarResource.class, response);
                break;
            case GET_ALL_FASTLONN:
                query(FastlonnResource.class, response);
                break;
            case GET_ALL_FASTTILLEGG:
                query(FasttilleggResource.class, response);
                break;
            case GET_ALL_VARIABELLONN:
                query(VariabellonnResource.class, response);
                break;
            case UPDATE_PERSONALRESSURS:
            case UPDATE_ARBEIDSFORHOLD:
                response.setStatus(Status.ADAPTER_REJECTED);
                response.setResponseStatus(ResponseStatus.REJECTED);
                response.setStatusCode("UNSUPPORTED_ACTION");
                response.setMessage("Unsupported action");
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
            .of(PersonalActions.values())
            .map(Enum::name)
            .filter(s -> s.startsWith("GET_ALL_"))
            .collect(Collectors.toSet());
    }
}
