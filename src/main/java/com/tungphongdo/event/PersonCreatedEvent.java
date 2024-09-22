package com.tungphongdo.event;

import com.tungphongdo.entity.Person;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PersonCreatedEvent extends ApplicationEvent {
    private Person person;

    public PersonCreatedEvent(Object source, Person person) {
        super(source);
        this.person = person;
    }
}
