package no.fint.provider.springer.handlers;

import com.google.common.collect.ImmutableMap;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.felles.kompleksedatatyper.Periode;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.Link;
import no.fint.model.resource.utdanning.elev.BasisgruppemedlemskapResource;
import no.fint.model.resource.utdanning.elev.ElevforholdResource;
import no.fint.model.resource.utdanning.elev.KontaktlarergruppemedlemskapResource;
import no.fint.model.resource.utdanning.timeplan.UndervisningsgruppemedlemskapResource;
import no.fint.model.resource.utdanning.utdanningsprogram.ProgramomrademedlemskapResource;
import no.fint.model.utdanning.basisklasser.Gruppemedlemskap;
import no.fint.model.utdanning.elev.ElevActions;
import no.fint.model.utdanning.elev.Elevforhold;
import no.fint.model.utdanning.timeplan.TimeplanActions;
import no.fint.model.utdanning.utdanningsprogram.UtdanningsprogramActions;
import no.fint.provider.springer.storage.SpringerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
public class GruppemedlemskapHandler extends SpringerRepository {

    private final ImmutableMap<String, GroupGenerator<?>> generators;

    public GruppemedlemskapHandler() {
        generators = ImmutableMap.<String, GroupGenerator<?>>builder()
                .put(ElevActions.GET_ALL_BASISGRUPPEMEDLEMSKAP.name(), this::basisgruppeGenerator)
                .put(ElevActions.GET_ALL_KONTAKTLARERGRUPPEMEDLEMSKAP.name(), this::kontaktlarergruppeGenerator)
                .put(TimeplanActions.GET_ALL_UNDERVISNINGSGRUPPEMEDLEMSKAP.name(), this::undervisningsgruppeGenerator)
                .put(UtdanningsprogramActions.GET_ALL_PROGRAMOMRADEMEDLEMSKAP.name(), this::programomradeGenerator)
                .build();
    }

    public static Function<Link,String> linkGruppe(Identifikator systemId) {
        return link -> String.format("%s_%s", StringUtils.substringAfterLast(link.getHref(), "/"), systemId.getIdentifikatorverdi());
    }

    public static Function<Link,String> linkElev(Identifikator systemId) {
        return link -> String.format("%s_%s", systemId.getIdentifikatorverdi(), StringUtils.substringAfterLast(link.getHref(), "/"));
    }

    private Stream<FintLinks> programomradeGenerator(ElevforholdResource elevforholdResource) {
        return elevforholdResource
                .getProgramomrade()
                .stream()
                .map(Link::getHref)
                .map(g -> {
                    ProgramomrademedlemskapResource r = new ProgramomrademedlemskapResource();
                    setPeriode(r);
                    setSystemId(r, elevforholdResource, g);
                    r.addElevforhold(Link.with(Elevforhold.class, "systemid", elevforholdResource.getSystemId().getIdentifikatorverdi()));
                    r.addProgramomrade(Link.with(g));
                    return r;
                });
    }

    private Stream<FintLinks> undervisningsgruppeGenerator(ElevforholdResource elevforholdResource) {
        return elevforholdResource
                .getUndervisningsgruppe()
                .stream()
                .map(Link::getHref)
                .map(g -> {
                    UndervisningsgruppemedlemskapResource r = new UndervisningsgruppemedlemskapResource();
                    setPeriode(r);
                    setSystemId(r, elevforholdResource, g);
                    r.addElevforhold(Link.with(Elevforhold.class, "systemid", elevforholdResource.getSystemId().getIdentifikatorverdi()));
                    r.addUndervisningsgruppe(Link.with(g));
                    return r;
                });
    }

    private Stream<FintLinks> kontaktlarergruppeGenerator(ElevforholdResource elevforholdResource) {
        return elevforholdResource
                .getKontaktlarergruppe()
                .stream()
                .map(Link::getHref)
                .map(g -> {
                    KontaktlarergruppemedlemskapResource r = new KontaktlarergruppemedlemskapResource();
                    setPeriode(r);
                    setSystemId(r, elevforholdResource, g);
                    r.addElevforhold(Link.with(Elevforhold.class, "systemid", elevforholdResource.getSystemId().getIdentifikatorverdi()));
                    r.addKontaktlarergruppe(Link.with(g));
                    return r;
                });
    }

    private Stream<FintLinks> basisgruppeGenerator(ElevforholdResource elevforholdResource) {
        return elevforholdResource
                .getBasisgruppe()
                .stream()
                .map(Link::getHref)
                .map(g -> {
                    BasisgruppemedlemskapResource r = new BasisgruppemedlemskapResource();
                    setPeriode(r);
                    setSystemId(r, elevforholdResource, g);
                    r.addElevforhold(Link.with(Elevforhold.class, "systemid", elevforholdResource.getSystemId().getIdentifikatorverdi()));
                    r.addBasisgruppe(Link.with(g));
                    return r;
                });
    }

    @Override
    public void accept(Event<FintLinks> event) {
        stream(ElevforholdResource.class).map(ElevforholdResource.class::cast).flatMap(generators.get(event.getAction())).forEach(event::addData);
        event.setResponseStatus(ResponseStatus.ACCEPTED);
    }

    @Override
    public Set<String> actions() {
        return generators.keySet();
    }


    private void setSystemId(Gruppemedlemskap g, ElevforholdResource elevforholdResource, String href) {
        Identifikator i = new Identifikator();
        i.setIdentifikatorverdi(
                String.format("%s_%s",
                        elevforholdResource.getSystemId().getIdentifikatorverdi(),
                        StringUtils.substringAfterLast(href, "/")));
        g.setSystemId(i);
    }

    private void setPeriode(Gruppemedlemskap result) {
        Periode periode = getSchoolYear();
        result.setGyldighetsperiode(periode);
    }

    public static Periode getSchoolYear() {
        Periode periode = new Periode();
        LocalDate startDate = LocalDate.now().with(Month.AUGUST).withDayOfMonth(20).with(DayOfWeek.MONDAY);
        if (startDate.isAfter(LocalDate.now())) {
            startDate = startDate.minusYears(1);
        }
        LocalDate endDate = LocalDate.now().with(Month.JUNE).withDayOfMonth(20).with(DayOfWeek.FRIDAY);
        if (endDate.isBefore(LocalDate.now())) {
            endDate = endDate.plusYears(1);
        }
        periode.setStart(Date.valueOf(startDate));
        periode.setSlutt(Date.valueOf(endDate));
        return periode;
    }

}
