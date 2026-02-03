package no.fint.provider.springer.storage

import no.novari.fint.model.resource.administrasjon.personal.LonnResource
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Repository

@Repository
abstract class LonnRepository(
    mongoTemplate: org.springframework.data.mongodb.core.MongoTemplate,
    wrapper: Wrapper
) : SpringerRepository(mongoTemplate, wrapper) {

    protected fun <T : LonnResource> findConflicts(data: List<T>, type: Class<T>): List<T> {
        val sourceSystemIds = data
            .filterNotNull()
            .mapNotNull { it.kildesystemId?.identifikatorverdi }
            .filter(StringUtils::isNotBlank)
            .toSet()

        return mongoTemplate.stream(wrapper.query(type), Springer::class.java)
            .map(wrapper.unwrapper(type))
            .filter {
                val id = it.kildesystemId?.identifikatorverdi
                id != null && StringUtils.isNotBlank(id) && sourceSystemIds.contains(id)
            }
            .toList()
    }
}
