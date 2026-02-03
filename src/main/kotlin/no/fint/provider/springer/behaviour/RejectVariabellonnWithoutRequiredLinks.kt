package no.fint.provider.springer.behaviour

import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.novari.fint.model.resource.administrasjon.personal.VariabellonnResource
import org.springframework.stereotype.Service

@Service
class RejectVariabellonnWithoutRequiredLinks : Behaviour<VariabellonnResource> {
    override fun accept(event: Event<*>, resource: VariabellonnResource) {
        if (empty(resource.arbeidsforhold)) {
            addProblem(event, "arbeidsforhold", "Mangler relasjon til Arbeidsforhold")
        }
        if (empty(resource.attestant)) {
            addProblem(event, "attestant", "Mangler relasjon til Attestant")
        }
        if (empty(resource.anviser)) {
            addProblem(event, "anviser", "Mangler relasjon til Anviser")
        }
        if (empty(resource.lonnsart)) {
            addProblem(event, "lonnsart", "Mangler relasjon til Lønnsart")
        }
        resource.kontostreng?.let {
            if (empty(it.art)) addProblem(event, "kontostreng.art", "Mangler relasjon til Art")
            if (empty(it.ansvar)) addProblem(event, "kontostreng.ansvar", "Mangler relasjon til Ansvar")
            if (empty(it.funksjon)) addProblem(event, "kontostreng.funksjon", "Mangler relasjon til Funksjon")
        } ?: addProblem(event, "kontostreng", "Mangler Kontostreng")

        if (!event.problems.isNullOrEmpty()) {
            event.responseStatus = ResponseStatus.REJECTED
            event.statusCode = this::class.java.simpleName
        }
    }
}
