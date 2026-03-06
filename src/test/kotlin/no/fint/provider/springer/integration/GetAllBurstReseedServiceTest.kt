package no.fint.provider.springer.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.client.result.DeleteResult
import no.fint.provider.springer.seeder.BaseSeeder
import no.fint.provider.springer.service.GetAllBurstReseedService
import no.fint.provider.springer.storage.Springer
import no.fint.provider.springer.storage.Wrapper
import no.novari.fint.model.resource.FintLinks
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.context.ApplicationContext
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class GetAllBurstReseedServiceTest {

    @Test
    fun `should reseed on third GET_ALL inside ten seconds`() {
        val mongoTemplate = mock(MongoTemplate::class.java)
        val applicationContext = mock(ApplicationContext::class.java)
        val wrapper = Wrapper(ObjectMapper())
        val clock = MutableClock(Instant.parse("2026-03-06T10:00:00Z"))

        `when`(mongoTemplate.remove(org.mockito.ArgumentMatchers.any(Query::class.java), org.mockito.ArgumentMatchers.eq(Springer::class.java)))
            .thenReturn(DeleteResult.acknowledged(0))
        `when`(applicationContext.getBeansOfType(BaseSeeder::class.java))
            .thenReturn(emptyMap())

        val service = GetAllBurstReseedService(mongoTemplate, wrapper, applicationContext, clock)

        repeat(3) {
            service.registerGetAll(FintLinks::class.java, "GET_ALL_TEST")
            clock.plusSeconds(3)
        }

        verify(mongoTemplate, times(1))
            .remove(org.mockito.ArgumentMatchers.any(Query::class.java), org.mockito.ArgumentMatchers.eq(Springer::class.java))
    }

    @Test
    fun `should not reseed when burst window has expired`() {
        val mongoTemplate = mock(MongoTemplate::class.java)
        val applicationContext = mock(ApplicationContext::class.java)
        val wrapper = Wrapper(ObjectMapper())
        val clock = MutableClock(Instant.parse("2026-03-06T10:00:00Z"))

        `when`(mongoTemplate.remove(org.mockito.ArgumentMatchers.any(Query::class.java), org.mockito.ArgumentMatchers.eq(Springer::class.java)))
            .thenReturn(DeleteResult.acknowledged(0))
        `when`(applicationContext.getBeansOfType(BaseSeeder::class.java))
            .thenReturn(emptyMap())

        val service = GetAllBurstReseedService(mongoTemplate, wrapper, applicationContext, clock)

        service.registerGetAll(FintLinks::class.java, "GET_ALL_TEST")
        clock.plusSeconds(11)
        service.registerGetAll(FintLinks::class.java, "GET_ALL_TEST")
        clock.plusSeconds(11)
        service.registerGetAll(FintLinks::class.java, "GET_ALL_TEST")

        verify(mongoTemplate, never())
            .remove(org.mockito.ArgumentMatchers.any(Query::class.java), org.mockito.ArgumentMatchers.eq(Springer::class.java))
    }

    private class MutableClock(
        private var instant: Instant,
        private val zoneId: ZoneId = ZoneId.of("UTC")
    ) : Clock() {
        override fun getZone(): ZoneId = zoneId

        override fun withZone(zone: ZoneId): Clock = MutableClock(instant, zone)

        override fun instant(): Instant = instant

        fun plusSeconds(seconds: Long) {
            instant = instant.plusSeconds(seconds)
        }
    }
}
