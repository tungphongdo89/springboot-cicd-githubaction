package com.tungphongdo.service;

import com.tungphongdo.entity.Person;
import com.tungphongdo.event.PersonAnotherCreatedEvent;
import com.tungphongdo.event.PersonCreatedEvent;
import com.tungphongdo.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository repository;

    private final ApplicationEventPublisher eventPublisher;

    private final JsonDataProcessor jsonDataProcessor;


    public List<Person> getPersons() {
        return repository.findAll();
    }

    public Person createPerson(Person person) {
        person = repository.save(person);
        eventPublisher.publishEvent(new PersonCreatedEvent(this, person));
        eventPublisher.publishEvent(new PersonAnotherCreatedEvent(this, person.getName(), person.getAge()));
        return person;
    }

    public String deletePerson(Long id) {
        try {
            repository.deleteById(id);
            return "Deleted person successfully";
        } catch (Exception ex) {
            return "Something went wrong";
        }

    }

    public String testAdapterPatterns(String dataType) {
        return null;
    }
}

