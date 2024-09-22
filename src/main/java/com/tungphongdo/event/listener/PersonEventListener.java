package com.tungphongdo.event.listener;

import com.tungphongdo.entity.Person;
import com.tungphongdo.event.PersonAnotherCreatedEvent;
import com.tungphongdo.event.PersonCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonEventListener {

    @EventListener
    @Async
    public void handlePersonCreatedEvent(PersonCreatedEvent event) {
        Person person = event.getPerson();
        log.info("User {} is created successfully", person.getName());
    }

    @EventListener
    @Async
    public void handlePersonAnotherCreatedEvent(PersonAnotherCreatedEvent event) {
        String name = event.getPersonName();
        int age = event.getPersonAge();
        log.info("Name - {}", name);
        log.info("Age - {}", age);
    }
}
