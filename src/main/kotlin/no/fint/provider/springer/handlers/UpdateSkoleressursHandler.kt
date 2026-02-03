package no.fint.provider.springer.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import no.novari.fint.model.resource.utdanning.elev.SkoleressursResource
import no.novari.fint.model.utdanning.elev.ElevActions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import no.fint.provider.springer.storage.Wrapper

@Service
class UpdateSkoleressursHandler(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper,
    mapper: ObjectMapper
) : UpdateHandler<SkoleressursResource>(mongoTemplate, wrapper, mapper, SkoleressursResource::class.java) {
    override fun actions(): Set<String> = setOf(ElevActions.UPDATE_SKOLERESSURS.name)
}
