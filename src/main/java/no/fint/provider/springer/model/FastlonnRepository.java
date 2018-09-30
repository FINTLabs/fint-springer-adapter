package no.fint.provider.springer.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.administrasjon.personal.PersonalActions;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.administrasjon.personal.FastlonnResource;
import no.fint.provider.springer.behaviour.Behaviour;
import no.fint.provider.springer.service.Handler;
import no.fint.provider.springer.service.IdentifikatorFactory;
import no.fint.provider.springer.storage.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class FastlonnRepository implements Handler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IdentifikatorFactory identifikatorFactory;

    @Autowired
    private List<Behaviour<FastlonnResource>> behaviours;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Wrapper wrapper;

    @Override
    public Set<String> actions() {
        return Stream.of(PersonalActions.UPDATE_FASTLONN).map(Enum::name).collect(Collectors.toSet());
    }

    @Override
    public void accept(Event<FintLinks> response) {
        log.debug("Handling {} ...", response);
        log.trace("Event data: {}", response.getData());
        try {
            switch (PersonalActions.valueOf(response.getAction())) {
                case UPDATE_FASTLONN:
                    List<FastlonnResource> data = objectMapper.convertValue(response.getData(), objectMapper.getTypeFactory().constructCollectionType(List.class, FastlonnResource.class));
                    log.trace("Converted data: {}", data);
                    data.stream().filter(i-> i.getSystemId()==null||i.getSystemId().getIdentifikatorverdi()==null).forEach(i->i.setSystemId(identifikatorFactory.create()));
                    response.setResponseStatus(ResponseStatus.ACCEPTED);
                    response.setData(null);
                    behaviours.forEach(b -> data.forEach(b.acceptPartially(response)));
                    if (response.getResponseStatus() == ResponseStatus.ACCEPTED) {
                        response.setData(new ArrayList<>(data));
                        data.stream().map(wrapper.wrapper(FastlonnResource.class)).forEach(mongoTemplate::insert);
                    }
                    break;
                default:
                    response.setStatus(Status.ADAPTER_REJECTED);
                    response.setResponseStatus(ResponseStatus.REJECTED);
                    response.setStatusCode("INVALID_ACTION");
                    response.setMessage("Invalid action");
            }
        } catch (Exception e) {
            log.error("Error!", e);
            response.setResponseStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }
    }

}
