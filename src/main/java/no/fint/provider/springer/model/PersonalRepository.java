package no.fint.provider.springer.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Streams;
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
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;

@Slf4j
@Repository
public class PersonalRepository extends SpringerRepository {

    @Autowired
    private ObjectMapper objectMapper;

    private EnumMap<PersonalActions, Class<? extends FintLinks>> actions = createMap();

    @Override
    public void accept(Event<FintLinks> response) {
        PersonalActions action = PersonalActions.valueOf(response.getAction());
        if (actions.containsKey(action)) {
            query(actions.get(action), response);
        } else if (action == PersonalActions.UPDATE_PERSONALRESSURS) {
            handleUpdatePersonalressurs(response);
        } else {
            response.setStatus(Status.ADAPTER_REJECTED);
            response.setResponseStatus(ResponseStatus.REJECTED);
            response.setStatusCode("INVALID_ACTION");
            response.setMessage("Invalid action");
        }
    }

    private void handleUpdatePersonalressurs(Event<FintLinks> response) {
        try {
            if (response.getOperation() != Operation.UPDATE || response.getData() == null || response.getData().size() != 1) {
                error(response, Status.ADAPTER_REJECTED, ResponseStatus.REJECTED, "INVALID_UPDATE", "Invalid update");
                return;
            }
            if (StringUtils.isEmpty(response.getQuery()) || !StringUtils.contains(response.getQuery(), '/')) {
                error(response, Status.ADAPTER_REJECTED, ResponseStatus.REJECTED, "INVALID_QUERY", "Invalid query: " + response.getQuery());
                return;
            }
            PersonalressursResource resource = objectMapper.convertValue(response.getData().get(0), PersonalressursResource.class);
            Springer result = findOne(PersonalressursResource.class, response.getQuery());
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
            error(response, Status.ADAPTER_REJECTED, ResponseStatus.ERROR, e.getClass().getSimpleName(), ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public Set<String> actions() {
        return Streams
                .concat(actions.keySet().stream(), Stream.of(PersonalActions.UPDATE_PERSONALRESSURS))
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    private EnumMap<PersonalActions, Class<? extends FintLinks>> createMap() {
        EnumMap<PersonalActions, Class<? extends FintLinks>> actions = new EnumMap<>(PersonalActions.class);
        actions.put(PersonalActions.GET_ALL_PERSONALRESSURS, PersonalressursResource.class);
        actions.put(PersonalActions.GET_ALL_ARBEIDSFORHOLD, ArbeidsforholdResource.class);
        actions.put(PersonalActions.GET_FRAVAR, FravarResource.class);
        actions.put(PersonalActions.GET_FASTLONN, FastlonnResource.class);
        actions.put(PersonalActions.GET_FASTTILLEGG, FasttilleggResource.class);
        actions.put(PersonalActions.GET_VARIABELLONN, VariabellonnResource.class);
        return actions;
    }

}
