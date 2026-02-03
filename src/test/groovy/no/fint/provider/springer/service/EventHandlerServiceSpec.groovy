package no.fint.provider.springer.service

import no.fint.event.model.DefaultActions
import no.fint.event.model.Event
import no.fint.provider.adapter.event.EventResponseService
import no.fint.provider.adapter.event.EventStatusService
import no.fint.provider.springer.SupportedActions
import spock.lang.Specification

class EventHandlerServiceSpec extends Specification {
    private EventHandlerService eventHandlerService
    private EventStatusService eventStatusService
    private EventResponseService eventResponseService

    void setup() {
        eventStatusService = Mock(EventStatusService) {
            verifyEvent(_, _) >> true
        }
        eventResponseService = Mock(EventResponseService)
        eventHandlerService = new EventHandlerService(eventResponseService, eventStatusService, new SupportedActions(), [])
        eventHandlerService.init()
    }

    def "Post response on health check"() {
        given:
        def event = new Event('rogfk.no', 'test', DefaultActions.HEALTH, 'test')
        def component = 'test'

        when:
        eventHandlerService.handleEvent(component, event)

        then:
        1 * eventResponseService.postResponse(component, _)
    }
}
