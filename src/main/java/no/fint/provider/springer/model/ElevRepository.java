package no.fint.provider.springer.model;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.Link;
import no.fint.model.resource.utdanning.elev.*;
import no.fint.model.utdanning.elev.Basisgruppemedlemskap;
import no.fint.model.utdanning.elev.ElevActions;
import no.fint.model.utdanning.elev.Kontaktlarergruppemedlemskap;
import no.fint.model.utdanning.timeplan.Undervisningsgruppemedlemskap;
import no.fint.model.utdanning.utdanningsprogram.Programomrademedlemskap;
import no.fint.model.utdanning.vurdering.Eksamensgruppemedlemskap;
import no.fint.provider.springer.storage.SpringerRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static no.fint.model.utdanning.elev.ElevActions.*;
import static no.fint.provider.springer.handlers.GruppemedlemskapHandler.linkElev;
import static no.fint.provider.springer.handlers.GruppemedlemskapHandler.linkGruppe;

@Slf4j
@Repository
public class ElevRepository extends SpringerRepository {

    @Override
    public void accept(Event<FintLinks> response) {
        switch (ElevActions.valueOf(response.getAction())) {
            case GET_ALL_BASISGRUPPE:
                stream(BasisgruppeResource.class)
                        .map(BasisgruppeResource.class::cast)
                        .peek(g -> g.getElevforhold().stream().map(linkGruppe(g.getSystemId())).map(Link.apply(Basisgruppemedlemskap.class, "systemid")).forEach(g::addGruppemedlemskap))
                        .forEach(response::addData);
                response.setResponseStatus(ResponseStatus.ACCEPTED);
                break;
            case GET_ALL_ELEV:
                query(ElevResource.class, response);
                break;
            case GET_ALL_ELEVFORHOLD:
                stream(ElevforholdResource.class)
                        .map(ElevforholdResource.class::cast)
                        .peek(e -> {
                            e.getBasisgruppe().stream().map(linkElev(e.getSystemId())).map(Link.apply(Basisgruppemedlemskap.class, "systemid")).forEach(e::addBasisgruppemedlemskap);
                            e.getKontaktlarergruppe().stream().map(linkElev(e.getSystemId())).map(Link.apply(Kontaktlarergruppemedlemskap.class, "systemid")).forEach(e::addKontaktlarergruppemedlemskap);
                            e.getUndervisningsgruppe().stream().map(linkElev(e.getSystemId())).map(Link.apply(Undervisningsgruppemedlemskap.class, "systemid")).forEach(e::addUndervisningsgruppemedlemskap);
                            e.getProgramomrade().stream().map(linkElev(e.getSystemId())).map(Link.apply(Programomrademedlemskap.class, "systemid")).forEach(e::addProgramomrademedlemskap);
                            e.getEksamensgruppe().stream().map(linkElev(e.getSystemId())).map(Link.apply(Eksamensgruppemedlemskap.class, "systemid")).forEach(e::addEksamensgruppemedlemskap);
                        })
                        .forEach(response::addData);
                response.setResponseStatus(ResponseStatus.ACCEPTED);
                break;
            case GET_ALL_KONTAKTLARERGRUPPE:
                stream(KontaktlarergruppeResource.class)
                        .map(KontaktlarergruppeResource.class::cast)
                        .peek(g -> g.getElevforhold().stream().map(linkGruppe(g.getSystemId())).map(Link.apply(Kontaktlarergruppemedlemskap.class, "systemid")).forEach(g::addGruppemedlemskap))
                        .forEach(response::addData);
                response.setResponseStatus(ResponseStatus.ACCEPTED);
                break;
            case GET_ALL_SKOLERESSURS:
                query(SkoleressursResource.class, response);
                break;
            case GET_ALL_UNDERVISNINGSFORHOLD:
                query(UndervisningsforholdResource.class, response);
                break;
        }
    }

    @Override
    public Set<String> actions() {
        return Stream
                .of(
                        GET_ALL_BASISGRUPPE,
                        GET_ALL_ELEV,
                        GET_ALL_ELEVFORHOLD,
                        GET_ALL_KONTAKTLARERGRUPPE,
                        GET_ALL_SKOLERESSURS,
                        GET_ALL_UNDERVISNINGSFORHOLD)
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
