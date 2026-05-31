package com.tungphongdo;

import com.tungphongdo.entity.Person;
import com.tungphongdo.event.PersonAnotherCreatedEvent;
import com.tungphongdo.event.PersonCreatedEvent;
import com.tungphongdo.repository.PersonRepository;
import com.tungphongdo.service.JsonDataProcessor;
import com.tungphongdo.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

	@Mock
	private PersonRepository personRepository;

	@Mock
	private ApplicationEventPublisher eventPublisher;

	@Mock
	private JsonDataProcessor jsonDataProcessor;

	private PersonService personService;

	@BeforeEach
	void setUp() {
		personService = new PersonService(personRepository, eventPublisher, jsonDataProcessor);
	}

	@Test
	void testGetPersons() {
		when(personRepository.findAll())
				.thenReturn(
						Arrays.asList(
								Person.builder().id(1L).name("TungHT").age(25).build(),
								Person.builder().id(2L).name("John Wick").age(30).build(),
								Person.builder().id(3L).name("Selena").age(27).build()
						)
				);

		List<Person> persons = personService.getPersons();

		assertNotNull(persons);
		assertEquals(3, persons.size());
		verify(personRepository).findAll();
	}

	@Test
	void testCreatePersonPublishesEvents() {
		Person person = Person.builder().name("Alice").age(28).build();
		Person savedPerson = Person.builder().id(10L).name("Alice").age(28).build();
		when(personRepository.save(person)).thenReturn(savedPerson);

		Person result = personService.createPerson(person);

		assertEquals(savedPerson, result);
		verify(personRepository).save(person);
		verify(eventPublisher).publishEvent(any(PersonCreatedEvent.class));
		verify(eventPublisher).publishEvent(any(PersonAnotherCreatedEvent.class));
		verify(eventPublisher, times(2)).publishEvent(any());
	}

	@Test
	void testDeletePersonSuccess() {
		String result = personService.deletePerson(1L);

		assertEquals("Deleted person successfully", result);
		verify(personRepository).deleteById(1L);
	}

	@Test
	void testDeletePersonFailure() {
		doThrow(new RuntimeException("boom")).when(personRepository).deleteById(1L);

		String result = personService.deletePerson(1L);

		assertEquals("Something went wrong", result);
		verify(personRepository).deleteById(1L);
	}

}
