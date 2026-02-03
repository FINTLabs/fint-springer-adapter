package no.fint.provider.adapter.event

import no.fint.event.model.Event
import no.fint.event.model.HeaderConstants
import no.fint.provider.adapter.FintAdapterEndpoints
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate

/**
 * Handles responses back to the provider status endpoint.
 */
@Service
class EventResponseService(
    private val endpoints: FintAdapterEndpoints,
    private val restTemplate: RestTemplate
) {
    private val log = LoggerFactory.getLogger(EventResponseService::class.java)

    /**
     * Method for posting back the response to the provider.
     *
     * @param component Name of component
     * @param event     Event to post back
     */
    fun postResponse(component: String, event: Event<*>) {
        try {
            val headers = HttpHeaders().apply {
                add(HeaderConstants.ORG_ID, event.orgId)
                add(HeaderConstants.CLIENT, "springer-adapter")
                add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            }
            val url = endpoints.providers[component] + endpoints.response
            log.info("{}: Posting response for {} ...", component, event.action)
            val response = restTemplate.exchange(url, HttpMethod.POST, HttpEntity(event, headers), Void::class.java)
            log.info("{}: Provider POST response: {}", component, response.statusCode)
        } catch (e: HttpStatusCodeException) {
            log.warn("{}: Provider POST response error: {}", component, e.statusCode)
        }
    }
}
