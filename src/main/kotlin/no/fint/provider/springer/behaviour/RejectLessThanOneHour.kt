package no.fint.provider.springer.behaviour

import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.novari.fint.model.resource.administrasjon.personal.VariabellonnResource
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RejectLessThanOneHour : Behaviour<VariabellonnResource> {
    private val log = LoggerFactory.getLogger(RejectLessThanOneHour::class.java)

    override fun accept(event: Event<*>, variabellonn: VariabellonnResource) {
        if (variabellonn.antall < 100L) {
            event.responseStatus = ResponseStatus.REJECTED
            event.message = "Antall kan ikke være under 100"
            event.statusCode = "VLX004"
            addProblem(event, "antall", "Antall kan ikke være under 100")
            log.info("Rejecting {}", event)
        }
    }
}
