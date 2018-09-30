package no.fint.provider.springer.storage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void load() throws IOException, ClassNotFoundException {
        if (mongoTemplate.count(null, Springer.class) == 0) {
            log.info("Empty database, initializing...");
            for (Resource r : new PathMatchingResourcePatternResolver(getClass().getClassLoader()).getResources("classpath*:/springer/*.json")) {
                JsonNode jsonNode = objectMapper.readTree(r.getInputStream());
                Class<?> type = Class.forName(jsonNode.get("_class").asText());
                long count = StreamSupport
                        .stream(jsonNode.get("_embedded").get("_entries").spliterator(), false)
                        .map(wrapper.wrapper(type))
                        .peek(mongoTemplate::insert)
                        .count();
                log.info("Added {} elements of {}", count, type);
            }
        }
    }
}
