package no.fint.provider.springer.handlers

import no.fint.event.model.Event
import no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon
import no.novari.fint.model.resource.utdanning.elev.ElevResource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Ignore
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles('test')
@Ignore
class UpdateElevHandlerSpec extends Specification {
    @Autowired
    private UpdateElevHandler handler

    def 'Update elev'() {
        given:
        def event = new Event<ElevResource>(
                corrId: UUID.randomUUID().toString(),
                action: 'UPDATE_ELEV',
                query: 'elevnummer/500001',
                data: [
                        new ElevResource(
                                kontaktinformasjon: new Kontaktinformasjon(
                                        mobiltelefonnummer: '4567890123456'
                                )
                        )
                ]
        )

        when:
        handler.accept(event)
        println(event.data)

        then:
        noExceptionThrown()
        event.data[0].kontaktinformasjon.mobiltelefonnummer == '4567890123456'
        event.data[0].elevnummer.identifikatorverdi == '500001'
    }
}
