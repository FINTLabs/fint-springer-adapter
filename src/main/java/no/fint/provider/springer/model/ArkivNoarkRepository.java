package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.arkiv.noark.NoarkActions;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.arkiv.noark.AdministrativEnhetResource;
import no.fint.model.resource.arkiv.noark.ArkivdelResource;
import no.fint.model.resource.arkiv.noark.ArkivressursResource;
import no.fint.model.resource.arkiv.noark.KlassifikasjonssystemResource;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Set;
import java.util.stream.Collectors;

import static no.fint.model.arkiv.noark.NoarkActions.valueOf;

@Slf4j
@Repository
public class ArkivNoarkRepository extends SpringerRepository {

    private final EnumMap<NoarkActions, Class<? extends FintLinks>> actions = new EnumMap<>(NoarkActions.class);

    @PostConstruct
    public void init() {
        actions.put(NoarkActions.GET_ALL_ARKIVDEL, ArkivdelResource.class);
        actions.put(NoarkActions.GET_ALL_KLASSIFIKASJONSSYSTEM, KlassifikasjonssystemResource.class);
        actions.put(NoarkActions.GET_ALL_ADMINISTRATIVENHET, AdministrativEnhetResource.class);
        actions.put(NoarkActions.GET_ALL_ARKIVRESSURS, ArkivressursResource.class);
    }

    @Override
    public void accept(Event<FintLinks> response) {
        NoarkActions action = valueOf(response.getAction());
        if (actions.containsKey(action)) {
            query(actions.get(action), response);
        } else {
            response.setStatus(Status.ADAPTER_REJECTED);
            response.setResponseStatus(ResponseStatus.REJECTED);
            response.setStatusCode("INVALID_ACTION");
            response.setMessage("Invalid action: " + action);
        }
    }

    @Override
    public Set<String> actions() {
        return actions.keySet()
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
