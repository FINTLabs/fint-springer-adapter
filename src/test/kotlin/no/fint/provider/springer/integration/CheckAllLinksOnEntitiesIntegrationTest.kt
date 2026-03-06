package no.fint.provider.springer.integration

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.client.RestTemplate
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Manual integration test for seeder endpoints.
 * Verifies that all _links in play-with-fint resources respond with HTTP 200.
 */
class CheckAllLinksOnEntitiesIntegrationTest {

    private val objectMapper = ObjectMapper()
    private val restTemplate = RestTemplate()

    @Test
    @Disabled("External integration test - run manually")
    fun allSeederRelationsShouldReturn200() {
        seederEndpoints().forEach { endpoint ->
            val entries = fetchEntries(endpoint)
            assertTrue(entries.isNotEmpty(), "Endpoint returnerte ingen entries: $endpoint")

            assertAllLinksReturn200(endpoint, entries)
        }
    }

    private fun assertAllLinksReturn200(endpoint: String, entries: List<JsonNode>) {
        val failedLinks = mutableListOf<String>()
        var checkedLinks = 0

        entries.forEach { entry ->
            val entityRef = entityReference(entry)
            val linksNode = entry.path("_links")
            if (!linksNode.isObject) return@forEach

            linksNode.fieldNames().asSequence().forEach { linkName ->
                val linkArray = linksNode.path(linkName)
                if (!linkArray.isArray) return@forEach

                linkArray.forEach { linkNode ->
                    val rawHref = linkNode.path("href").asText()
                    if (rawHref.isBlank()) return@forEach
                    val href = resolveHref(rawHref)
                    if (!href.startsWith("http")) return@forEach

                    checkedLinks++
                    val statusCode = getStatusCodeSafe(href)
                    if (statusCode != 200) {
                        failedLinks.add(
                            "status=$statusCode endpoint=$endpoint entity=$entityRef relation=$linkName url=$href (raw=$rawHref)"
                        )
                    }
                }
            }
        }

        assertTrue(checkedLinks > 0, "Fant ingen HTTP-lenker i _links for endpoint $endpoint")
        assertTrue(
            failedLinks.isEmpty(),
            "Noen relasjoner returnerte ikke 200 for $endpoint:\n${failedLinks.joinToString("\n")}"
        )
    }

    private fun fetchEntries(endpoint: String): List<JsonNode> {
        val response = exchange(endpoint)
        assertEquals(200, response.statusCode.value(), "Klarte ikke hente endpoint $endpoint")

        val root = objectMapper.readTree(response.body ?: throw AssertionError("Tomt svar fra endpoint $endpoint"))
        val entries = root.path("_embedded").path("_entries")

        assertTrue(entries.isArray, "Svar mangler _embedded._entries for endpoint $endpoint")
        return entries.toList()
    }

    private fun getStatusCodeSafe(url: String): Int {
        return try {
            exchange(url).statusCode.value()
        } catch (e: RestClientResponseException) {
            e.statusCode.value()
        } catch (_: Exception) {
            0
        }
    }

    private fun resolveHref(href: String): String = when {
        href.startsWith("http") -> href
        href.startsWith("/") -> "$PLAY_WITH_FINT_BASE$href"
        else -> "$PLAY_WITH_FINT_BASE/$href"
    }

    private fun entityReference(entry: JsonNode): String {
        val selfHref = entry.path("_links").path("self").path(0).path("href").asText()
        if (selfHref.isNotBlank()) return selfHref

        val systemId = entry.path("systemId").path("identifikatorverdi").asText()
        if (systemId.isNotBlank()) return "systemId=$systemId"

        val fodselsnummer = entry.path("fodselsnummer").path("identifikatorverdi").asText()
        if (fodselsnummer.isNotBlank()) return "fodselsnummer=$fodselsnummer"

        return "<unknown-entity>"
    }

    private fun exchange(url: String): ResponseEntity<String> {
        val headers = HttpHeaders().apply { add("Accept", "application/hal+json") }
        return restTemplate.exchange(url, HttpMethod.GET, HttpEntity<Void>(headers), String::class.java)
    }

    companion object {
        private const val PLAY_WITH_FINT_BASE = "https://play-with-fint.felleskomponent.no"
        private const val SEEDER_ROOT = "src/main/kotlin/no/fint/provider/springer/seeder"
    }

    private fun seederEndpoints(): List<String> {
        val seederRootPath = Paths.get(System.getProperty("user.dir"), SEEDER_ROOT)
        if (!Files.exists(seederRootPath)) {
            throw IllegalStateException("Fant ikke seeder-katalog: $seederRootPath")
        }

        return Files.walk(seederRootPath).use { paths ->
            paths
                .filter { path ->
                    Files.isRegularFile(path) &&
                            path.fileName.toString().endsWith("Seeder.kt") &&
                            path.fileName.toString() != "BaseSeeder.kt"
                }
                .map { path -> toEndpoint(path, seederRootPath) }
                .toList()
        }
            .distinct()
            .sorted()
    }

    private fun toEndpoint(path: Path, seederRootPath: Path): String {
        val relativePath = seederRootPath.relativize(path)
        val domainPath = relativePath.parent
            ?.let { parent ->
                (0 until parent.nameCount)
                    .joinToString("/") { i -> parent.getName(i).toString() }
            }
            .orEmpty()
        val resourceName = path.fileName.toString().removeSuffix("Seeder.kt").lowercase()
        return "$PLAY_WITH_FINT_BASE/$domainPath/$resourceName"
    }
}
