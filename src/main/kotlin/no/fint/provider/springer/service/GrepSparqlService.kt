package no.fint.provider.springer.service

import com.fasterxml.jackson.annotation.JsonProperty
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.utdanning.utdanningsprogram.ArstrinnResource
import org.apache.jena.query.Query
import org.apache.jena.query.QueryExecution
import org.apache.jena.query.QueryFactory
import org.apache.jena.query.ResultSet
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.util.stream.Stream

@Service
class GrepSparqlService(
    private val restTemplate: RestTemplate,
    @param:Value($$"${fint.grep.sparql.endpoint:https://data.udir.no/kl06/sparql}")
    private val grepSparqlEndpoint: String,
    @param:Value($$"${fint.grep.rest.endpoint:https://data.udir.no/kl06/v201906/{id}}")
    private val grepRestEndpoint: String
) {

    private val log = LoggerFactory.getLogger(GrepSparqlService::class.java)

    fun getArstrinnFromRest(): Stream<ArstrinnResource> = try {
        restTemplate.getForObject(grepRestEndpoint, Array<GrepObject>::class.java, "aarstrinn")
            ?.asSequence()
            ?.map { grepObject ->
                ArstrinnResource().apply {
                    navn = grepObject.code
                    beskrivelse = grepObject.titles
                        .firstOrNull { it.language.equals("default", ignoreCase = true) }
                        ?.value
                    systemId = Identifikator().apply { identifikatorverdi = grepObject.code }
                    addGrepreferanse(Link.with(grepObject.urlData))
                }
            }?.toList()?.stream()
            ?: Stream.empty()
    } catch (ex: RestClientException) {
        log.error(ex.message)
        Stream.empty()
    }

    fun getArstrinn(): Stream<ArstrinnResource> {
        val builder = Stream.builder<ArstrinnResource>()
        val query: Query = QueryFactory.create(Constants.ARSTRINN)
        try {
            QueryExecutionHTTP.service(grepSparqlEndpoint).query(query).build().use { qexec: QueryExecution ->
                val results: ResultSet = qexec.execSelect()
                while (results.hasNext()) {
                    val next = results.next()
                    val r = ArstrinnResource().apply {
                        navn = next.get("kode").toString()
                        beskrivelse = next.get("tittel").toString()
                        systemId = Identifikator().apply { identifikatorverdi = navn }
                        addGrepreferanse(Link.with(next.get("url").toString()))
                    }
                    builder.add(r)
                }
            }
        } catch (e: Exception) {
            log.warn("SparQl backend error on query {}", query, e)
        }
        return builder.build()
    }

    data class GrepObject(
        @param:JsonProperty("url-data") val urlData: String,
        @param:JsonProperty("kode") val code: String,
        @param:JsonProperty("tittel") val titles: List<Title>
    ) {
        data class Title(
            @param:JsonProperty("spraak") val language: String,
            @param:JsonProperty("verdi") val value: String
        )
    }

    private object Constants {
        const val ARSTRINN = """
                PREFIX grep:<http://psi.udir.no/ontologi/kl06/>
                SELECT DISTINCT ?id ?kode ?tittel ?url
                WHERE {
                  ?x a grep:aarstrinn ;
                  grep:id ?id ;
                  grep:kode ?kode ;
                  grep:url-data ?url ;
                  grep:tittel ?tittel
                  FILTER (lang(?tittel) = \"default\")
                }
                """
    }
}
