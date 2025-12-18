package no.fint.provider.springer.config;

import no.fint.provider.springer.storage.Springer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class MongoIndexConfig {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void ensureIndexes() {
        mongoTemplate.indexOps(Springer.class)
                .ensureIndex(new Index().on("type", Sort.Direction.ASC));
    }
}
