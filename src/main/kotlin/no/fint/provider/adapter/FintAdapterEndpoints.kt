package no.fint.provider.adapter

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "fint.adapter.endpoints")
data class FintAdapterEndpoints(
    var sse: String? = null,
    var status: String? = null,
    var response: String? = null,
    var providers: Map<String, String> = emptyMap()
)
