package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.arkiv.kodeverk.KodeverkActions;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.arkiv.kodeverk.*;
import no.fint.provider.springer.storage.SpringerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ArkivKodeverkRepository extends SpringerRepository {

    private final EnumMap<KodeverkActions, Class<? extends FintLinks>> actions = new EnumMap<>(KodeverkActions.class);

    @PostConstruct
    public void init() {
        actions.put(KodeverkActions.GET_ALL_SAKSSTATUS, SaksstatusResource.class);
        actions.put(KodeverkActions.GET_ALL_SKJERMINGSHJEMMEL, SkjermingshjemmelResource.class);
        actions.put(KodeverkActions.GET_ALL_TILGANGSRESTRIKSJON, TilgangsrestriksjonResource.class);
        actions.put(KodeverkActions.GET_ALL_KLASSIFIKASJONSTYPE, KlassifikasjonstypeResource.class);
        actions.put(KodeverkActions.GET_ALL_DOKUMENTSTATUS, DokumentStatusResource.class);
        actions.put(KodeverkActions.GET_ALL_DOKUMENTTYPE, DokumentTypeResource.class);
        actions.put(KodeverkActions.GET_ALL_JOURNALSTATUS, JournalStatusResource.class);
        actions.put(KodeverkActions.GET_ALL_VARIANTFORMAT, VariantformatResource.class);
        actions.put(KodeverkActions.GET_ALL_ROLLE, RolleResource.class);
    }

    @Override
    public void accept(Event<FintLinks> response) {
        if (!StringUtils.equals(response.getSource(), "arkiv-kodeverk")) {
            log.info("Skipping {} from {}", response.getAction(), response.getSource());
            return;
        }

        KodeverkActions action = KodeverkActions.valueOf(response.getAction());
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
