package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.utdanning.elev.*;
import no.fint.model.utdanning.elev.ElevActions;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static no.fint.model.utdanning.elev.ElevActions.*;

@Slf4j
@Repository
public class ElevRepository extends SpringerRepository {

    @Override
    public void accept(Event<FintLinks> response) {
        switch (ElevActions.valueOf(response.getAction())) {
            case GET_ALL_BASISGRUPPE:
                query(BasisgruppeResource.class, response);
                break;
            case GET_ALL_ELEV:
                query(ElevResource.class, response);
                break;
            case GET_ALL_ELEVFORHOLD:
                query(ElevforholdResource.class, response);
                break;
            case GET_ALL_KONTAKTLARERGRUPPE:
                query(KontaktlarergruppeResource.class, response);
                break;
            case GET_ALL_SKOLERESSURS:
                query(SkoleressursResource.class, response);
                break;
            case GET_ALL_UNDERVISNINGSFORHOLD:
                query(UndervisningsforholdResource.class, response);
                break;
        }
    }

    @Override
    public Set<String> actions() {
        return Stream
                .of(
                        GET_ALL_BASISGRUPPE,
                        GET_ALL_ELEV,
                        GET_ALL_ELEVFORHOLD,
                        GET_ALL_KONTAKTLARERGRUPPE,
                        GET_ALL_SKOLERESSURS,
                        GET_ALL_UNDERVISNINGSFORHOLD)
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
