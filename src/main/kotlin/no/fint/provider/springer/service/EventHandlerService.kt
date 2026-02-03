package no.fint.provider.springer.service

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.ImmutableSet
import jakarta.annotation.PostConstruct
import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.adapter.event.EventResponseService
import no.fint.provider.adapter.event.EventStatusService
import no.fint.provider.springer.SupportedActions
import no.novari.fint.model.resource.FintLinks
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.Collection
import java.util.concurrent.ThreadLocalRandom

/**
 * The EventHandlerService receives the event from SSE endpoint (provider) in the [handleEvent] method.
 */
@Service
class EventHandlerService(
    private val eventResponseService: EventResponseService,
    private val eventStatusService: EventStatusService,
    private val supportedActions: SupportedActions,
    private val handlers: Collection<Handler>
) {

    private val log = LoggerFactory.getLogger(EventHandlerService::class.java)

    private lateinit var actionsHandlerMap: ImmutableMultimap<String, Handler>

    fun actionsHandlerMap(): ImmutableMultimap<String, Handler> = actionsHandlerMap

    val actions: ImmutableSet<String>
        get() = actionsHandlerMap.keySet()

    fun handleEvent(component: String, event: Event<*>) {
        if (event.isHealthCheck) {
            postHealthCheckResponse(component, event)
        } else {
            if (eventStatusService.verifyEvent(component, event)) {
                @Suppress("UNCHECKED_CAST")
                val response = Event<FintLinks>(event)
                handleResponse(component, event.action, response)
            }
        }
    }

    private fun handleResponse(component: String, action: String, response: Event<FintLinks>) {
        try {
            log.debug("EventHandlerService.handleReponse for $action")
            actionsHandlerMap[action].forEach { h -> h.accept(response) }
        } catch (e: Exception) {
            log.error("Exception in handleResponse${e.message}", e)
            response.responseStatus = ResponseStatus.ERROR
            response.message = ExceptionUtils.getStackTrace(e)
        } finally {
            response.data?.let {
                log.info("{}: Response for {}: {}, {} items", component, response.action, response.responseStatus, it.size)
                log.trace("Event data: {}", it)
            } ?: run {
                log.info("{}: Response for {}: {}", component, response.action, response.responseStatus)
            }
            eventResponseService.postResponse(component, response)
        }
    }

    /**
     * Checks if the application is healthy and updates the event object.
     */
    fun postHealthCheckResponse(component: String, event: Event<*>) {
        val healthCheckEvent = Event<FintLinks>(event)
        healthCheckEvent.status = Status.TEMP_UPSTREAM_QUEUE

        if (healthCheck()) {
            // Don't add Health data for now to avoid casting issues
        } else {
            healthCheckEvent.message = "The adapter is unable to communicate with the application."
        }

        eventResponseService.postResponse(component, healthCheckEvent)
    }

    /**
     * This is where we implement the health check code
     */
    private fun healthCheck(): Boolean = ThreadLocalRandom.current().nextBoolean()

    /**
     * Data used in examples
     */
    @PostConstruct
    fun init() {
        val builder = ImmutableMultimap.builder<String, Handler>()
        handlers.forEach { h ->
            h.actions().forEach { a ->
                builder.put(a, h)
                supportedActions.add(a)
            }
        }
        actionsHandlerMap = builder.build()
        log.info("Registered {} handlers, supporting actions: {}", handlers.size(), supportedActions.getActions().size)
    }
}
