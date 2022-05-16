package no.fint.provider.springer.model;

import com.google.common.collect.ImmutableMultimap;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.Link;
import no.fint.model.resource.utdanning.elev.BasisgruppeResource;
import no.fint.model.resource.utdanning.elev.ElevforholdResource;
import no.fint.model.resource.utdanning.utdanningsprogram.ArstrinnResource;
import no.fint.model.resource.utdanning.utdanningsprogram.ProgramomradeResource;
import no.fint.model.resource.utdanning.utdanningsprogram.SkoleResource;
import no.fint.model.resource.utdanning.utdanningsprogram.UtdanningsprogramResource;
import no.fint.model.utdanning.elev.Basisgruppe;
import no.fint.model.utdanning.elev.Elevforhold;
import no.fint.model.utdanning.timeplan.Fag;
import no.fint.model.utdanning.utdanningsprogram.Arstrinn;
import no.fint.model.utdanning.utdanningsprogram.Programomrade;
import no.fint.model.utdanning.utdanningsprogram.Skole;
import no.fint.model.utdanning.utdanningsprogram.Utdanningsprogram;
import no.fint.model.vigokodeverk.Programomrader;
import no.fint.model.vigokodeverk.Utdanningsprogrammer;
import no.fint.provider.springer.service.GrepSparqlService;
import no.fint.provider.springer.service.Handler;
import no.fint.provider.springer.storage.Springer;
import no.fint.provider.springer.storage.Wrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.util.StreamUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static no.fint.model.utdanning.utdanningsprogram.UtdanningsprogramActions.*;

@Slf4j
@Repository
public class VigoKodeverkRepository implements Handler {

    @Value("${fint.adapter.vigokv.baseuri:https://api.felleskomponent.no/utdanning/vigo/kodeverk/}")
    private String baseuri;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GrepSparqlService sparqlService;

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected Wrapper wrapper;

    private volatile Map<String, ArstrinnResource> arstrinn = Collections.emptyMap();

    @Override
    public void accept(Event<FintLinks> response) {
        switch (valueOf(response.getAction())) {
            case GET_ALL_UTDANNINGSPROGRAM:
                getUtdanningsprogram(response);
                break;
            case GET_ALL_PROGRAMOMRADE:
                getProgramomrade(response);
                break;
            case GET_ALL_ARSTRINN:
                arstrinn.values().forEach(response::addData);
                break;
        }
    }

    @Scheduled(initialDelay = 10000L, fixedDelay = 3600000L)
    public void updateArstrinn() {
        Map<@NonNull String, ArstrinnResource> map = sparqlService.getArstrinnFromRest().collect(Collectors.toMap(ArstrinnResource::getBeskrivelse, Function.identity()));
        restTemplate.exchange(
                baseuri + "/programomrader/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CollectionModel<Programomrader>>() {
                })
                .getBody()
                .getContent()
                .forEach(p -> {
                    Optional.ofNullable(map.get(p.getArstrinn()))
                            .ifPresent(trinn -> trinn.addProgramomrade(Link.with(Programomrade.class, "systemid", p.getKode().getIdentifikatorverdi())));
                });

        StreamUtils
                .createStreamFromIterator(mongoTemplate.stream(wrapper.query(BasisgruppeResource.class), Springer.class))
                .map(wrapper.unwrapper(BasisgruppeResource.class))
                .forEach(b -> map.values()
                        .stream()
                        .filter(trinn -> b.getTrinn().stream().map(Link::getHref).anyMatch(s -> s.endsWith("/" + trinn.getSystemId().getIdentifikatorverdi())))
                        .forEach(trinn -> trinn.addBasisgruppe(Link.with(Basisgruppe.class, "systemid", b.getSystemId().getIdentifikatorverdi()))
                        ));

        arstrinn = map;
        log.info("Updated Arstrinn");
    }

    private void getProgramomrade(Event<FintLinks> response) {
        ImmutableMultimap.Builder<String, String> builder = new ImmutableMultimap.Builder<>();
        StreamUtils
                .createStreamFromIterator(mongoTemplate.stream(wrapper.query(ElevforholdResource.class), Springer.class))
                .map(wrapper.unwrapper(ElevforholdResource.class))
                .forEach(s -> s.getProgramomrade()
                        .stream()
                        .map(Link::getHref)
                        .map(l -> StringUtils.substringAfterLast(l, "/"))
                        .forEach(id -> builder.put(id, s.getSystemId().getIdentifikatorverdi())));
        ImmutableMultimap<String, String> elever = builder.build();

        ResponseEntity<CollectionModel<EntityModel<Programomrader>>> result = restTemplate.exchange(
                baseuri + "/programomrader/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CollectionModel<EntityModel<Programomrader>>>() {
                });

        result.getBody()
                .getContent()
                .parallelStream()
                .map(p -> {
                    Programomrader c = p.getContent();
                    ProgramomradeResource r = new ProgramomradeResource();
                    r.setNavn(c.getNavn());
                    r.setBeskrivelse(c.getBeskrivelse());
                    r.setSystemId(c.getKode());
                    r.setPeriode(Collections.emptyList());
                    r.addVigoreferanse(Link.with(p.getLink("self").get().getHref()));
                    r.addGrepreferanse(Link.with(p.getLink("grep").get().getHref()));

                    p.getLinks()
                            .stream()
                            .filter(l -> l.getRel().value().equalsIgnoreCase("utdanningsprogram"))
                            .map(org.springframework.hateoas.Link::getHref)
                            .map(l -> StringUtils.substringAfterLast(l, "/"))
                            .map(Link.apply(Utdanningsprogram.class, "systemid"))
                            .forEach(r::addUtdanningsprogram);
                    p.getLinks()
                            .stream()
                            .filter(l -> l.getRel().value().equalsIgnoreCase("fagunderprogramomrade"))
                            .map(org.springframework.hateoas.Link::getHref)
                            .map(l -> StringUtils.substringAfterLast(l, "/"))
                            .map(Link.apply(Fag.class, "systemid"))
                            .forEach(r::addFag);

                    Optional.ofNullable(arstrinn.get(c.getArstrinn()))
                            .map(ArstrinnResource::getSystemId)
                            .map(Identifikator::getIdentifikatorverdi)
                            .map(Link.apply(Arstrinn.class, "systemid"))
                            .ifPresent(r::addTrinn);

                    elever.get(c.getKode().getIdentifikatorverdi())
                            .stream()
                            .map(Link.apply(Elevforhold.class, "systemid"))
                            .forEach(r::addElevforhold);

                    return r;
                })
                .forEach(response::addData);
    }

    private void getUtdanningsprogram(Event<FintLinks> response) {
        ImmutableMultimap.Builder<String, String> builder = new ImmutableMultimap.Builder<>();
        StreamUtils
                .createStreamFromIterator(mongoTemplate.stream(wrapper.query(SkoleResource.class), Springer.class))
                .map(wrapper.unwrapper(SkoleResource.class))
                .forEach(s -> s.getUtdanningsprogram()
                        .stream()
                        .map(Link::getHref)
                        .map(l -> StringUtils.substringAfterLast(l, "/"))
                        .forEach(id -> builder.put(id, s.getSkolenummer().getIdentifikatorverdi())));
        ImmutableMultimap<String, String> skoler = builder.build();

        ResponseEntity<CollectionModel<EntityModel<Utdanningsprogrammer>>> result = restTemplate.exchange(
                baseuri + "/utdanningsprogrammer/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CollectionModel<EntityModel<Utdanningsprogrammer>>>() {
                });

        result.getBody()
                .getContent()
                .parallelStream()
                .map(u -> {
                    Utdanningsprogrammer c = u.getContent();
                    UtdanningsprogramResource r = new UtdanningsprogramResource();
                    r.setNavn(c.getNavn());
                    r.setBeskrivelse(c.getBeskrivelse());
                    r.setSystemId(c.getKode());
                    r.setPeriode(Collections.emptyList());
                    r.addVigoreferanse(Link.with(u.getLink("self").get().getHref()));
                    r.addGrepreferanse(Link.with(u.getLink("grep").get().getHref()));
                    u.getLinks()
                            .stream()
                            .filter(l -> l.getRel().value().equalsIgnoreCase("programomrade"))
                            .map(org.springframework.hateoas.Link::getHref)
                            .map(l -> StringUtils.substringAfterLast(l, "/"))
                            .map(Link.apply(Programomrade.class, "systemid"))
                            .forEach(r::addProgramomrade);
                    skoler.get(c.getKode().getIdentifikatorverdi())
                            .stream()
                            .map(Link.apply(Skole.class, "skolenummer"))
                            .forEach(r::addSkole);
                    return r;
                })
                .forEach(response::addData);
    }

    @Override
    public Set<String> actions() {
        return Stream.of(
                GET_ALL_UTDANNINGSPROGRAM,
                GET_ALL_PROGRAMOMRADE,
                GET_ALL_ARSTRINN
        )
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
