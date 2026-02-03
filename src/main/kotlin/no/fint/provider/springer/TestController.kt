package no.fint.provider.springer

import no.fint.event.model.Event
import no.fint.provider.springer.service.EventHandlerService
import no.novari.fint.model.resource.FintLinks
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val eventHandlerService: EventHandlerService
) {
    @PostMapping
    fun handleEvent(@RequestBody input: Event<FintLinks>): Event<FintLinks> {
        val response = Event<FintLinks>(input)
        eventHandlerService.actionsHandlerMap()[input.action].forEach { it.accept(response) }
        return response
    }
}
