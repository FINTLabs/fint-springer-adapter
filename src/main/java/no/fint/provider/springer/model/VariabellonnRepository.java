package no.fint.provider.springer.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.administrasjon.personal.PersonalActions;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.administrasjon.personal.VariabellonnResource;
import no.fint.provider.springer.behaviour.Behaviour;
import no.fint.provider.springer.service.Handler;
import no.fint.provider.springer.service.IdentifikatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class VariabellonnRepository implements Handler {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    IdentifikatorFactory identifikatorFactory;

    @Autowired
    List<Behaviour<VariabellonnResource>> behaviours;

    private Collection<VariabellonnResource> repository = new ConcurrentLinkedQueue<>();

    @PostConstruct
    public void init() throws IOException {
        for (Resource r : new PathMatchingResourcePatternResolver(getClass().getClassLoader()).getResources("classpath*:/variabellonn*.json")) {
            repository.add(objectMapper.readValue(r.getInputStream(), VariabellonnResource.class));
        }
    }

    @Override
    public Set<String> actions() {
        return Stream.of(PersonalActions.UPDATE_VARIABELLONN).map(Enum::name).collect(Collectors.toSet());
    }

    @Override
    public void accept(Event<FintLinks> response) {
        log.debug("Handling {} ...", response);
        log.trace("Event data: {}", response.getData());
        try {
            switch (PersonalActions.valueOf(response.getAction())) {
                case UPDATE_VARIABELLONN:
                    List<VariabellonnResource> data = objectMapper.convertValue(response.getData(), objectMapper.getTypeFactory().constructCollectionType(List.class, VariabellonnResource.class));
                    log.trace("Converted data: {}", data);
                    data.stream().filter(i-> i.getSystemId()==null||i.getSystemId().getIdentifikatorverdi()==null).forEach(i->i.setSystemId(identifikatorFactory.create()));
                    response.setResponseStatus(ResponseStatus.ACCEPTED);
                    response.setData(null);
                    behaviours.forEach(b -> data.forEach(b.acceptPartially(response)));
                    if (response.getResponseStatus() == ResponseStatus.ACCEPTED) {
                        response.setData(new ArrayList<>(data));
                        data.forEach(r -> repository.removeIf(i -> i.getSystemId().getIdentifikatorverdi().equals(r.getSystemId().getIdentifikatorverdi())));
                        repository.addAll(data);
                    }
                    break;
                case GET_ALL_VARIABELLONN:
                    response.setResponseStatus(ResponseStatus.ACCEPTED);
                    response.setData(new ArrayList<>(repository));
                    break;
                default:
                    response.setStatus(Status.ADAPTER_REJECTED);
                    response.setResponseStatus(ResponseStatus.REJECTED);
                    response.setMessage("Invalid action");
            }
        } catch (Exception e) {
            log.error("Error!", e);
            response.setResponseStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }
    }

}
