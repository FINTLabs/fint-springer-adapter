package no.fint.provider.adapter.sse

import com.google.common.collect.ImmutableMap
import no.fint.event.model.HeaderConstants
import no.fint.provider.adapter.FintAdapterEndpoints
import no.fint.provider.adapter.FintAdapterProps
import no.fint.provider.springer.service.EventHandlerService
import no.fint.sse.FintSse
import no.fint.sse.FintSseConfig
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicReference
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy

/**
 * Handles the client connections to the provider SSE endpoint
 */
@Component
class SseInitializer(
    private val props: FintAdapterProps,
    private val endpoints: FintAdapterEndpoints,
    private val eventHandlerService: EventHandlerService
) {
    private val log = LoggerFactory.getLogger(SseInitializer::class.java)
    private val sseClientsRef = AtomicReference<List<FintSse>>(emptyList())

    val sseClients: List<FintSse>
        get() = sseClientsRef.get()

    @PostConstruct
    @Synchronized
    fun init() {
        val config = FintSseConfig.withOrgIds(*props.organizations)
        val newClients = props.organizations.flatMap { orgId ->
            endpoints.providers.map { (component, provider) ->
                val fintSse = FintSse(provider + endpoints.sse, null, config)
                val listener = FintEventListener(component, eventHandlerService)
                fintSse.connect(
                    listener,
                    ImmutableMap.of(
                        HeaderConstants.ORG_ID, orgId,
                        HeaderConstants.CLIENT, "springer-adapter",
                        "x-fint-actions", eventHandlerService.actions.joinToString(",")
                    )
                )
                log.debug("SseInitializer.init for OrgId:{} Url:{} for {}", orgId, fintSse.sseUrl, eventHandlerService.actions.joinToString(","))
                fintSse
            }
        }
        sseClientsRef.set(newClients)
    }

    @Scheduled(initialDelay = 20000L, fixedDelay = 5000L)
    fun checkSseConnection() {
        val current = sseClients
        if (current.isEmpty()) {
            log.warn("Reinitializing SSE connections!")
            init()
            return
        }
        try {
            val expired = current
                .associateBy({ it.sseUrl }, { it.age })
                .filterValues { it > props.expiration }
            if (expired.isNotEmpty()) {
                log.warn("Stale connections detected: {}", expired)
                cleanup()
                init()
            } else {
                current.forEach { sseClient ->
                    if (!sseClient.verifyConnection()) {
                        log.info("Reconnecting SSE client {}", sseClient.sseUrl)
                    }
                }
            }
        } catch (e: Exception) {
            log.error("Unexpected error during SSE connection check!", e)
        }
    }

    @PreDestroy
    @Synchronized
    fun cleanup() {
        val oldClients = sseClientsRef.getAndSet(emptyList())
        oldClients.forEach { it.close() }
    }
}
