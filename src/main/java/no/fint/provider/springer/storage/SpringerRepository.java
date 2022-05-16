package no.fint.provider.springer.storage;

import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.model.resource.FintLinks;
import no.fint.provider.springer.service.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.util.StreamUtils;

import java.util.stream.Stream;

public abstract class SpringerRepository implements Handler {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected Wrapper wrapper;

    protected void query(Class<? extends FintLinks> type, Event<FintLinks> response) {
        stream(type).forEach(response::addData);
        response.setResponseStatus(ResponseStatus.ACCEPTED);
    }

    protected Stream<? extends FintLinks> stream(Class<? extends FintLinks> type) {
        return StreamUtils
                .createStreamFromIterator(mongoTemplate.stream(wrapper.query(type), Springer.class))
                .map(wrapper.unwrapper(type));
    }
}
