package ro.ubb.conference.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.domain.Session;
import ro.ubb.conference.core.domain.SessionListener;
import ro.ubb.conference.core.service.ListenerService;
import ro.ubb.conference.web.converter.SessionListenerConverter;
import ro.ubb.conference.web.dto.SessionListenerDto;
import ro.ubb.conference.web.dto.SessionListenersDto;

import java.util.Set;

/**
 * Created by CristianCosmin on 19.05.2017.
 */

@RestController
public class SessionListenerController {
    private static final Logger log = LoggerFactory.getLogger(AuthorPaperController.class);

    @Autowired
    private ListenerService listenerService;

    @Autowired
    private SessionListenerConverter sessionListenerConverter;

    @RequestMapping(value="sessionlistener/{sessionId}",method= RequestMethod.GET)
    public SessionListenersDto getSessionListeners(
            @PathVariable final Long sessionId
    ) {
        log.trace("getSessionListeners : sessionId={}",sessionId);

        Session session = listenerService.findSession(sessionId);
        Set<SessionListener> sessionListenerSet = session.getSessionListeners();
        Set<SessionListenerDto> sessionListenerDtos = sessionListenerConverter.convertModelsToDtos(sessionListenerSet);

        SessionListenersDto sessionListenersDto = new SessionListenersDto(sessionListenerDtos);

        log.trace("getSessionListeners: result={}",sessionListenersDto);
        return sessionListenersDto;
    }
}
