package no.fint.provider.personnel.behaviour;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.Problem;
import no.fint.event.model.ResponseStatus;
import no.fint.model.resource.administrasjon.kompleksedatatyper.VariabelttilleggResource;
import no.fint.model.resource.administrasjon.personal.VariabellonnResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@Slf4j
public class RejectLessThanOneHour implements Behaviour<VariabellonnResource> {
    @Override
    public void accept(Event event, VariabellonnResource variabellonn) {
        if (variabellonn.getVariabelttillegg().stream().mapToLong(VariabelttilleggResource::getAntall).anyMatch(l -> l < 100L)) {
            event.setResponseStatus(ResponseStatus.REJECTED);
            event.setMessage("Antall kan ikke være under 100");
            event.setStatusCode("VLX004");
            addProblem(event, "variabelttillegg.antall", "Antall kan ikke være under 100");
            log.info("Rejecting {}", event);
        }
    }

}
