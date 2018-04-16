package no.fint.provider.personnel;

import no.fint.model.administrasjon.personal.PersonalActions;
import no.fint.provider.adapter.AbstractSupportedActions;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SupportedActions extends AbstractSupportedActions {

    @PostConstruct
    public void addSupportedActions() {
        addAll(PersonalActions.class);
    }

}
