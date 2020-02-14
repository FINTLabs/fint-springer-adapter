package no.fint.provider.springer.service;

import com.google.common.collect.ImmutableMultimap;
import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.event.model.health.Health;
import no.fint.event.model.health.HealthStatus;
import no.fint.model.resource.FintLinks;
import no.fint.provider.adapter.event.EventResponseService;
import no.fint.provider.adapter.event.EventStatusService;
import no.fint.provider.springer.SupportedActions;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The EventHandlerService receives the <code>event</code> from SSE endpoint (provider) in the {@link #handleEvent(String, Event)} method.
 */
@Slf4j
@Service
public class EventHandlerService {

    @Autowired
    private EventResponseService eventResponseService;

    @Autowired
    private EventStatusService eventStatusService;

    @Autowired
    private SupportedActions supportedActions;

    @Autowired
    private Collection<Handler> handlers;

    private ImmutableMultimap<String, Handler> actionsHandlerMap;

    public Set<String> getActions() {
        return actionsHandlerMap.keySet();
    }

    public void handleEvent(String component, Event event) {
        if (event.isHealthCheck()) {
            postHealthCheckResponse(component, event);
        } else {
            if (eventStatusService.verifyEvent(component, event).getStatus() == Status.ADAPTER_ACCEPTED) {
                handleResponse(component, event.getAction(), new Event<>(event));
            }
        }
    }

    private void handleResponse(String component, String action, Event<FintLinks> response) {
        try {
            actionsHandlerMap.get(action).forEach(h -> h.accept(response));
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.ERROR);
            response.setMessage(ExceptionUtils.getStackTrace(e));
        } finally {
            if (response.getData() != null) {
                log.info("{}: Response for {}: {}, {} items", component, response.getAction(), response.getResponseStatus(), response.getData().size());
                log.trace("Event data: {}", response.getData());
            } else {
                log.info("{}: Response for {}: {}", component, response.getAction(), response.getResponseStatus());
            }
            eventResponseService.postResponse(component, response);
        }
    }

    /**
     * Checks if the application is healthy and updates the event object.
     *
     * @param event The event object
     */
    public void postHealthCheckResponse(String component, Event event) {
        Event<Health> healthCheckEvent = new Event<>(event);
        healthCheckEvent.setStatus(Status.TEMP_UPSTREAM_QUEUE);

        if (healthCheck()) {
            healthCheckEvent.addData(new Health("adapter", HealthStatus.APPLICATION_HEALTHY));
        } else {
            healthCheckEvent.addData(new Health("adapter", HealthStatus.APPLICATION_UNHEALTHY));
            healthCheckEvent.setMessage("The adapter is unable to communicate with the application.");
        }

        eventResponseService.postResponse(component, healthCheckEvent);
    }

    /**
     * This is where we implement the health check code
     *
     * @return {@code true} if health is ok, else {@code false}
     */
    private boolean healthCheck() {
        /*
         * Check application connectivity etc.
         */
        return ThreadLocalRandom.current().nextBoolean();
    }

    /**
     * Data used in examples
     */
    @PostConstruct
    void init() {
        ImmutableMultimap.Builder<String, Handler> builder = ImmutableMultimap.builder();
        handlers.forEach(h -> h.actions().forEach(a -> {
            builder.put(a, h);
            supportedActions.add(a);
        }));
        actionsHandlerMap = builder.build();
        log.info("Registered {} handlers, supporting actions: {}", handlers.size(), supportedActions.getActions());
    }
}
