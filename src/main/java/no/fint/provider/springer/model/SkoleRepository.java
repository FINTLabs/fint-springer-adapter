package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.novari.fint.model.resource.FintLinks;
import no.novari.fint.model.resource.utdanning.utdanningsprogram.SkoleResource;
import no.novari.fint.model.utdanning.utdanningsprogram.UtdanningsprogramActions;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class SkoleRepository extends SpringerRepository {

    @Override
    public void accept(Event<FintLinks> response) {
        if (UtdanningsprogramActions.valueOf(response.getAction()) == UtdanningsprogramActions.GET_ALL_SKOLE) {
            query(SkoleResource.class, response);
        }
    }

    @Override
    public Set<String> actions() {
        return Stream
            .of(UtdanningsprogramActions.GET_ALL_SKOLE)
            .map(Enum::name)
            .collect(Collectors.toSet());
    }
}
