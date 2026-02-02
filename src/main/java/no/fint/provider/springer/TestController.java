package no.fint.provider.springer;

import no.fint.event.model.Event;
import no.novari.fint.model.resource.FintLinks;
import no.fint.provider.springer.service.EventHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private EventHandlerService eventHandlerService;

    @PostMapping
    public Event<FintLinks> handleEvent(@RequestBody Event<FintLinks> input) {
        Event<FintLinks> response = new Event<>(input);
        eventHandlerService.getActionsHandlerMap().get(input.getAction()).forEach(it -> it.accept(response));
        return response;
    }

}
