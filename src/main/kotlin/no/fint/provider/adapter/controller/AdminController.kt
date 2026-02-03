package no.fint.provider.adapter.controller

import no.fint.provider.adapter.sse.SseInitializer
import no.fint.sse.FintSse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/admin"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AdminController(
    private val sseInitializer: SseInitializer
) {

    @GetMapping("/sse")
    fun getSseConnections(): List<FintSse> = sseInitializer.sseClients

    @DeleteMapping("/sse")
    fun destroySseConnections() {
        sseInitializer.cleanup()
    }

    @PostMapping("/sse")
    fun initSseConnections() {
        sseInitializer.init()
    }
}
