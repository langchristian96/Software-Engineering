package ro.ubb.conference.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.conference.core.domain.Conference;
import ro.ubb.conference.core.service.ConferenceService;
import ro.ubb.conference.core.service.SessionService;
import ro.ubb.conference.web.converter.ConferenceConverter;
import ro.ubb.conference.web.dto.ConferenceDto;
import ro.ubb.conference.web.dto.ConferencesDto;
import ro.ubb.conference.web.dto.EmptyJsonResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by user on 5/4/2017.
 */
@RestController
public class ConferenceController {
    private static final Logger log  = LoggerFactory.getLogger(ConferenceController.class);

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ConferenceConverter conferenceConverter;


    @RequestMapping(value = "/conferences", method = RequestMethod.GET)
    public ConferencesDto getConferences() {
        log.trace("getConferences");

        List<Conference> conferences = conferenceService.findAll();

        log.trace("getConferences: conferences={}", conferences);

        return new ConferencesDto(conferenceConverter.convertModelsToDtos(conferences));
    }

    @RequestMapping(value = "/conferences/{id}", method = RequestMethod.GET)
    public ConferenceDto getOneConference(@PathVariable final Long id) {
        log.trace("getOneConference");

        Conference conference = conferenceService.findOne(id);

        log.trace("getOneConference: conference={}", conference);

        return conferenceConverter.convertModelToDto(conference);
    }


    @RequestMapping(value = "/conferences/{conferenceId}", method = RequestMethod.PUT)
    public Map<String, ConferenceDto> updateConference(
            @PathVariable final Long conferenceId,
            @RequestBody final Map<String, ConferenceDto> conferenceDtoMap) {
        log.trace("updateConference: id={}, conferenceDtoMap={}", conferenceId, conferenceDtoMap);

        ConferenceDto conferenceDto = conferenceDtoMap.get("conference");
        Conference conference = conferenceService.updateConference(conferenceId, conferenceDto.getName(), conferenceDto.getEdition(), conferenceDto.getStartDate(), conferenceDto.getEndDate(), conferenceDto.getCallDate(), conferenceDto.getPapersDeadline(), sessionService.findAll(conferenceDto.getSessions()));

        Map<String, ConferenceDto> result = new HashMap<>();
        result.put("conference", conferenceConverter.convertModelToDto(conference));

        log.trace("updateConference: result={}", result);

        return result;
    }


    @RequestMapping(value = "/conferences", method = RequestMethod.POST)
    public Map<String, ConferenceDto> createConference(
            @RequestBody final Map<String, ConferenceDto> conferenceDtoMap) {
        log.trace("createConference: conferenceDtoMap={}", conferenceDtoMap);

        ConferenceDto conferenceDto = conferenceDtoMap.get("conference");
        Conference conference = conferenceService.createConference(
                conferenceDto.getName(), conferenceDto.getEdition(), conferenceDto.getStartDate(), conferenceDto.getEndDate(), conferenceDto.getCallDate(), conferenceDto.getPapersDeadline());

        Map<String, ConferenceDto> result = new HashMap<>();
        result.put("conference", conferenceConverter.convertModelToDto(conference));

        log.trace("updateConference: result={}", result);

        return result;
    }


    @RequestMapping(value = "conferences/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteConference(@PathVariable final Long id) {
        log.trace("deleteConference: id={}", id);

        conferenceService.deleteConference(id);

        log.trace("deleteConference - method end");

        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
