package com.tungphongdo.controller;

import com.tungphongdo.entity.Person;
import com.tungphongdo.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/person")
public class PersonController {
    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<Person>> getPersons() {
        return ResponseEntity.ok(personService.getPersons());
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        return ResponseEntity.ok(personService.createPerson(person));
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(@PathVariable("id") Long id) {
        return ResponseEntity.ok(personService.deletePerson(id));
    }
}
