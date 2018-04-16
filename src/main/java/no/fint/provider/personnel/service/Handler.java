package no.fint.provider.personnel.service;

import no.fint.event.model.Event;
import no.fint.model.administrasjon.personal.PersonalActions;
import no.fint.model.resource.FintLinks;

import java.util.EnumSet;
import java.util.function.Consumer;

public interface Handler extends Consumer<Event<FintLinks>> {

    default EnumSet<PersonalActions> actions() {
        return EnumSet.noneOf(PersonalActions.class);
    }

}
