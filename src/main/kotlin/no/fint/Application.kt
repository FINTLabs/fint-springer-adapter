package no.fint

import no.fint.provider.adapter.FintAdapterEndpoints
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableHypermediaSupport(type = [HAL])
@EnableConfigurationProperties(FintAdapterEndpoints::class)
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
