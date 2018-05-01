package no.fint.provider.personnel.behaviour;

import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.model.resource.administrasjon.kompleksedatatyper.VariabelttilleggResource;
import no.fint.model.resource.administrasjon.personal.VariabellonnResource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RejectVariabellonnWithoutRequiredLinks implements Behaviour<VariabellonnResource> {
    @Override
    public void accept(Event event, VariabellonnResource resource) {
        if (empty(resource.getArbeidsforhold())) {
            addProblem(event, "arbeidsforhold", "Mangler relasjon til Arbeidsforhold");
        }
        if (empty(resource.getAttestant())) {
            addProblem(event, "attestant", "Mangler relasjon til Attestant");
        }
        if (empty(resource.getAnviser())) {
            addProblem(event, "anviser", "Mangler relasjon til Anviser");
        }
        if (empty(resource.getVariabelttillegg())) {
            addProblem(event, "variabelttillegg", "Mangler Variabelttillegg");
        }

        for (int i = 0; i < resource.getVariabelttillegg().size(); i++) {
            VariabelttilleggResource v = resource.getVariabelttillegg().get(i);
            
            if (empty(v.getLonnsart())) {
                addProblem(event, "variabelttillegg["+i+"].lonnsart", "Mangler relasjon til Lønnsart");
            }

            if (Objects.isNull(v.getKontostreng())) {
                addProblem(event, "variabelttillegg[\"+i+\"].kontostreng", "Mangler Kontostreng");
            } else {
                if (empty(v.getKontostreng().getArt())) {
                    addProblem(event, "variabelttillegg[\"+i+\"].kontostreng.art", "Mangler relasjon til Art");
                }
                if (empty(v.getKontostreng().getAnsvar())) {
                    addProblem(event, "variabelttillegg[\"+i+\"].kontostreng.ansvar", "Mangler relasjon til Ansvar");
                }
                if (empty(v.getKontostreng().getFunksjon())) {
                    addProblem(event, "variabelttillegg[\"+i+\"].kontostreng.funksjon", "Mangler relasjon til Funksjon");
                }

            }
        }

        if (!event.getProblems().isEmpty()) {
            event.setResponseStatus(ResponseStatus.REJECTED);
            event.setStatusCode(getClass().getSimpleName());
        }
    }

}
