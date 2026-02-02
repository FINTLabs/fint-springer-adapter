package no.fint.provider.springer.handlers;

import no.novari.fint.model.administrasjon.personal.PersonalActions;
import no.novari.fint.model.resource.administrasjon.personal.PersonalressursResource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class UpdatePersonalressursHandler extends UpdateHandler<PersonalressursResource> {

    public UpdatePersonalressursHandler() {
        super(PersonalressursResource.class);
    }

    @Override
    public Set<String> actions() {
        return Collections.singleton(PersonalActions.UPDATE_PERSONALRESSURS.name());
    }
}
