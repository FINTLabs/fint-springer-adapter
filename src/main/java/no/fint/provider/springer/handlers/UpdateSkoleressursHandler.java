package no.fint.provider.springer.handlers;

import no.novari.fint.model.resource.utdanning.elev.SkoleressursResource;
import no.novari.fint.model.utdanning.elev.ElevActions;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class UpdateSkoleressursHandler extends UpdateHandler<SkoleressursResource> {

    public UpdateSkoleressursHandler() {
        super(SkoleressursResource.class);
    }

    @Override
    public Set<String> actions() {
        return Collections.singleton(ElevActions.UPDATE_SKOLERESSURS.name());
    }
}
