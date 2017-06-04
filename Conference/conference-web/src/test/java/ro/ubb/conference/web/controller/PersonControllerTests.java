package ro.ubb.conference.web.controller;

/**
 * Created by paul on 6/4/2017.
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.core.service.PersonService;
import ro.ubb.conference.web.converter.PersonConverter;
import ro.ubb.conference.web.dto.PersonDto;

import java.util.*;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.core.service.PersonService;
import ro.ubb.conference.web.converter.PersonConverter;
import ro.ubb.conference.web.dto.PersonDto;


/**
 * Created by langchristian96 on 5/30/2017.
 */
public class PersonControllerTests {
    private MockMvc mockMvc;

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;

    @Mock
    private PersonConverter personConverter;


    private Person person1;
    private Person person2;
    private PersonDto personDto1;
    private PersonDto personDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(personController)
                .build();
        initData();
    }



    @Test
    public void getPerson() throws Exception {
        List<Person> persons = Arrays.asList(person1, person2);
        Set<PersonDto> personDtos =
                new HashSet<>(Arrays.asList(personDto1,personDto2));
        when(personService.findAll()).thenReturn(persons);
        when(personConverter.convertModelsToDtos(persons)).thenReturn(personDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.persons", hasSize(2)))
                .andExpect(jsonPath("$.persons[0].name", anyOf(is("name1"), is("name2"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println(result);

        verify(personService, times(1)).findAll();
        verify(personConverter, times(1)).convertModelsToDtos(persons);
        verifyNoMoreInteractions(personService, personConverter);


    }

    @Test
    public void deletePerson() throws Exception{
        doNothing().when(personService).deletePerson(person1.getId());
        ResultActions resultActions=mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/persons/{personId}",person1.getId()))


                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));



        verify(personService, times(1)).deletePerson(person1.getId());
        verifyNoMoreInteractions(personService, personConverter);

    }

    @Test
    public void createPerson() throws Exception{
        Person p=new Person("newPerson","1234","newPerson","newPerson","newPerson@newPerson.com");
        PersonDto personDto=createPersonDto(p);
        when(personService.createPerson(p.getUsern(),p.getPassword(),p.getName(),p.getAffiliation(),p.getEmail()))
                .thenReturn(p);

        Map<String,PersonDto> personDtoMap=new HashMap<>();
        personDtoMap.put("person",personDto);

        ResultActions resultActions=mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/person",personDto)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(personDtoMap)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
                //.andExpect(jsonPath("$.person", is("")));



        verify(personService, times(1)).createPerson(p.getUsern(),p.getPassword(),p.getName(),p.getAffiliation(),p.getEmail());
        //verifyNoMoreInteractions(personService, personConverter);
    }

    @Test
    public void updatePerson() throws Exception {

        when(personService.updatePerson(person1.getId()
                ,personDto1.getPassword(),personDto1.getName(),personDto1.getAffiliation(),personDto1.getEmail()))
                .thenReturn(person1);

        when(personConverter.convertModelToDto(person1)).thenReturn(personDto1);

        Map<String, PersonDto> personDtoHashMap = new HashMap<>();
        personDtoHashMap.put("person", personDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/persons/{personId}", person1.getId(), personDtoHashMap)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(personDtoHashMap)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.person.name", is("name1")));

        verify(personService, times(1)).updatePerson(person1.getId()
                ,personDto1.getPassword(),personDto1.getName(),personDto1.getAffiliation(),personDto1.getEmail());
        //verifyNoMoreInteractions(personService, personConverter);
    }



    private String toJsonString(Map<String, PersonDto> personDtoMap) {
        try {
            return new ObjectMapper().writeValueAsString(personDtoMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initData(){
        person1=Person.builder().usern("name1").name("name1").password("1234").email("name1@name1.com").affiliation("affiliation1").build();
        person1.setId(2l);
        person2=Person.builder().usern("name2").name("name2").password("1234").email("name2@name2.com").affiliation("affiliation2").build();
        person2.setId(3l);

        personDto1=createPersonDto(person1);
        personDto2=createPersonDto(person2);

    }


    private PersonDto createPersonDto(Person person) {
        PersonDto personDto=PersonDto.builder().usern(person.getUsern())
                .name(person.getName())
                .affiliation(person.getAffiliation())
                .email(person.getEmail())
                .password(person.getPassword())
                .build();
        personDto.setId(person.getId());
        return personDto;

    }

}

