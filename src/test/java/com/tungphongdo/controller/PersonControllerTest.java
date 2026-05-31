package com.tungphongdo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tungphongdo.entity.Person;
import com.tungphongdo.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc(addFilters = false)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @Test
    void getPersonsReturnsList() throws Exception {
        List<Person> persons = List.of(
                Person.builder().id(1L).name("Alice").age(20).build(),
                Person.builder().id(2L).name("Bob").age(30).build()
        );
        when(personService.getPersons()).thenReturn(persons);

        mockMvc.perform(get("/api/v1/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].name").value("Bob"));
    }

    @Test
    void createPersonReturnsCreatedPerson() throws Exception {
        Person request = Person.builder().name("Charlie").age(25).build();
        Person response = Person.builder().id(3L).name("Charlie").age(25).build();
        when(personService.createPerson(any(Person.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.name").value("Charlie"));
    }

    @Test
    void deletePersonReturnsMessage() throws Exception {
        when(personService.deletePerson(10L)).thenReturn("Deleted person successfully");

        mockMvc.perform(delete("/api/v1/person/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted person successfully"));

        verify(personService).deletePerson(10L);
    }

    @Test
    void testAdapterPatternsReturnsResult() throws Exception {
        when(personService.testAdapterPatterns("json")).thenReturn("json data");

        mockMvc.perform(get("/api/v1/person/test-adapter").param("dataType", "json"))
                .andExpect(status().isOk())
                .andExpect(content().string("json data"));

        verify(personService).testAdapterPatterns("json");
    }
}


