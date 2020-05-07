package no.fint.provider.springer.handlers;

import no.fint.model.resource.FintLinks;
import no.fint.model.resource.utdanning.elev.ElevforholdResource;

import java.util.function.Function;
import java.util.stream.Stream;

@FunctionalInterface
public interface GroupGenerator<T extends FintLinks> extends Function<ElevforholdResource, Stream<T>> {
}
