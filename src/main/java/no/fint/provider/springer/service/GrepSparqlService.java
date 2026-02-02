package no.fint.provider.springer.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator;
import no.novari.fint.model.resource.Link;
import no.novari.fint.model.resource.utdanning.utdanningsprogram.ArstrinnResource;
import org.apache.jena.query.*;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class GrepSparqlService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${fint.grep.sparql.endpoint:http://data.udir.no/kl06/sparql}")
	private String grepSparqlEndpoint;

	@Value("${fint.grep.rest.endpoint:https://data.udir.no/kl06/v201906/{id}}")
	private String grepRestEndpoint;

	public Stream<ArstrinnResource> getArstrinnFromRest() {
		try {
			return Arrays.stream(restTemplate.getForObject(grepRestEndpoint, GrepObject[].class, "aarstrinn"))
					.map(grepObject -> {
						ArstrinnResource resource = new ArstrinnResource();
						resource.setNavn(grepObject.getCode());
						resource.setBeskrivelse(grepObject.getTitles().stream()
								.filter(object -> object.getLanguage().equalsIgnoreCase("default"))
								.map(GrepObject.Title::getValue)
								.findFirst()
								.orElse(null)
						);
						Identifikator systemId = new Identifikator();
						systemId.setIdentifikatorverdi(grepObject.getCode());
						resource.setSystemId(systemId);
						resource.addGrepreferanse(Link.with(grepObject.getUrlData()));

						return resource;
					});
		} catch (RestClientException ex) {
			log.error(ex.getMessage());

			return Stream.empty();
		}
	}

	@Data
	static class GrepObject {
		@JsonProperty(value = "url-data")
		private String urlData;

		@JsonProperty(value = "kode")
		private String code;

		@JsonProperty(value = "tittel")
		private List<Title> titles;

		@Data
		static class Title {
			@JsonProperty(value = "spraak")
			private String language;

			@JsonProperty(value = "verdi")
			private String value;
		}
	}

	public Stream<ArstrinnResource> getArstrinn() {
	    Stream.Builder<ArstrinnResource> builder = Stream.builder();
		Query query = QueryFactory.create(Constants.ARSTRINN);
		try (QueryExecution qexec = QueryExecutionHTTP.service(grepSparqlEndpoint).query(query).build()) {

			ResultSet results = qexec.execSelect();

			while (results.hasNext()) {
				QuerySolution next = results.next();
                ArstrinnResource r = new ArstrinnResource();
                r.setNavn(next.get("kode").toString());
                r.setBeskrivelse(next.get("tittel").toString());
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
		static final String ARSTRINN = """
                PREFIX grep:<http://psi.udir.no/ontologi/kl06/>
                SELECT DISTINCT ?id ?kode ?tittel ?url
                WHERE {
                  ?x a grep:aarstrinn ;
                  grep:id ?id ;
                  grep:kode ?kode ;
                  grep:url-data ?url ;
                  grep:tittel ?tittel
                  FILTER (lang(?tittel) = "default")
                }
                """;
	}
}
