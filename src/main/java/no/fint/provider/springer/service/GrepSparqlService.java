package no.fint.provider.springer.service;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.Link;
import no.fint.model.resource.utdanning.utdanningsprogram.ArstrinnResource;
import org.apache.jena.query.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Stream;

@Slf4j
@Service
public class GrepSparqlService {

	@Value("${fint.grep.sparql.endpoint:http://data.udir.no/kl06/sparql}")
	private String grepSparqlEndpoint;

	public Stream<ArstrinnResource> getArstrinn() {
	    Stream.Builder<ArstrinnResource> builder = Stream.builder();
		Query query = QueryFactory.create(Constants.ARSTRINN);
		try (QueryExecution qexec = QueryExecutionFactory.sparqlService(grepSparqlEndpoint, query)) {

			ResultSet results = qexec.execSelect();

			while (results.hasNext()) {
				QuerySolution next = results.next();
                ArstrinnResource r = new ArstrinnResource();
                r.setNavn(next.get("kode").toString());
                r.setBeskrivelse(next.get("tittel").toString());
                r.setPeriode(Collections.emptyList());
                Identifikator systemid = new Identifikator();
                systemid.setIdentifikatorverdi(r.getNavn());
                r.setSystemId(systemid);
                r.addGrepreferanse(Link.with(next.get("url").toString()));

                builder.add(r);
			}

		} catch (QueryException e) {
			log.warn("SparQl backend error on query {}", query, e);
		}
		return builder.build();

	}

	static final class Constants {
		static final String ARSTRINN = "PREFIX grep:<http://psi.udir.no/ontologi/kl06/>\n" +
                "SELECT DISTINCT ?id?kode?tittel?url\n" +
                "WHERE {\n" +
                "  ?s a grep:aarstrinn.\n" +
                "  ?s grep:id ?id .\n" +
                "  ?s grep:kode ?kode .\n" +
                "  ?s grep:tittel ?tittel filter (lang(?tittel) = '') .\n" +
                "  ?s grep:url-data ?url .\n" +
                "}";


	}
}
