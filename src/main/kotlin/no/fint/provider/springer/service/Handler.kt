package no.fint.provider.springer.service

import no.fint.event.model.Event
import no.novari.fint.model.resource.FintLinks

fun interface Handler {
    fun accept(event: Event<FintLinks>)

    fun actions(): Set<String> = emptySet()
}
