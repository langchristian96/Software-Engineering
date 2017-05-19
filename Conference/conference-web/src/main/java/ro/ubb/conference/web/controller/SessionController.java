package ro.ubb.conference.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.conference.core.domain.Session;
import ro.ubb.conference.core.service.PaperService;
import ro.ubb.conference.core.service.SessionService;
import ro.ubb.conference.web.converter.SessionConverter;
import ro.ubb.conference.web.dto.EmptyJsonResponse;
import ro.ubb.conference.web.dto.SessionDto;
import ro.ubb.conference.web.dto.SessionsDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Budu.
 */

@RestController
public class SessionController {

    private static final Logger log = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private SessionConverter sessionConverter;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public SessionsDto getSessions() {
        log.trace("getSessions");

        List<Session> sessions = sessionService.findAll();

        log.trace("getSessions: sessions={}", sessions);

        return new SessionsDto(sessionConverter.convertModelsToDtos(sessions));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/sessions/{id}", method = RequestMethod.GET)
    public SessionDto getOneSession(@PathVariable final Long id) {
        log.trace("getOneSession");

        Session session = sessionService.findOne(id);

        log.trace("getOneSession: session={}", session);

        return sessionConverter.convertModelToDto(session);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/sessions/{sessionId}", method = RequestMethod.PUT)
    public Map<String, SessionDto> updateSession(
            @PathVariable final Long sessionId,
            @RequestBody final Map<String, SessionDto> sessionDtoMap) {
        log.trace("updateSession: sessionId={}, sessionDtoMap={}", sessionId, sessionDtoMap);

        SessionDto sessionDto = sessionDtoMap.get("session");
        Session session = sessionService.updateSession(sessionId, sessionDto.getDate(),
                 sessionDto.getSessionChairId(), sessionDto.getConferenceId(), paperService.findAll(sessionDto.getPapers()), sessionDto.getListeners());

        Map<String, SessionDto> result = new HashMap<>();
        result.put("session", sessionConverter.convertModelToDto(session));

        log.trace("updateSession: result={}", result);

        return result;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/sessions", method = RequestMethod.POST)
    public Map<String, SessionDto> createSession(
            @RequestBody final Map<String, SessionDto> sessionDtoMap) {
        log.trace("createSession: sessionDtoMap={}", sessionDtoMap);

        SessionDto sessionDto = sessionDtoMap.get("session");
        Session session = sessionService.createSession(
                sessionDto.getDate(), sessionDto.getSessionChairId(), sessionDto.getConferenceId());

        Map<String, SessionDto> result = new HashMap<>();
        result.put("session", sessionConverter.convertModelToDto(session));

        log.trace("updateSession: result={}", result);

        return result;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "sessions/{sessionId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteSession(@PathVariable final Long sessionId) {
        log.trace("deleteSession: sessionId={}", sessionId);

        sessionService.deleteSession(sessionId);

        log.trace("deleteSession - method end");

        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
