package ro.ubb.conference.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.Session;
import ro.ubb.conference.core.repository.SessionRepository;

import java.util.List;

/**
 * Created by Budu.
 */

@Service
public class SessionServiceImpl implements SessionService {

    //private static final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);

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
    public Session updateSession(Long sessionId, String date, Long conferenceId, Person sessionChair, ArrayList<Listener> listeners, ArrayList<Paper> papers ) {
        log.trace("updateSession: sessionId={}, date={}, conferenceId={}, session chair={}",
                sessionId, date, conferenceId, sessionChair);

        Session session = sessionRepository.findOne(sessionId);
        session.setDate(date);
        session.setConferenceId(conference);
        session.setSessionChair(sessionChair);
        session.setListeners(listeners);
        session.setPapers(papers);

        log.trace("updateSession: session={}", session);

        return session;
    }

    @Override
    public Session createSession(String date, Long conferenceId, Person sessionChair, ArrayList<Listener> listeners, ArrayList<Paper> papers ) {
        log.trace("createSession: date={}, conferenceId={}, session chair={}",
                date, conferenceId, sessionChair);

        Session session = new Session(date, conferenceId, sessionChair, listeners, papers);
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
