package ro.ubb.conference.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.core.service.PaperService;
import ro.ubb.conference.core.service.PersonService;
import ro.ubb.conference.web.converter.PaperConverter;
import ro.ubb.conference.web.converter.PersonConverter;
import ro.ubb.conference.web.dto.*;

import javax.annotation.security.PermitAll;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by langchristian96 on 5/5/2017.
 */



@RestController
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonConverter personConverter;

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    public PersonsDto getPersons() {
        log.trace("getPersons");

        List<Person> persons = personService.findAll();

        log.trace("getPersons: persons={}", persons);

        return new PersonsDto(personConverter.convertModelsToDtos(persons));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/persons/{id}", method = RequestMethod.GET)
    public PersonDto getPerson(@PathVariable final Long id) {
        log.trace("getPerson");

        Person person = personService.findOne(id);

        log.trace("getPerson: person={}", person);

        return personConverter.convertModelToDto(person);
    }

    @RequestMapping(value = "/persons/{personId}", method = RequestMethod.PUT)
    @CrossOrigin(origins = "http://localhost:4200")

    public Map<String, PersonDto> updatePerson(
            @PathVariable final Long personId,
            @RequestBody final Map<String, PersonDto> personDtoMap) {
        log.trace("updatePerson: personId={}, personDtoMap={}", personId, personDtoMap);

        PersonDto personDto = personDtoMap.get("person");
        Person person = personService.updatePerson(personId,personDto.getPassword(),personDto.getName(),personDto.getAffiliation(),personDto.getEmail());

        Map<String, PersonDto> result = new HashMap<>();
        result.put("person", personConverter.convertModelToDto(person));

        log.trace("updatePerson: result={}", result);

        return result;
    }


    @RequestMapping(value = "/getPersonId/{personUsern}", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")

    public Map<String, Long> getPersonId(
            @PathVariable final String personUsern) {

        log.trace("getPersonId");

        Person person = personService.getUserByUserName(personUsern);
        Map<String, Long> result = new HashMap<>();
        result.put("personId", person.getId());

        log.trace("getPersonId: result={}", result);

        return result;

    }



    @RequestMapping(value = "/persons", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200")

    public Map<String, PersonDto> createPerson(
            @RequestBody final Map<String, PersonDto> personDtoMap) {
        log.trace("createPerson: personDtoMap={}", personDtoMap);

        PersonDto personDto = personDtoMap.get("person");
        Person person = personService.createPerson(personDto.getUsern(),passwordEncoder.encode(personDto.getPassword()),personDto.getName(),personDto.getAffiliation(),personDto.getEmail());

        Map<String, PersonDto> result = new HashMap<>();
        result.put("person", personConverter.convertModelToDto(person));

        log.trace("createPerson: result={}", result);

        return result;
    }

    @RequestMapping(value = "/persons/{personId}", method = RequestMethod.DELETE)
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity deletePerson(@PathVariable final Long personId) {
        log.trace("deletePerson: personId={}", personId);



        personService.deletePerson(personId);

        log.trace("deletePerson - method end");

        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }

    @RequestMapping(value = "/persons/{usern}", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")

    public PersonDto getPersonByUsername(@PathVariable final String usern) {
        log.trace("getPersonByUsername");

        Person person = personService.getUserByUserName(usern);

        log.trace("getPersonByUsername: person={}", person);

        return personConverter.convertModelToDto(person);
    }


}
