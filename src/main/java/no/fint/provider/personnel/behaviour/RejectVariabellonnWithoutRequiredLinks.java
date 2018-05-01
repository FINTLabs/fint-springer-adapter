package no.fint.provider.personnel.behaviour;

import no.fint.event.model.Event;
import no.fint.event.model.Problem;
import no.fint.event.model.ResponseStatus;
import no.fint.model.resource.administrasjon.kompleksedatatyper.VariabelttilleggResource;
import no.fint.model.resource.administrasjon.personal.VariabellonnResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RejectVariabellonnWithoutRequiredLinks implements Behaviour<VariabellonnResource> {
    @Override
    public void accept(Event event, VariabellonnResource resource) {
        List<Problem> problems = new ArrayList<>();

        if (empty(resource.getArbeidsforhold())) {
            problems.add(newProblem("arbeidsforhold", "Mangler relasjon til Arbeidsforhold"));
        }
        if (empty(resource.getAttestant())) {
            problems.add(newProblem("attestant", "Mangler relasjon til Attestant"));
        }
        if (empty(resource.getAnviser())) {
            problems.add(newProblem("anviser", "Mangler relasjon til Anviser"));
        }

        for (int i = 0; i < resource.getVariabelttillegg().size(); i++) {
            VariabelttilleggResource v = resource.getVariabelttillegg().get(i);
            
            if (empty(v.getLonnsart())) {
                problems.add(newProblem("variabelttillegg["+i+"].lonnsart", "Mangler relasjon til Lønnsart"));
            }

            if (Objects.isNull(v.getKontostreng())) {
                problems.add(newProblem("variabelttillegg[\"+i+\"].kontostreng", "Mangler Kontostreng"));
            } else {
                if (empty(v.getKontostreng().getArt())) {
                    problems.add(newProblem("variabelttillegg[\"+i+\"].kontostreng.art", "Mangler relasjon til Art"));
                }
                if (empty(v.getKontostreng().getAnsvar())) {
                    problems.add(newProblem("variabelttillegg[\"+i+\"].kontostreng.ansvar", "Mangler relasjon til Ansvar"));
                }
                if (empty(v.getKontostreng().getFunksjon())) {
                    problems.add(newProblem("variabelttillegg[\"+i+\"].kontostreng.funksjon", "Mangler relasjon til Funksjon"));
                }

            }
        }
        if (!problems.isEmpty()) {
            event.setProblems(problems);
            event.setResponseStatus(ResponseStatus.REJECTED);
            event.setStatusCode(getClass().getSimpleName());
        }
    }

}
