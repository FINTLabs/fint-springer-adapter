package no.fint.provider.springer.model

import com.fasterxml.jackson.databind.ObjectMapper
import no.fint.event.model.Event
import no.fint.event.model.Operation
import no.fint.event.model.ResponseStatus
import no.fint.event.model.Status
import no.fint.provider.springer.storage.Springer
import no.fint.provider.springer.storage.SpringerRepository
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.administrasjon.personal.PersonalActions
import no.novari.fint.model.felles.kompleksedatatyper.Kontaktinformasjon
import no.novari.fint.model.resource.FintLinks
import no.novari.fint.model.resource.administrasjon.personal.*
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.StringUtils.isNoneBlank
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Repository
import java.util.stream.Collectors
import java.util.stream.Stream

@Repository
class PersonalRepository(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper,
    private val objectMapper: ObjectMapper
) : SpringerRepository(mongoTemplate, wrapper) {

    private val log = LoggerFactory.getLogger(PersonalRepository::class.java)

    override fun accept(response: Event<FintLinks>) {
        when (PersonalActions.valueOf(response.action)) {
            PersonalActions.GET_ALL_PERSONALRESSURS -> query(PersonalressursResource::class.java, response)
            PersonalActions.GET_ALL_ARBEIDSFORHOLD -> query(ArbeidsforholdResource::class.java, response)
            PersonalActions.GET_ALL_FRAVAR -> {
                if (StringUtils.contains(response.source, "personal")) query(FravarResource::class.java, response)
            }
            PersonalActions.GET_ALL_FASTLONN -> query(FastlonnResource::class.java, response)
            PersonalActions.GET_ALL_FASTTILLEGG -> query(FasttilleggResource::class.java, response)
            PersonalActions.GET_ALL_VARIABELLONN -> query(VariabellonnResource::class.java, response)
            PersonalActions.UPDATE_PERSONALRESSURS -> handleUpdatePersonalressurs(response)
            PersonalActions.UPDATE_ARBEIDSFORHOLD -> unsupported(response)
            else -> invalid(response)
        }
    }

    private fun unsupported(response: Event<FintLinks>) {
        response.status = Status.ADAPTER_REJECTED
        response.responseStatus = ResponseStatus.REJECTED
        response.statusCode = "UNSUPPORTED_ACTION"
        response.message = "Unsupported action"
    }

    private fun invalid(response: Event<FintLinks>) {
        response.status = Status.ADAPTER_REJECTED
        response.responseStatus = ResponseStatus.REJECTED
        response.statusCode = "INVALID_ACTION"
        response.message = "Invalid action"
    }

    private fun handleUpdatePersonalressurs(response: Event<FintLinks>) {
        try {
            if (response.operation != Operation.UPDATE || response.data == null || response.data.size != 1) {
                response.status = Status.ADAPTER_REJECTED
                response.responseStatus = ResponseStatus.REJECTED
                response.statusCode = "INVALID_UPDATE"
                response.message = "Invalid update"
                return
            }
            if (response.query.isNullOrEmpty() || !response.query.contains('/')) {
                response.status = Status.ADAPTER_REJECTED
                response.responseStatus = ResponseStatus.REJECTED
                response.statusCode = "INVALID_QUERY"
                response.message = "Invalid query: ${response.query}"
                return
            }
            val resource = objectMapper.convertValue(response.data[0], PersonalressursResource::class.java)
            val queryString = StringUtils.split(response.query, '/')
            val query = wrapper.query(PersonalressursResource::class.java)
                .addCriteria(Criteria.where("value.%s.identifikatorverdi".format(queryString[0])).`is`(queryString[1]))
            log.info("Query: {}", query)
            val result: Springer? = mongoTemplate.findOne(query, Springer::class.java)
            if (result == null) {
                response.status = Status.ADAPTER_REJECTED
                response.responseStatus = ResponseStatus.REJECTED
                response.statusCode = "NOT_FOUND"
                response.message = "No match for query"
                return
            }
            val personalressurs = wrapper.unwrapper(PersonalressursResource::class.java)(result!!)
            resource.brukernavn?.takeIf { it != personalressurs.brukernavn }?.let {
                log.info("Updating brukernavn from {} to {}", personalressurs.brukernavn, it)
                personalressurs.brukernavn = it
            }
            resource.kontaktinformasjon?.let { n ->
                val o: Kontaktinformasjon = personalressurs.kontaktinformasjon ?: Kontaktinformasjon()
                if (isNoneBlank(n.epostadresse) && !StringUtils.equals(n.epostadresse, o.epostadresse)) {
                    log.info("Updating epostadresse from {} to {}", o.epostadresse, n.epostadresse)
                    o.epostadresse = n.epostadresse
                }
                if (isNoneBlank(n.telefonnummer) && !StringUtils.equals(n.telefonnummer, o.telefonnummer)) {
                    log.info("Updating telefonnummer from {} to {}", o.telefonnummer, n.telefonnummer)
                    o.telefonnummer = n.telefonnummer
                }
                if (isNoneBlank(n.mobiltelefonnummer) && !StringUtils.equals(n.mobiltelefonnummer, o.mobiltelefonnummer)) {
                    log.info("Updating mobiltelefonnummer from {} to {}", o.mobiltelefonnummer, n.mobiltelefonnummer)
                    o.mobiltelefonnummer = n.mobiltelefonnummer
                }
                if (isNoneBlank(n.nettsted) && !StringUtils.equals(n.nettsted, o.nettsted)) {
                    log.info("Updating nettsted from {} to {}", o.nettsted, n.nettsted)
                    o.nettsted = n.nettsted
                }
                if (isNoneBlank(n.sip) && !StringUtils.equals(n.sip, o.sip)) {
                    log.info("Updating sip from {} to {}", o.sip, n.sip)
                    o.sip = n.sip
                }
                personalressurs.kontaktinformasjon = o
            }
            wrapper.update(result, personalressurs)
            mongoTemplate.save(result)
            response.responseStatus = ResponseStatus.ACCEPTED
            response.data = listOf(personalressurs)
        } catch (e: Exception) {
            response.responseStatus = ResponseStatus.ERROR
            response.message = ExceptionUtils.getStackTrace(e)
        }
    }

    override fun actions(): Set<String> =
        Stream.of(*PersonalActions.values())
            .map { it.name }
            .filter { it.startsWith("GET_ALL_") }
            .collect(Collectors.toSet())
}
