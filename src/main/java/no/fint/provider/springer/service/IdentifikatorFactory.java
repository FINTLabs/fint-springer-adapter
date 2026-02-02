package no.fint.provider.springer.service;

import lombok.extern.slf4j.Slf4j;
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.provider.springer.storage.Springer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class IdentifikatorFactory {

    @Autowired(required = false)
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void init() {
        if (mongoTemplate != null) {
            try {
                long count = mongoTemplate.count(null, Springer.class);
                atomicInteger.set((int) count);
                log.info("Setting initial value to {}", atomicInteger.get());
            } catch (Exception e) {
                log.info("Unable to update initial value: {} {}", e.getClass().getSimpleName(), e.getMessage());
            }
        }
    }

    private AtomicInteger atomicInteger = new AtomicInteger();

    public Identifikator create() {
        Identifikator identifikator = new Identifikator();
        identifikator.setIdentifikatorverdi("ID%010d".formatted(atomicInteger.incrementAndGet()));
        return identifikator;
    }
}
