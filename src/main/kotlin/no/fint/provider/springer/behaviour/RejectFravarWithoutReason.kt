package no.fint.provider.springer.behaviour

import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.novari.fint.model.resource.administrasjon.personal.FravarResource
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RejectFravarWithoutReason : Behaviour<FravarResource> {
    private val log = LoggerFactory.getLogger(RejectFravarWithoutReason::class.java)

    override fun accept(event: Event<*>, fravar: FravarResource) {
        if (fravar.fravarsgrunn.isEmpty()) {
            event.responseStatus = ResponseStatus.REJECTED
            event.message = "Fravær må ha fraværsgrunn"
            event.statusCode = "FVX003"
            addProblem(event, "fravarsgrunn", "Fravær må ha fraværsgrunn")
            log.info("Rejecting {}", event)
        }
    }
}
