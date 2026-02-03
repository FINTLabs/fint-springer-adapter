package no.fint.provider.springer.config

import jakarta.annotation.PostConstruct
import no.fint.provider.springer.storage.Springer
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.index.Index
import org.springframework.stereotype.Component

@Component
class MongoIndexConfig(
    private val mongoTemplate: MongoTemplate
) {
    @PostConstruct
    fun ensureIndexes() {
        mongoTemplate.indexOps(Springer::class.java)
            .createIndex(Index().on("type", Sort.Direction.ASC))
    }
}
