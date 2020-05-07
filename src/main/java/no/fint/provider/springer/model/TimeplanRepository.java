package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
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

import static no.fint.model.utdanning.timeplan.TimeplanActions.*;

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
        }
    }

    @Override
    public Set<String> actions() {
        return Stream
            .of(GET_ALL_FAG, GET_ALL_ROM, GET_ALL_TIME, GET_ALL_UNDERVISNINGSGRUPPE)
            .map(Enum::name)
            .collect(Collectors.toSet());
    }
}
