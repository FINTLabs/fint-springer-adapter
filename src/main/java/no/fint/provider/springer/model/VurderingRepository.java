package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.utdanning.vurdering.EksamensgruppeResource;
import no.fint.model.resource.utdanning.vurdering.FravarResource;
import no.fint.model.resource.utdanning.vurdering.KarakterverdiResource;
import no.fint.model.resource.utdanning.vurdering.VurderingResource;
import no.fint.model.utdanning.vurdering.VurderingActions;
import no.fint.provider.springer.storage.SpringerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Set;
import java.util.stream.Collectors;

import static no.fint.model.utdanning.vurdering.VurderingActions.*;

@Slf4j
@Repository
public class VurderingRepository extends SpringerRepository {

    private final EnumMap<VurderingActions, Class<? extends FintLinks>> actions = new EnumMap<>(VurderingActions.class);

    @PostConstruct
    public void init() {
        actions.put(GET_ALL_EKSAMENSGRUPPE, EksamensgruppeResource.class);
        actions.put(GET_ALL_FRAVAR, FravarResource.class);
        actions.put(GET_ALL_KARAKTERVERDI, KarakterverdiResource.class);
        actions.put(GET_ALL_VURDERING, VurderingResource.class);
    }

    @Override
    public void accept(Event<FintLinks> response) {
        if (!StringUtils.contains(response.getSource(), "utdanning")) {
            log.info("Skipping {} from {}", response.getAction(), response.getSource());
            return;
        }

        VurderingActions action = valueOf(response.getAction());
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
