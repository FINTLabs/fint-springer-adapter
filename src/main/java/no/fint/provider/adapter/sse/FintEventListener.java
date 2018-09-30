package no.fint.provider.adapter.sse;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.provider.springer.service.EventHandlerService;
import no.fint.sse.AbstractEventListener;
import org.glassfish.jersey.media.sse.InboundEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Event listener for the for the SSE client. When an inbound event is received the {@link #onEvent(InboundEvent)} method
 * calls {@link EventHandlerService} service.
 */
@Slf4j
public class FintEventListener extends AbstractEventListener {

    private EventHandlerService eventHandler;
    private Path traceFile;

    public FintEventListener(EventHandlerService eventHandler) {
        this.eventHandler = eventHandler;
        try {
            traceFile = Files.createTempFile("events", ".json");
            log.info("Inbound events will be written to {}", traceFile);
        } catch (IOException e) {
            traceFile = null;
        }
    }

    @Override
    public void onEvent(InboundEvent inboundEvent) {
        if (traceFile != null) {
            try {
                Files.write(traceFile, (inboundEvent.readData()+"\n\n").getBytes(), StandardOpenOption.APPEND, StandardOpenOption.SYNC);
            } catch (IOException e) {

            }
        }
        super.onEvent(inboundEvent);
    }

    @Override
    public void onEvent(Event event) {
        eventHandler.handleEvent(event);
    }
}
