package com.tungphongdo.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PersonAnotherCreatedEvent extends ApplicationEvent {
    private String personName;
    private int personAge;

    public PersonAnotherCreatedEvent(Object source, String name, int age) {
        super(source);
        this.personName = name;
        this.personAge = age;
    }
}
