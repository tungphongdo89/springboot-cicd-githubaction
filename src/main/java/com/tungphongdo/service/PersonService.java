package com.tungphongdo.service;

import com.tungphongdo.entity.Person;
import com.tungphongdo.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository repository;

    public List<Person> getPersons() {
        return repository.findAll();
    }

    public Person createPerson(Person person) {
        return repository.save(person);
    }

    public String deletePerson(Long id) {
        try {
            repository.deleteById(id);
            return "Deleted person successfully";
        } catch (Exception ex) {
            return "Something went wrong";
        }

    }
}

