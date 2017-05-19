package ro.ubb.conference.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.core.service.ListenerService;
import ro.ubb.conference.core.service.PersonService;
import ro.ubb.conference.web.converter.ListenerConverter;
import ro.ubb.conference.web.converter.PersonConverter;
import ro.ubb.conference.web.dto.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by langchristian96 on 5/18/2017.
 */




@RestController
public class ListenerController {

    private static final Logger log = LoggerFactory.getLogger(ListenerController.class);

    @Autowired
    private ListenerService personService;

    @Autowired
    private ListenerConverter personConverter;

    @RequestMapping(value = "/listeners", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    public ListenersDto getListeners() {
        log.trace("getListeners");

        List<Listener> listeners = personService.findAll();

        log.trace("getListeners: listeners={}", listeners);

        return new ListenersDto(personConverter.convertModelsToDtos(listeners));
    }

    @RequestMapping(value = "/listeners/{personId}", method = RequestMethod.PUT)
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, ListenerDto> updateListener(
            @PathVariable final Long personId,
            @RequestBody final Map<String, ListenerDto> personDtoMap) {
        log.trace("updateListener: personId={}, personDtoMap={}", personId, personDtoMap);

        ListenerDto personDto = personDtoMap.get("listener");
        Listener person = personService.updateListener(personId,personDto.getPassword(),personDto.getName(),personDto.getAffiliation(),personDto.getEmail(), personDto.getSessions());

        Map<String, ListenerDto> result = new HashMap<>();
        result.put("listener", personConverter.convertModelToDto(person));

        log.trace("updateListener: result={}", result);

        return result;
    }

    @RequestMapping(value = "/listeners", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, ListenerDto> createListener(
            @RequestBody final Map<String, ListenerDto> personDtoMap) {
        log.trace("createListener: personDtoMap={}", personDtoMap);

        ListenerDto personDto = personDtoMap.get("listener");
        Listener person = personService.createListener(personDto.getUsern(),personDto.getPassword(),personDto.getName(),personDto.getAffiliation(),personDto.getEmail());

        Map<String, ListenerDto> result = new HashMap<>();
        result.put("listener", personConverter.convertModelToDto(person));

        log.trace("updateListener: result={}", result);

        return result;
    }

    @RequestMapping(value = "/listeners/{personId}", method = RequestMethod.DELETE)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity deletePerson(@PathVariable final Long personId) {
        log.trace("deleteListener: personId={}", personId);



        personService.deleteListener(personId);

        log.trace("deleteListener - method end");

        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
