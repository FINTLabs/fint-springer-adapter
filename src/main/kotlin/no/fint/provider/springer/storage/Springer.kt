package no.fint.provider.springer.storage

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Springer(
    @Id var id: String? = null,
    var type: String,
    var value: Any?
)
