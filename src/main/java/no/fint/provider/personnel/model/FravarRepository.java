package no.fint.provider.personnel.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.administrasjon.personal.PersonalActions;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.administrasjon.personal.FravarResource;
import no.fint.provider.personnel.service.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class FravarRepository implements Handler {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    IdentifikatorFactory identifikatorFactory;

    private Collection<FravarResource> repository = new ConcurrentLinkedQueue<>();

    @PostConstruct
    public void init() throws IOException {
        for (Resource r : new PathMatchingResourcePatternResolver(getClass().getClassLoader()).getResources("classpath*:/fravar*.json")) {
            repository.add(objectMapper.readValue(r.getInputStream(), FravarResource.class));
        }
    }

    @Override
    public EnumSet<PersonalActions> actions() {
        return EnumSet.of(PersonalActions.GET_ALL_FRAVAR, PersonalActions.UPDATE_FRAVAR);
    }

    @Override
    public void accept(Event<FintLinks> response) {
        log.debug("Handling {} ...", response);
        log.trace("Event data: {}", response.getData());
        try {
            switch (PersonalActions.valueOf(response.getAction())) {
                case UPDATE_FRAVAR:
                    List<FravarResource> data = objectMapper.convertValue(response.getData(), objectMapper.getTypeFactory().constructCollectionType(List.class, FravarResource.class));
                    log.trace("Converted data: {}", data);
                    data.stream().filter(i-> i.getSystemId()==null||i.getSystemId().getIdentifikatorverdi()==null).forEach(i->i.setSystemId(identifikatorFactory.create()));
                    data.forEach(r -> repository.removeIf(i -> i.getSystemId().getIdentifikatorverdi().equals(r.getSystemId().getIdentifikatorverdi())));
                    repository.addAll(data);
                    response.setResponseStatus(ResponseStatus.ACCEPTED);
                case GET_ALL_FRAVAR:
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
