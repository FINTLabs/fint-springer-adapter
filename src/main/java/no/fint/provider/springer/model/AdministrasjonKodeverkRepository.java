package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.administrasjon.kodeverk.KodeverkActions;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.administrasjon.kodeverk.*;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class AdministrasjonKodeverkRepository extends SpringerRepository {

    @Override
    public void accept(Event<FintLinks> response) {
        switch (KodeverkActions.valueOf(response.getAction())) {
            case GET_ALL_ARBEIDSFORHOLDSTYPE:
                query(ArbeidsforholdstypeResource.class, response);
                break;
            case GET_ALL_PERSONALRESSURSKATEGORI:
                query(PersonalressurskategoriResource.class, response);
                break;
            case GET_ALL_STILLINGSKODE:
                query(StillingskodeResource.class, response);
                break;
            case GET_ALL_ART:
                query(ArtResource.class, response);
                break;
            case GET_ALL_ANSVAR:
                query(AnsvarResource.class, response);
                break;
            case GET_ALL_FUNKSJON:
                query(FunksjonResource.class, response);
                break;
            case GET_ALL_PROSJEKT:
                query(ProsjektResource.class, response);
                break;
            case GET_ALL_LONNSART:
                query(LonnsartResource.class, response);
                break;
            case GET_ALL_FRAVARSGRUNN:
                query(FravarsgrunnResource.class, response);
                break;
            case GET_ALL_FRAVARSTYPE:
                query(FravarstypeResource.class, response);
                break;
            case GET_ALL_UKETIMETALL:
                query(UketimetallResource.class, response);
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
                .of(KodeverkActions.values())
                .map(Enum::name)
                .filter(s -> s.startsWith("GET_ALL_"))
                .collect(Collectors.toSet());
    }
}
