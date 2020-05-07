package no.fint.provider.springer.handlers

import spock.lang.Specification

class SchoolYearSpec extends Specification {
    def 'School year period is valid'() {
        when:
        def periode = GruppemedlemskapHandler.getSchoolYear()
        println(periode)

        then:
        periode.start < new Date()
        new Date() < periode.slutt
    }
}
