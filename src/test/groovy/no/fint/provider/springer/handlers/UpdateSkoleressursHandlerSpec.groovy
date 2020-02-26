package no.fint.provider.springer.handlers

import no.fint.event.model.Event
import no.fint.event.model.Operation
import no.fint.model.felles.kompleksedatatyper.Identifikator
import no.fint.model.felles.kompleksedatatyper.Kontaktinformasjon
import no.fint.model.resource.utdanning.elev.ElevResource
import no.fint.model.resource.utdanning.elev.SkoleressursResource
import no.fint.model.utdanning.elev.ElevActions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Ignore
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles('test')
@Ignore
class UpdateSkoleressursHandlerSpec extends Specification {
    @Autowired
    private UpdateSkoleressursHandler handler

    def 'Update elev'() {
        given:
        def event = new Event<SkoleressursResource>(
                corrId: UUID.randomUUID().toString(),
                action: ElevActions.UPDATE_SKOLERESSURS,
                operation: Operation.UPDATE,
                query: 'systemid/SR_NORA',
                data: [
                        new SkoleressursResource(
                                systemId: new Identifikator(
                                        identifikatorverdi: 'SR_NORA'
                                ),
                                feidenavn: new Identifikator(
                                        identifikatorverdi: 'nora.lysere@osloskole.no'
                                )
                        )
                ]
        )

        when:
        handler.accept(event)
        println(event.data)

        then:
        noExceptionThrown()
        event.data[0].feidenavn.identifikatorverdi == 'nora.lysere@osloskole.no'
        event.data[0].systemId.identifikatorverdi == 'SR_NORA'
    }
}
