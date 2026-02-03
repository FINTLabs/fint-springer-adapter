package no.fint.provider.springer.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "fint.springer")
data class SpringerProperties(
    /** Whether to clean the database before loading initial data on startup */
    var cleanOnStartup: Boolean = false
)
