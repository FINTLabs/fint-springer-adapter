package no.fint.provider.personnel.behaviour;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.Problem;
import no.fint.event.model.ResponseStatus;
import no.fint.model.resource.administrasjon.personal.FravarResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@Slf4j
public class RejectFravarWithoutReason implements Behaviour<FravarResource> {
    @Override
    public void accept(Event event, FravarResource fravar) {
        if (fravar.getFravarsgrunn().isEmpty()) {
            event.setResponseStatus(ResponseStatus.REJECTED);
            event.setMessage("Fravær må ha fraværsgrunn");
            event.setStatusCode("FVX003");
            Problem problem = new Problem();
            problem.setField("fravarsgrunn");
            problem.setMessage("Fravær må ha fraværsgrunn");
            if (Objects.isNull(event.getProblems())) {
                event.setProblems(new ArrayList<>());
            }
            event.getProblems().add(problem);
            log.info("Rejecting {}", event);
        }
    }
}
