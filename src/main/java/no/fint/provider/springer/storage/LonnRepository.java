package no.fint.provider.springer.storage;

import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.administrasjon.personal.LonnResource;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class LonnRepository extends SpringerRepository {
    protected <T extends LonnResource> List<T> findConflicts(List<T> data, Class<T> type) {
        Set<String> sourceSystemIds = data.stream()
                .filter(Objects::nonNull)
                .map(LonnResource::getKildesystemId)
                .filter(Objects::nonNull)
                .map(Identifikator::getIdentifikatorverdi)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());
        return mongoTemplate.stream(wrapper.query(type), Springer.class)
                .map(wrapper.unwrapper(type))
                .filter(r -> r.getKildesystemId() != null
                        && StringUtils.isNotBlank(r.getKildesystemId().getIdentifikatorverdi())
                        && sourceSystemIds.contains(r.getKildesystemId().getIdentifikatorverdi()))
                .collect(Collectors.toList());
    }
}
