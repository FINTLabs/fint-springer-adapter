package no.fint.provider.springer.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.Operation;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.FintLinks;
import no.fint.provider.springer.service.Handler;
import no.fint.provider.springer.storage.Springer;
import no.fint.provider.springer.storage.Wrapper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Slf4j
public abstract class UpdateHandler<T extends FintLinks> implements Handler {
    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected Wrapper wrapper;

    @Autowired
    protected ObjectMapper mapper;

    private final Class<T> type;

    protected UpdateHandler(Class<T> type) {
        this.type = type;
    }

    protected boolean validQuery(String query) {
        return Arrays.stream(PropertyUtils.getPropertyDescriptors(type))
                .filter(it -> it.getPropertyType().equals(Identifikator.class))
                .map(PropertyDescriptor::getName)
                .anyMatch(it -> StringUtils.startsWithIgnoreCase(query, it + "/"));
    }

    protected void reject(Event<FintLinks> event, String statusCode) {
        event.setStatus(Status.ADAPTER_REJECTED);
        event.setResponseStatus(ResponseStatus.REJECTED);
        event.setStatusCode(statusCode);
    }

    protected Stream<T> stream(CriteriaDefinition criteria) {
        return mongoTemplate.stream(wrapper.query(type).addCriteria(criteria), Springer.class)
                .map(wrapper.unwrapper(type));
    }

    protected CriteriaDefinition createCriteria(String query) {
        String[] split = StringUtils.split(query, '/');
        String field = Arrays.stream(PropertyUtils.getPropertyDescriptors(type))
                .filter(it -> it.getPropertyType().equals(Identifikator.class))
                .map(PropertyDescriptor::getName)
                .filter(it -> StringUtils.equalsIgnoreCase(it, split[0]))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        return Criteria.where("value.%s.identifikatorverdi".formatted(field)).is(split[1]);
    }

    protected <T> Consumer<T> copy(T target) {
        return source -> Arrays.stream(PropertyUtils.getPropertyDescriptors(type))
                .map(PropertyDescriptor::getName)
                .forEach(name -> {
                    try {
                        if (!PropertyUtils.isReadable(source, name) || !PropertyUtils.isWriteable(target, name))
                            return;
                        Object value = PropertyUtils.getProperty(source, name);
                        if (value != null) {
                            PropertyUtils.setProperty(target, name, value);
                        }
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void accept(Event<FintLinks> event) {
        if (event.getOperation() != Operation.UPDATE) {
            reject(event, "INVALID_OPERATION");
            return;
        }
        if (!validQuery(event.getQuery())) {
            reject(event, "INVALID_QUERY");
            return;
        }
        List<T> updates = mapper.convertValue(event.getData(), mapper.getTypeFactory().constructCollectionType(List.class, type));
        event.setData(new ArrayList<>());
        stream(createCriteria(event.getQuery()))
                .peek(it -> updates.forEach(copy(it)))
                .forEach(event::addObject);
        if (event.getData().isEmpty()) {
            reject(event, "NOT_FOUND");
            return;
        }
        event.setResponseStatus(ResponseStatus.ACCEPTED);
        event.setStatus(Status.ADAPTER_ACCEPTED);
    }
}
