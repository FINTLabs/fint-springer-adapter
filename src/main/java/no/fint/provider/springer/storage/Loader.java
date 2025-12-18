package no.fint.provider.springer.storage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.fint.provider.springer.config.SpringerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class Loader {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Wrapper wrapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SpringerProperties springerProperties;

    @PostConstruct
    public void load() throws IOException, ClassNotFoundException {
        log.info("Checking database content ...");
        
        if (springerProperties.isCleanOnStartup()) {
            log.info("Cleaning database as per configuration... 🧹");
            mongoTemplate.dropCollection(Springer.class);
        }
        for (Resource r : new PathMatchingResourcePatternResolver(getClass().getClassLoader()).getResources("classpath*:/springer/**/*.json")) {
            try {
                log.info("Checking {} ...", r);
                JsonNode jsonNode = objectMapper.readTree(r.getInputStream());
                Class<?> type = Class.forName(jsonNode.get("_class").asText());
                if (mongoTemplate.count(wrapper.query(type), Springer.class) == 0) {
                    long count = StreamSupport
                            .stream(jsonNode.get("_embedded").get("_entries").spliterator(), false)
                            .map(wrapper.wrapper(type))
                            .peek(mongoTemplate::insert)
                            .count();
                    log.info("Added {} elements of {}", count, type);
                }
            } catch (Exception e) {
                log.error("Exception in load() on "+ r.getFilename() +": " + e.getMessage());
                throw e;
            }
        }

        log.info("Completed database initialization.");
    }
}
