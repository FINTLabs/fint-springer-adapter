package no.fint.provider.adapter.sse

import no.fint.event.model.Event
import no.fint.provider.springer.service.EventHandlerService
import no.fint.sse.AbstractEventListener
import org.slf4j.LoggerFactory

/**
 * Event listener for the SSE client. When an inbound event is received the {@link #onEvent(InboundEvent)} method
 * calls {@link EventHandlerService} service.
 */
class FintEventListener(
    private val component: String,
    private val eventHandler: EventHandlerService
) : AbstractEventListener() {
    private val log = LoggerFactory.getLogger(FintEventListener::class.java)

    override fun onEvent(event: Event<*>) {
        log.info("{}: Processing event {} for {} - {}", component, event.action, event.orgId, event.corrId)
        eventHandler.handleEvent(component, event)
    }
}
