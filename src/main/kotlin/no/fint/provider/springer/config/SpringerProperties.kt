package no.fint.provider.springer.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "fint.springer")
data class SpringerProperties(
    var reseedTypes: List<String> = emptyList()
)
