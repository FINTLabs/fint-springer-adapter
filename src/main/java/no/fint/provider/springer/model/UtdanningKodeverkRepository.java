package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.utdanning.kodeverk.ElevkategoriResource;
import no.fint.model.resource.utdanning.kodeverk.FravarstypeResource;
import no.fint.model.resource.utdanning.kodeverk.KarakterskalaResource;
import no.fint.model.resource.utdanning.kodeverk.SkoleeiertypeResource;
import no.fint.model.utdanning.kodeverk.KodeverkActions;
import no.fint.provider.springer.storage.SpringerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Set;
import java.util.stream.Collectors;

import static no.fint.model.utdanning.kodeverk.KodeverkActions.*;

@Slf4j
@Repository
public class UtdanningKodeverkRepository extends SpringerRepository {

    private final EnumMap<KodeverkActions, Class<? extends FintLinks>> actions = new EnumMap<>(KodeverkActions.class);

    @PostConstruct
    public void init() {
        actions.put(GET_ALL_ELEVKATEGORI, ElevkategoriResource.class);
        actions.put(GET_ALL_FRAVARSTYPE, FravarstypeResource.class);
        actions.put(GET_ALL_KARAKTERSKALA, KarakterskalaResource.class);
        actions.put(GET_ALL_SKOLEEIERTYPE, SkoleeiertypeResource.class);
    }

    @Override
    public void accept(Event<FintLinks> response) {
        if (!StringUtils.contains(response.getSource(), "utdanning")) {
            log.info("Skipping {} from {}", response.getAction(), response.getSource());
            return;
        }
        KodeverkActions action = valueOf(response.getAction());
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
