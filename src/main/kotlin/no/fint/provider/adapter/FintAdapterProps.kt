package no.fint.provider.adapter

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class FintAdapterProps(
    @Value("\${fint.adapter.organizations}")
    val organizations: Array<String>,
    @Value("\${fint.adapter.sse-expiration:1200000}")
    val expiration: Int,
    @Value("\${fint.adapter.reject-unknown-events:true}")
    val rejectUnknownEvents: Boolean
)
