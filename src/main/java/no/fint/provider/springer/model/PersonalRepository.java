package no.fint.provider.springer.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.Operation;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.model.administrasjon.personal.PersonalActions;
import no.fint.model.felles.kompleksedatatyper.Kontaktinformasjon;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.administrasjon.personal.*;
import no.fint.provider.springer.storage.Springer;
import no.fint.provider.springer.storage.SpringerRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;

@Slf4j
@Repository
public class PersonalRepository extends SpringerRepository {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void accept(Event<FintLinks> response) {
        switch (PersonalActions.valueOf(response.getAction())) {
            case GET_ALL_PERSONALRESSURS:
                query(PersonalressursResource.class, response);
                break;
            case GET_ALL_ARBEIDSFORHOLD:
                query(ArbeidsforholdResource.class, response);
                break;
            case GET_ALL_FRAVAR:
                if (StringUtils.contains(response.getSource(), "personal"))
                    query(FravarResource.class, response);
                else
                    log.info("Not handling {} from {}", response.getAction(), response.getSource());
                break;
            case GET_ALL_FASTLONN:
                query(FastlonnResource.class, response);
                break;
            case GET_ALL_FASTTILLEGG:
                query(FasttilleggResource.class, response);
                break;
            case GET_ALL_VARIABELLONN:
                query(VariabellonnResource.class, response);
                break;
            case UPDATE_PERSONALRESSURS:
                handleUpdatePersonalressurs(response);
                break;
            case UPDATE_ARBEIDSFORHOLD:
                response.setStatus(Status.ADAPTER_REJECTED);
                response.setResponseStatus(ResponseStatus.REJECTED);
                response.setStatusCode("UNSUPPORTED_ACTION");
                response.setMessage("Unsupported action");
                break;
            default:
                response.setStatus(Status.ADAPTER_REJECTED);
                response.setResponseStatus(ResponseStatus.REJECTED);
                response.setStatusCode("INVALID_ACTION");
                response.setMessage("Invalid action");
        }
    }

    private void handleUpdatePersonalressurs(Event<FintLinks> response) {
        try {
            if (response.getOperation() != Operation.UPDATE || response.getData() == null || response.getData().size() != 1) {
                response.setStatus(Status.ADAPTER_REJECTED);
                response.setResponseStatus(ResponseStatus.REJECTED);
                response.setStatusCode("INVALID_UPDATE");
                response.setMessage("Invalid update");
                return;
            }
            if (StringUtils.isEmpty(response.getQuery()) || !StringUtils.contains(response.getQuery(), '/')) {
                response.setStatus(Status.ADAPTER_REJECTED);
                response.setResponseStatus(ResponseStatus.REJECTED);
                response.setStatusCode("INVALID_QUERY");
                response.setMessage("Invalid query: " + response.getQuery());
                return;
            }
            PersonalressursResource resource = objectMapper.convertValue(response.getData().get(0), PersonalressursResource.class);
            String[] queryString = StringUtils.split(response.getQuery(), '/');
            Query query = wrapper.query(PersonalressursResource.class)
                    .addCriteria(Criteria.where(String.format("value.%s.identifikatorverdi", queryString[0])).is(queryString[1]));
            log.info("Query: {}", query);
            Springer result = mongoTemplate.findOne(query, Springer.class);
            PersonalressursResource personalressurs = wrapper.unwrapper(PersonalressursResource.class).apply(result);
            if (resource.getBrukernavn() != null && !resource.getBrukernavn().equals(personalressurs.getBrukernavn())) {
                log.info("Updating brukernavn from {} to {}", personalressurs.getBrukernavn(), resource.getBrukernavn());
                personalressurs.setBrukernavn(resource.getBrukernavn());
            }
            if (resource.getKontaktinformasjon() != null && !resource.getKontaktinformasjon().equals(personalressurs.getKontaktinformasjon())) {
                Kontaktinformasjon n = resource.getKontaktinformasjon();
                Kontaktinformasjon o = personalressurs.getKontaktinformasjon();
                if (isNoneBlank(n.getEpostadresse()) && !StringUtils.equals(n.getEpostadresse(), o.getEpostadresse())) {
                    log.info("Updating epostadresse from {} to {}", o.getEpostadresse(), n.getEpostadresse());
                    o.setEpostadresse(n.getEpostadresse());
                }
                if (isNoneBlank(n.getTelefonnummer()) && !StringUtils.equals(n.getTelefonnummer(), o.getTelefonnummer())) {
                    log.info("Updating telefonnummer from {} to {}", o.getTelefonnummer(), n.getTelefonnummer());
                    o.setTelefonnummer(n.getTelefonnummer());
                }
                if (isNoneBlank(n.getMobiltelefonnummer()) && !StringUtils.equals(n.getMobiltelefonnummer(), o.getMobiltelefonnummer())) {
                    log.info("Updating mobiltelefonnummer from {} to {}", o.getMobiltelefonnummer(), n.getMobiltelefonnummer());
                    o.setMobiltelefonnummer(n.getMobiltelefonnummer());
                }
                if (isNoneBlank(n.getNettsted()) && !StringUtils.equals(n.getNettsted(), o.getNettsted())) {
                    log.info("Updating nettsted from {} to {}", o.getNettsted(), n.getNettsted());
                    o.setNettsted(n.getNettsted());
                }
                if (isNoneBlank(n.getSip()) && !StringUtils.equals(n.getSip(), o.getSip())) {
                    log.info("Updating sip from {} to {}", o.getSip(), n.getSip());
                    o.setSip(n.getSip());
                }
            }
            wrapper.update(result, personalressurs);
            mongoTemplate.save(result);
            response.setResponseStatus(ResponseStatus.ACCEPTED);
            response.setData(Collections.singletonList(personalressurs));
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.ERROR);
            response.setMessage(ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public Set<String> actions() {
        return Stream
                .of(PersonalActions.values())
                .map(Enum::name)
                .filter(n -> n.startsWith("GET_ALL_"))
                .collect(Collectors.toSet());
    }
}
