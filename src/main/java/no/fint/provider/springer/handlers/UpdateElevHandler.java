package no.fint.provider.springer.handlers;

import no.novari.fint.model.resource.utdanning.elev.ElevResource;
import no.novari.fint.model.utdanning.elev.ElevActions;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class UpdateElevHandler extends UpdateHandler<ElevResource> {

    public UpdateElevHandler() {
        super(ElevResource.class);
    }

    @Override
    public Set<String> actions() {
        return Collections.singleton(ElevActions.UPDATE_ELEV.name());
    }
}
