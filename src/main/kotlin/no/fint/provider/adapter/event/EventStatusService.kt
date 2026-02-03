package no.fint.provider.adapter.event

import no.fint.event.model.DefaultActions
import no.fint.event.model.Event
import no.fint.event.model.HeaderConstants
import no.fint.event.model.Status
import no.fint.provider.adapter.FintAdapterEndpoints
import no.fint.provider.adapter.FintAdapterProps
import no.fint.provider.springer.SupportedActions
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate

/**
 * Handles statuses back to the provider status endpoint.
 */
@Service
class EventStatusService(
    private val endpoints: FintAdapterEndpoints,
    private val restTemplate: RestTemplate,
    private val supportedActions: SupportedActions,
    private val props: FintAdapterProps
) {
    private val log = LoggerFactory.getLogger(EventStatusService::class.java)

    /**
     * Verifies if we can handle the event and set the status accordingly.
     *
     * @return true if the event was accepted, false otherwise
     */
    fun verifyEvent(component: String, event: Event<*>): Boolean {
        if (supportedActions.supports(event.action) || DefaultActions.getDefaultActions().contains(event.action)) {
            log.debug("Adapter accepted event: {}", event.action)
            event.status = Status.ADAPTER_ACCEPTED
        } else if (props.rejectUnknownEvents) {
            log.info("Rejecting event: {}", event.action)
            event.status = Status.ADAPTER_REJECTED
        } else {
            log.debug("Event not implemented, skipping: {}", event.action)
            return false
        }

        log.info("{}: Posting status for {} {} ...", component, event.action, event.corrId)
        return postStatus(component, event) && event.status == Status.ADAPTER_ACCEPTED
    }

    /**
     * Method for posting back the status to the provider.
     *
     * @param component Name of component
     * @param event     Event to send
     */
    fun postStatus(component: String, event: Event<*>): Boolean {
        return try {
            val headers = HttpHeaders().apply {
                add(HeaderConstants.ORG_ID, event.orgId)
                add(HeaderConstants.CLIENT, "springer-adapter")
                add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            }
            val url = endpoints.providers[component] + endpoints.status
            val response = restTemplate.exchange(url, HttpMethod.POST, HttpEntity(event, headers), Void::class.java)
            log.info("{}: Provider POST status response: {}", component, response.statusCode)
            true
        } catch (e: HttpStatusCodeException) {
            log.warn("{}: Provider POST status error: {}", component, e.statusCode)
            false
        }
    }
}
