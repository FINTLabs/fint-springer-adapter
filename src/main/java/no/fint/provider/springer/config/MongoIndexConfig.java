package no.fint.provider.springer.config;

import no.fint.provider.springer.storage.Springer;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MongoIndexConfig {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void ensureIndexes() {
        mongoTemplate.indexOps(Springer.class)
                .ensureIndex(new Index().on("type", Sort.Direction.ASC));
    }
}
