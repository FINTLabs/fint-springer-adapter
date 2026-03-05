package no.fint.provider.springer.seeder

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import no.novari.fint.model.resource.felles.PersonResource
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource

/**
 * Generisk test som sammenligner JSON-filer med seeder-output.
 * 
 * For å teste en annen entitet, endre følgende variabler i testmetoden:
 * - entityName: navnet på entiteten (f.eks. "Person", "Personalressurs")
 * - jsonPath: stien til JSON-filen (f.eks. "springer/administrasjon.personal/person.json")
 * - seederEntities: resultatet fra seederens generateEntitiesForTest()
 * - resourceClass: klassen til resource-typen (f.eks. PersonResource::class.java)
 */
class GenericSeederTest {

    private val objectMapper = ObjectMapper()

    /**
     * Test for Fravar-entiteten.
     */
    @Test
    fun testFravarSeederMatchesJson() {
        val entityName = "Fravar"
        val jsonPath = "springer/administrasjon.personal/fravar.json"
        val seederEntities = TestFravarSeeder().generateTestEntities()
        val resourceClass = no.novari.fint.model.resource.administrasjon.personal.FravarResource::class.java
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Fasttillegg-entiteten.
     */
    @Test
    fun testFasttilleggSeederMatchesJson() {
        val entityName = "Fasttillegg"
        val jsonPath = "springer/administrasjon.personal/fasttillegg.json"
        val seederEntities = TestFasttilleggSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Fastlonn-entiteten.
     */
    @Test
    fun testFastlonnSeederMatchesJson() {
        val entityName = "Fastlonn"
        val jsonPath = "springer/administrasjon.personal/fastlonn.json"
        val seederEntities = TestFastlonnSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Arbeidsforhold-entiteten.
     */
    @Test
    fun testArbeidsforholdSeederMatchesJson() {
        val entityName = "Arbeidsforhold"
        val jsonPath = "springer/administrasjon.personal/arbeidsforhold.json"
        val seederEntities = TestArbeidsforholdSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Variabellonn-entiteten.
     */
    @Test
    fun testVariabellonnSeederMatchesJson() {
        val entityName = "Variabellonn"
        val jsonPath = "springer/administrasjon.personal/variabellonn.json"
        val seederEntities = TestVariabellonnSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Fullmakt-entiteten.
     */
    @Test
    fun testFullmaktSeederMatchesJson() {
        val entityName = "Fullmakt"
        val jsonPath = "springer/administrasjon.fullmakt/fullmakt.json"
        val seederEntities = TestFullmaktSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Rolle-entiteten.
     */
    @Test
    fun testRolleSeederMatchesJson() {
        val entityName = "Rolle"
        val jsonPath = "springer/administrasjon.fullmakt/rolle.json"
        val seederEntities = TestRolleSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Ansvar-kodeverk.
     */
    @Test
    fun testAnsvarSeederMatchesJson() {
        val entityName = "Ansvar"
        val jsonPath = "springer/administrasjon.kodeverk/ansvar.json"
        val seederEntities = TestAnsvarSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Arbeidsforholdstype-kodeverk.
     */
    @Test
    fun testArbeidsforholdstypeSeederMatchesJson() {
        val entityName = "Arbeidsforholdstype"
        val jsonPath = "springer/administrasjon.kodeverk/arbeidsforholdstype.json"
        val seederEntities = TestArbeidsforholdstypeSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Art-kodeverk.
     */
    @Test
    fun testArtSeederMatchesJson() {
        val entityName = "Art"
        val jsonPath = "springer/administrasjon.kodeverk/art.json"
        val seederEntities = TestArtSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Fravarsgrunn-kodeverk.
     */
    @Test
    fun testFravarsgrunnSeederMatchesJson() {
        val entityName = "Fravarsgrunn"
        val jsonPath = "springer/administrasjon.kodeverk/fravarsgrunn.json"
        val seederEntities = TestFravarsgrunnSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Fravarstype-kodeverk.
     */
    @Test
    fun testFravarstypeSeederMatchesJson() {
        val entityName = "Fravarstype"
        val jsonPath = "springer/administrasjon.kodeverk/fravarstype.json"
        val seederEntities = TestFravarstypeSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Funksjon-kodeverk.
     */
    @Test
    fun testFunksjonSeederMatchesJson() {
        val entityName = "Funksjon"
        val jsonPath = "springer/administrasjon.kodeverk/funksjon.json"
        val seederEntities = TestFunksjonSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Lonnsart-kodeverk.
     */
    @Test
    fun testLonnsartSeederMatchesJson() {
        val entityName = "Lonnsart"
        val jsonPath = "springer/administrasjon.kodeverk/lonnsart.json"
        val seederEntities = TestLonnsartSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Personalressurskategori-kodeverk.
     */
    @Test
    fun testPersonalressurskategoriSeederMatchesJson() {
        val entityName = "Personalressurskategori"
        val jsonPath = "springer/administrasjon.kodeverk/personalressurskategori.json"
        val seederEntities = TestPersonalressurskategoriSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Prosjekt-kodeverk.
     */
    @Test
    fun testProsjektSeederMatchesJson() {
        val entityName = "Prosjekt"
        val jsonPath = "springer/administrasjon.kodeverk/prosjekt.json"
        val seederEntities = TestProsjektSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Stillingskode-kodeverk.
     */
    @Test
    fun testStillingskodeSeederMatchesJson() {
        val entityName = "Stillingskode"
        val jsonPath = "springer/administrasjon.kodeverk/stillingskode.json"
        val seederEntities = TestStillingskodeSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Test for Uketimetall-kodeverk.
     */
    @Test
    fun testUketimetallSeederMatchesJson() {
        val entityName = "Uketimetall"
        val jsonPath = "springer/administrasjon.kodeverk/uketimetall.json"
        val seederEntities = TestUketimetallSeeder().generateTestEntities()
        
        runSeederTest(entityName, jsonPath, seederEntities)
    }
    
    /**
     * Kjører seeder-test med gitt konfigurasjon
     */
    private fun runSeederTest(
        entityName: String,
        jsonPath: String,
        seederEntities: List<*>
    ) {
        // Last JSON-fil
        val jsonContent = loadJsonFile(jsonPath)
        val jsonEntries = extractEntriesFromJson(jsonContent)
        
        // Valider antall
        println("📊 $entityName: JSON har ${jsonEntries.size} entries, seeder genererte ${seederEntities.size} entiteter")
        assertTrue(seederEntities.isNotEmpty(), "$entityName seeder skal generere minst én entitet")
        
        // Konverter seeder-output til JSON for sammenligning
        val seederJson = seederEntities.map { objectMapper.valueToTree<JsonNode>(it) }
        
        // Valider at alle seeder-entiteter har påkrevde felt
        seederJson.forEachIndexed { index, node ->
            validateRequiredFields(node, entityName, index)
        }
        
        // Valider at alle seeder-entiteter har _links
        seederJson.forEachIndexed { index, node ->
            validateLinks(node, entityName, index)
        }
        
        // Sammenlign relasjoner mellom JSON og seeder
        println("\n🔗 Sammenligner relasjoner:")
        compareRelations(jsonEntries, seederJson, entityName)
        
        println("✅ $entityName seeder test fullført: ${seederEntities.size} entiteter validert")
    }
    
    /**
     * Laster en JSON-fil fra classpath
     */
    private fun loadJsonFile(path: String): JsonNode {
        val resource = ClassPathResource(path)
        return objectMapper.readTree(resource.inputStream)
    }
    
    /**
     * Ekstraherer _embedded.entries fra JSON-filen
     */
    private fun extractEntriesFromJson(jsonContent: JsonNode): List<JsonNode> {
        val embedded = jsonContent.get("_embedded")
            ?: throw AssertionError("JSON mangler _embedded-felt")
        
        val entries = embedded.get("_entries")
            ?: throw AssertionError("JSON mangler _embedded._entries-felt")
        
        return entries.toList()
    }
    
    /**
     * Validerer at en entitet har påkrevde felt (ikke-null)
     */
    private fun validateRequiredFields(node: JsonNode, entityName: String, index: Int) {
        // Sjekk systemId, fodselsnummer eller navn (avhengig av entitetstype)
        val hasSystemId = node.has("systemId") && !node.get("systemId").isNull
        val hasFodselsnummer = node.has("fodselsnummer") && !node.get("fodselsnummer").isNull
        val hasNavn = node.has("navn") && !node.get("navn").isNull
        
        assertTrue(
            hasSystemId || hasFodselsnummer || hasNavn,
            "$entityName[$index] må ha enten systemId, fodselsnummer eller navn som identifikator"
        )
        
        // For Person-spesifikke felt
        if (entityName == "Person") {
            assertNotNull(
                node.get("navn"),
                "$entityName[$index] mangler 'navn'-felt"
            )
            assertFalse(
                node.get("navn").isNull,
                "$entityName[$index] har null 'navn'"
            )
        }
    }
    
    /**
     * Validerer at en entitet har _links
     */
    private fun validateLinks(node: JsonNode, entityName: String, index: Int) {
        val links = node.get("_links")
        assertNotNull(links, "$entityName[$index] mangler _links")
        
        val selfLink = links.get("self")
        assertNotNull(selfLink, "$entityName[$index] mangler _links.self")
        assertTrue(selfLink.isArray && selfLink.size() > 0, "$entityName[$index]._links.self skal være en ikke-tom array")
    }
    
    /**
     * Sammenligner relasjoner mellom JSON og seeder-output
     */
    private fun compareRelations(
        jsonEntries: List<JsonNode>,
        seederEntries: List<JsonNode>,
        entityName: String
    ) {
        // Samle alle unike relasjonstyper fra JSON (case-insensitive)
        val allJsonRelations = mutableSetOf<String>()
        jsonEntries.forEach { entry ->
            val links = entry.get("_links")
            if (links != null && links.isObject) {
                links.fieldNames().forEach { fieldName ->
                    if (fieldName != "self") {
                        allJsonRelations.add(fieldName.lowercase())
                    }
                }
            }
        }
        
        // Samle alle unike relasjonstyper fra seeder (case-insensitive)
        val allSeederRelations = mutableSetOf<String>()
        seederEntries.forEach { entry ->
            val links = entry.get("_links")
            if (links != null && links.isObject) {
                links.fieldNames().forEach { fieldName ->
                    if (fieldName != "self") {
                        allSeederRelations.add(fieldName.lowercase())
                    }
                }
            }
        }
        
        println("  JSON relasjoner: ${allJsonRelations.sorted()}")
        println("  Seeder relasjoner: ${allSeederRelations.sorted()}")
        
        // Finn manglende relasjoner (case-insensitive)
        val missingInSeeder = allJsonRelations - allSeederRelations
        val extraInSeeder = allSeederRelations - allJsonRelations
        
        if (missingInSeeder.isNotEmpty()) {
            println("  ⚠️  MANGLER i seeder: ${missingInSeeder.sorted()}")
        }
        
        if (extraInSeeder.isNotEmpty()) {
            println("  ℹ️  Ekstra i seeder (ikke i JSON): ${extraInSeeder.sorted()}")
        }
        
        if (missingInSeeder.isEmpty() && extraInSeeder.isEmpty()) {
            println("  ✅ Alle relasjoner matcher!")
        }
        
        // Detaljert sammenligning per entitet
        println("\n  📋 Detaljert sammenligning per entitet:")
        jsonEntries.forEachIndexed { index, jsonEntry ->
            if (index < seederEntries.size) {
                val seederEntry = seederEntries[index]
                compareEntityRelations(jsonEntry, seederEntry, index, entityName)
            }
        }
    }
    
    /**
     * Sammenligner relasjoner for en enkelt entitet
     */
    private fun compareEntityRelations(
        jsonEntry: JsonNode,
        seederEntry: JsonNode,
        index: Int,
        entityName: String
    ) {
        val jsonLinks = jsonEntry.get("_links")
        val seederLinks = seederEntry.get("_links")
        
        if (jsonLinks == null || !jsonLinks.isObject) return
        if (seederLinks == null || !seederLinks.isObject) {
            println("    [$index] ⚠️  Seeder mangler _links helt")
            return
        }
        
        val jsonRelations = jsonLinks.fieldNames().asSequence().filter { it != "self" }.map { it.lowercase() }.toSet()
        val seederRelations = seederLinks.fieldNames().asSequence().filter { it != "self" }.map { it.lowercase() }.toSet()
        
        val missing = jsonRelations - seederRelations
        val extra = seederRelations - jsonRelations
        
        if (missing.isNotEmpty() || extra.isNotEmpty()) {
            println("    [$index] Relasjoner:")
            if (missing.isNotEmpty()) {
                println("      ⚠️  MANGLER: ${missing.sorted()}")
            }
            if (extra.isNotEmpty()) {
                println("      ℹ️  Ekstra: ${extra.sorted()}")
            }
        }
    }
}

/**
 * Test-wrapper for FravarSeeder
 */
private class TestFravarSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.personal.FravarResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.personal.FravarSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for FasttilleggSeeder
 */
private class TestFasttilleggSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.personal.FasttilleggResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.personal.FasttilleggSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for FastlonnSeeder
 */
private class TestFastlonnSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.personal.FastlonnResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.personal.FastlonnSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for ArbeidsforholdSeeder
 */
private class TestArbeidsforholdSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.personal.ArbeidsforholdSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for VariabellonnSeeder
 */
private class TestVariabellonnSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.personal.VariabellonnResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.personal.VariabellonnSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for FullmaktSeeder
 */
private class TestFullmaktSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.fullmakt.FullmaktResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.fullmakt.FullmaktSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for RolleSeeder
 */
private class TestRolleSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.fullmakt.RolleResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.fullmakt.RolleSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for AnsvarSeeder
 */
private class TestAnsvarSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.kodeverk.AnsvarResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.kodeverk.AnsvarSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for ArbeidsforholdstypeSeeder
 */
private class TestArbeidsforholdstypeSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.kodeverk.ArbeidsforholdstypeResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.kodeverk.ArbeidsforholdstypeSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for ArtSeeder
 */
private class TestArtSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.kodeverk.ArtResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.kodeverk.ArtSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for FravarsgrunnSeeder
 */
private class TestFravarsgrunnSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.kodeverk.FravarsgrunnResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.kodeverk.FravarsgrunnSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for FravarstypeSeeder
 */
private class TestFravarstypeSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.kodeverk.FravarstypeResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.kodeverk.FravarstypeSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for FunksjonSeeder
 */
private class TestFunksjonSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.kodeverk.FunksjonResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.kodeverk.FunksjonSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for LonnsartSeeder
 */
private class TestLonnsartSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.kodeverk.LonnsartResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.kodeverk.LonnsartSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for PersonalressurskategoriSeeder
 */
private class TestPersonalressurskategoriSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.kodeverk.PersonalressurskategoriResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.kodeverk.PersonalressurskategoriSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for ProsjektSeeder
 */
private class TestProsjektSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.kodeverk.ProsjektResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.kodeverk.ProsjektSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for StillingskodeSeeder
 */
private class TestStillingskodeSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.kodeverk.StillingskodeResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.kodeverk.StillingskodeSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Test-wrapper for UketimetallSeeder
 */
private class TestUketimetallSeeder {
    fun generateTestEntities(): List<no.novari.fint.model.resource.administrasjon.kodeverk.UketimetallResource> {
        val mockRepo = MockSeederRepository()
        val seeder = no.fint.provider.springer.seeder.administrasjon.kodeverk.UketimetallSeeder(mockRepo)
        return seeder.generateEntitiesForTest()
    }
}

/**
 * Minimal mock-implementasjon av SeederRepository for testing.
 * Denne gjør ingenting, men lar oss instansiere seeders uten ekte MongoDB-tilkobling.
 */
private class MockSeederRepository : no.fint.provider.springer.storage.SeederRepository(
    MockMongoTemplate(),
    MockWrapper(ObjectMapper()),
    ObjectMapper()
) {
    override fun <T> exists(type: Class<T>): Boolean = false
    override fun <T> save(entity: T, type: Class<T>) {}
    override fun accept(response: no.fint.event.model.Event<no.novari.fint.model.resource.FintLinks>) {}
}

/**
 * Minimal mock av MongoTemplate
 */
private class MockMongoTemplate : org.springframework.data.mongodb.core.MongoTemplate(
    com.mongodb.client.MongoClients.create("mongodb://localhost:27017"),
    "test"
)

/**
 * Minimal mock av Wrapper
 */
private class MockWrapper(objectMapper: ObjectMapper) : no.fint.provider.springer.storage.Wrapper(objectMapper)
