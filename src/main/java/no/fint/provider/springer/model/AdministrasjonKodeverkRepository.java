package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.novari.fint.model.administrasjon.kodeverk.KodeverkActions;
import no.novari.fint.model.resource.FintLinks;
import no.novari.fint.model.resource.administrasjon.kodeverk.*;
import no.fint.provider.springer.storage.SpringerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static no.novari.fint.model.administrasjon.kodeverk.KodeverkActions.*;

@Slf4j
@Repository
public class AdministrasjonKodeverkRepository extends SpringerRepository {

    private final EnumMap<KodeverkActions, Class<? extends FintLinks>> actions = new EnumMap<>(KodeverkActions.class);

    @PostConstruct
    public void init() {
        actions.put(GET_ALL_ARBEIDSFORHOLDSTYPE, ArbeidsforholdstypeResource.class);
        actions.put(GET_ALL_PERSONALRESSURSKATEGORI, PersonalressurskategoriResource.class);
        actions.put(GET_ALL_STILLINGSKODE, StillingskodeResource.class);
        actions.put(GET_ALL_ART, ArtResource.class);
        actions.put(GET_ALL_ANSVAR, AnsvarResource.class);
        actions.put(GET_ALL_FUNKSJON, FunksjonResource.class);
        actions.put(GET_ALL_PROSJEKT, ProsjektResource.class);
        actions.put(GET_ALL_LONNSART, LonnsartResource.class);
        actions.put(GET_ALL_FRAVARSGRUNN, FravarsgrunnResource.class);
        actions.put(GET_ALL_FRAVARSTYPE, FravarstypeResource.class);
        actions.put(GET_ALL_UKETIMETALL, UketimetallResource.class);
    }

    @Override
    public void accept(Event<FintLinks> response) {
        if (!StringUtils.contains(response.getSource(), "administrasjon")) {
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
        return Stream
                .of(KodeverkActions.values())
                .map(Enum::name)
                .filter(s -> s.startsWith("GET_ALL_"))
                .collect(Collectors.toSet());
    }
}
