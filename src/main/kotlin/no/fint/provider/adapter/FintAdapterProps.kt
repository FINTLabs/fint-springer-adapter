package no.fint.provider.adapter

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class FintAdapterProps(
    @param:Value($$"${fint.adapter.organizations}")
    val organizations: Array<String>,
    @param:Value($$"${fint.adapter.sse-expiration:1200000}")
    val expiration: Int,
    @param:Value($$"${fint.adapter.reject-unknown-events:true}")
    val rejectUnknownEvents: Boolean
)
