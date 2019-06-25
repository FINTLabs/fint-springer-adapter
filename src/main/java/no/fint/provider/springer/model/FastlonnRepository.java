package no.fint.provider.springer.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.Operation;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.administrasjon.personal.PersonalActions;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.administrasjon.personal.FastlonnResource;
import no.fint.provider.springer.behaviour.Behaviour;
import no.fint.provider.springer.service.IdentifikatorFactory;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class FastlonnRepository extends SpringerRepository {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    IdentifikatorFactory identifikatorFactory;

    @Autowired
    List<Behaviour<FastlonnResource>> behaviours;

    @Override
    public Set<String> actions() {
        return Stream.of(PersonalActions.UPDATE_FASTLONN).map(Enum::name).collect(Collectors.toSet());
    }

    @Override
    public void accept(Event<FintLinks> response) {
        log.debug("Handling {} ...", response);
        log.trace("Event data: {}", response.getData());
        if (response.getOperation() != Operation.CREATE || response.getData() == null || response.getData().size() != 1) {
            error(response, Status.ADAPTER_REJECTED, ResponseStatus.REJECTED, "INVALID_UPDATE", "Invalid update");
            return;
        }
        FastlonnResource data = objectMapper.convertValue(response.getData().get(0), FastlonnResource.class);
        log.trace("Converted data: {}", data);
        data.setSystemId(identifikatorFactory.create());
        response.setResponseStatus(ResponseStatus.ACCEPTED);
        response.setData(null);
        behaviours.forEach(b -> b.acceptPartially(response));
        if (response.getResponseStatus() == ResponseStatus.ACCEPTED) {
            create(FastlonnResource.class, data);
            response.setData(Collections.singletonList(data));
        }
    }
}