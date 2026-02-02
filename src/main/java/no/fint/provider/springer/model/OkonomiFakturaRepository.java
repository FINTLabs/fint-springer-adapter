package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.novari.fint.model.okonomi.faktura.FakturaActions;
import no.novari.fint.model.resource.FintLinks;
import no.novari.fint.model.resource.okonomi.faktura.FakturautstederResource;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Set;

@Slf4j
@Repository
public class OkonomiFakturaRepository extends SpringerRepository {

    @Override
    public void accept(Event<FintLinks> response) {
        switch (FakturaActions.valueOf(response.getAction())) {
            case GET_ALL_FAKTURAUTSTEDER:
                query(FakturautstederResource.class, response);
                break;
            default:
                response.setStatus(Status.ADAPTER_REJECTED);
                response.setResponseStatus(ResponseStatus.REJECTED);
                response.setStatusCode("INVALID_ACTION");
                response.setMessage("Invalid action");
        }
    }

    @Override
    public Set<String> actions() {
        return Collections.singleton(FakturaActions.GET_ALL_FAKTURAUTSTEDER.name());
    }
}
