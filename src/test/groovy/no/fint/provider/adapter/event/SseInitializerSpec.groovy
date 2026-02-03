package no.fint.provider.adapter.event

import com.google.common.collect.ImmutableSet
import no.fint.provider.adapter.FintAdapterEndpoints
import no.fint.provider.adapter.FintAdapterProps
import no.fint.provider.adapter.sse.SseInitializer
import no.fint.provider.springer.service.EventHandlerService
import no.fint.sse.FintSse
import spock.lang.Specification

class SseInitializerSpec extends Specification {
    private SseInitializer sseInitializer
    private FintAdapterProps props
    private FintAdapterEndpoints endpoints
    private FintSse fintSse
    private EventHandlerService eventHandlerService

    void setup() {
        eventHandlerService = Mock(EventHandlerService) {
            getActions() >> ImmutableSet.of('GET_JALLA')
        }
        props = Mock(FintAdapterProps) {
            getOrganizations() >> ['rogfk.no', 'hfk.no', 'vaf.no']
        }
        endpoints = Mock(FintAdapterEndpoints) {
            getProviders() >> ['test':'http://localhost']
            getSse() >> '/sse/%s'
        }
        fintSse = Mock(FintSse)
    }

    def "Register and close SSE client for organizations"() {
        given:
        sseInitializer = new SseInitializer(props, endpoints, eventHandlerService)

        when:
        sseInitializer.init()

        then:
        sseInitializer.sseClients.size() == 3
    }

    def "Check SSE connection"() {
        given:
        sseInitializer = new SseInitializer(props, endpoints, eventHandlerService)
        sseInitializer.init() // Create some SSE clients first

        when:
        sseInitializer.checkSseConnection()

        then:
        notThrown(Exception)
    }

    def "Close SSE connection"() {
        given:
        sseInitializer = new SseInitializer(props, endpoints, eventHandlerService)
        sseInitializer.init() // Create some SSE clients first

        when:
        sseInitializer.cleanup()

        then:
        sseInitializer.sseClients.isEmpty()
    }
}
