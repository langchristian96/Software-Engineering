package ro.ubb.conference.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.Conference;
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Session;
import ro.ubb.conference.core.repository.ConferenceRepository;
import ro.ubb.conference.core.repository.ListenerRepository;
import ro.ubb.conference.core.repository.SessionRepository;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Budu.
 */

@Service
public class SessionServiceImpl implements SessionService {

    private static final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private ListenerRepository listenerRepository;

    @Override
    public List<Session> findAll() {
        log.trace("findAll");

        List<Session> sessions = sessionRepository.findAll();

        log.trace("findAll: sessions={}", sessions);

        return sessions;
    }

    @Override
    public Set<Session> findAll(Set<Long> sessions) {
        log.trace("findAll");

        Set<Session> sessionsList = (Set<Session>) sessionRepository.findAll(sessions);

        log.trace("findAll: sessionsList={}", sessionsList);

        return sessionsList;
    }

    @Override
    public Session findOne(Long sessionId) {
        log.trace("findOne");

        Session session = (Session) sessionRepository.findOne(sessionId);

        log.trace("findOne: session={}", session);

        return session;
    }

    @Override
    @Transactional
    public Session updateSession(Long sessionId, String date, Long sessionChairId, Long conferenceId, Set<Paper> papers, Set<Long> listeners) {
        log.trace("updateSession: sessionId={}, date={}, conferenceId={}, session chair id={}",
                sessionId, date, conferenceId, sessionChairId);

        Session session = (Session)sessionRepository.findOne(sessionId);
        session.setDate(date);
        session.setConference((Conference)conferenceRepository.findOne(conferenceId));
        session.setSessionChairId(sessionChairId);
        session.setPapers(papers);
        session.getListeners().stream()
                .map(d->d.getId())
                .forEach(i->
                {
                    if(listeners.contains(i)){
                        listeners.remove(i);
                    }
                });
        List<Listener> listenerList = listenerRepository.findAll(listeners);
        listenerList.forEach(session::addListener);



        log.trace("updateSession: session={}", session);

        return session;
    }

    @Override
    public Session createSession(String date, Long sessionChairId, Long conferenceId/*, ArrayList<Long> listeners, ArrayList<Long> papers*/ ) {
        log.trace("createSession: date={}, conferenceId={}, session chair id={}",
                date, conferenceId, sessionChairId);

        Session session = new Session(date, sessionChairId, (Conference) conferenceRepository.findOne(conferenceId), new HashSet<>(), new HashSet<>());
        session = (Session) sessionRepository.save(session);

        log.trace("createSession: session={}", session);

        return session;
    }

    @Override
    public void deleteSession(Long sessionId) {
        log.trace("deleteSession: sessionId={}", sessionId);

        sessionRepository.delete(sessionId);

        log.trace("deleteSession - method end");
    }


}
