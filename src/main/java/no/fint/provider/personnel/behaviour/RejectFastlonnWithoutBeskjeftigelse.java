package no.fint.provider.personnel.behaviour;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.Problem;
import no.fint.event.model.ResponseStatus;
import no.fint.model.resource.administrasjon.personal.FastlonnResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@Slf4j
public class RejectFastlonnWithoutBeskjeftigelse implements Behaviour<FastlonnResource> {
    @Override
    public void accept(Event event, FastlonnResource fastlonn) {
        if (Objects.isNull(fastlonn.getBeskjeftigelse())||fastlonn.getBeskjeftigelse().isEmpty()) {
            event.setResponseStatus(ResponseStatus.REJECTED);
            event.setMessage("Fastlønn må ha minst én beskjeftigelse");
            event.setStatusCode("FLX002");
            Problem problem = new Problem();
            problem.setField("beskjeftigelse");
            problem.setMessage("Fastlønn må ha minst én beskjeftigelse");
            if (Objects.isNull(event.getProblems())) {
                event.setProblems(new ArrayList<>());
            }
            event.getProblems().add(problem);
            log.info("Rejecting {}", event);
        }
    }
}
