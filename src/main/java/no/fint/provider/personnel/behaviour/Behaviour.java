package no.fint.provider.personnel.behaviour;

import no.fint.event.model.Event;
import org.jooq.lambda.function.Consumer2;

public interface Behaviour<T> extends Consumer2<Event,T> {
}
