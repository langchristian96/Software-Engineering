package ro.ubb.conference.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.Session;
import ro.ubb.conference.core.repository.SessionRepository;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Budu.
 */

@Service
public class SessionServiceImpl implements SessionService {

    private static final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public List<Session> findAll() {
        log.trace("findAll");

        List<Session> sessions = sessionRepository.findAll();

        log.trace("findAll: sessions={}", sessions);

        return sessions;
    }

    @Override
    @Transactional
    public Session updateSession(Long sessionId, String date, Long conferenceId, Long sessionChairId, ArrayList<Long> listeners, ArrayList<Long> papers ) {
        log.trace("updateSession: sessionId={}, date={}, conferenceId={}, session chair id={}",
                sessionId, date, conferenceId, sessionChairId);

        Session session = sessionRepository.findOne(sessionId);
        session.setDate(date);
        session.setConferenceId(conferenceId);
        session.setSessionChairId(sessionChairId);
        session.setListeners(listeners);
        session.setPapers(papers);

        log.trace("updateSession: session={}", session);

        return session;
    }

    @Override
    public Session createSession(String date, Long conferenceId, Long sessionChairId, ArrayList<Long> listeners, ArrayList<Long> papers ) {
        log.trace("createSession: date={}, conferenceId={}, session chair id={}",
                date, conferenceId, sessionChairId);

        Session session = new Session(date, conferenceId, sessionChairId, listeners, papers);
        session = sessionRepository.save(session);

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
