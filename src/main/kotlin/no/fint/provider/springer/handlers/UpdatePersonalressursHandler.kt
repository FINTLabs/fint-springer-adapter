package no.fint.provider.springer.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import no.novari.fint.model.administrasjon.personal.PersonalActions
import no.novari.fint.model.resource.administrasjon.personal.PersonalressursResource
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import no.fint.provider.springer.storage.Wrapper

@Service
class UpdatePersonalressursHandler(
    mongoTemplate: MongoTemplate,
    wrapper: Wrapper,
    mapper: ObjectMapper
) : UpdateHandler<PersonalressursResource>(mongoTemplate, wrapper, mapper, PersonalressursResource::class.java) {
    override fun actions(): Set<String> = setOf(PersonalActions.UPDATE_PERSONALRESSURS.name)
}
