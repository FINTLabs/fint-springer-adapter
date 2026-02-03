package no.fint.provider.springer.behaviour

import no.fint.event.model.Event
import no.fint.event.model.ResponseStatus
import no.novari.fint.model.resource.administrasjon.personal.FastlonnResource
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RejectFastlonnWithoutPercentage : Behaviour<FastlonnResource> {
    private val log = LoggerFactory.getLogger(RejectFastlonnWithoutPercentage::class.java)

    override fun accept(event: Event<*>, resource: FastlonnResource) {
        val prosent = resource.prosent
        if (prosent == null || prosent < 0L) {
            event.responseStatus = ResponseStatus.REJECTED
            event.message = "Fastlønn må ha prosent > 0"
            event.statusCode = "FLX002"
            addProblem(event, "prosent", "Fastlønn må ha prosent > 0")
            log.info("Rejecting {}", event)
        }
    }
}
