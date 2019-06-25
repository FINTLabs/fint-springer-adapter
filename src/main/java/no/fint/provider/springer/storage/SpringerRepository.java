package no.fint.provider.springer.storage;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.administrasjon.personal.FravarResource;
import no.fint.provider.springer.service.Handler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.StreamUtils;

@Slf4j
public abstract class SpringerRepository implements Handler {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected Wrapper wrapper;

    protected void query(Class<? extends FintLinks> type, Event<FintLinks> response) {
        if (response.getAction().startsWith("GET_ALL_")) {
            StreamUtils
                    .createStreamFromIterator(mongoTemplate.stream(wrapper.query(type), Springer.class))
                    .map(wrapper.unwrapper(type))
                    .forEach(response::addData);
            response.setResponseStatus(ResponseStatus.ACCEPTED);
        } else if (response.getAction().startsWith("GET_")) {
            if (StringUtils.isEmpty(response.getQuery()) || !StringUtils.contains(response.getQuery(), '/')) {
                error(response, Status.ADAPTER_REJECTED, ResponseStatus.REJECTED, "INVALID_QUERY", "Invalid query: " + response.getQuery());
                return;
            }
            Springer result = findOne(type, response.getQuery());
            if (result == null || result.getValue() == null) {
                error(response, Status.ADAPTER_REJECTED, ResponseStatus.REJECTED, "NOT_FOUND", type.getSimpleName() + " not found: " + response.getQuery());
                return;
            }
            response.addData(wrapper.unwrapper(type).apply(result));
            response.setResponseStatus(ResponseStatus.ACCEPTED);
        } else {
            error(response, Status.ADAPTER_REJECTED, ResponseStatus.REJECTED, "INVALID_ACTION", "Invalid action");
        }
    }

    protected void error(Event<FintLinks> response, Status status, ResponseStatus responseStatus, String statusCode, String message) {
        response.setStatus(status);
        response.setResponseStatus(responseStatus);
        response.setStatusCode(statusCode);
        response.setMessage(message);
    }

    protected Springer findOne(Class<? extends FintLinks> type, String eventQuery) {
        String[] queryString = StringUtils.split(eventQuery, '/');
        Query query = wrapper.query(type)
                .addCriteria(Criteria.where(String.format("value.%s.identifikatorverdi", queryString[0])).is(queryString[1]));
        log.debug("{}", query);
        return mongoTemplate.findOne(query, Springer.class);
    }

    protected <T extends FintLinks> void create(Class<T> type, T data) {
        Springer springer = wrapper.wrapper(type).apply(data);
        mongoTemplate.insert(springer);
        log.debug("ID = {}", springer.getId());
    }
}
