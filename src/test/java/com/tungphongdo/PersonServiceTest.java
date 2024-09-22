package com.tungphongdo;

import com.tungphongdo.entity.Person;
import com.tungphongdo.repository.PersonRepository;
import com.tungphongdo.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void testGetPersons() {
        when(personRepository.findAll())
                .thenReturn(
                        Arrays.asList(Person.builder().id(1L).name("TungHT").age(25).build(),
                            Person.builder().id(2L).name("John Wick").age(30).build(),
                            Person.builder().id(3L).name("Selena").age(27).build())
                );

        List<Person> persons = personService.getPersons();

        assertNotNull(persons);
        assertEquals(2, persons.size());
    }

}
