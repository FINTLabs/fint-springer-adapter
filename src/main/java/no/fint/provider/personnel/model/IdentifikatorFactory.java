package no.fint.provider.personnel.model;

import no.fint.model.felles.kompleksedatatyper.Identifikator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class IdentifikatorFactory {

    @Autowired
    private AtomicInteger atomicInteger;

    public Identifikator create() {
        Identifikator identifikator = new Identifikator();
        identifikator.setIdentifikatorverdi(String.format("ID%06d", atomicInteger.incrementAndGet()));
        return identifikator;
    }
}
