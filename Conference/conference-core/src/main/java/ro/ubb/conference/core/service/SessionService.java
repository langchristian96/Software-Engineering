package ro.ubb.conference.core.service;
import org.springframework.stereotype.Service;
import ro.ubb.conference.core.domain.Conference;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Budu.
 */
@Service
public interface SessionService {
    List<Session> findAll();

    Set<Session> findAll(Set<Long> sessions);

    Session findOne(Long sessionId);

    Session updateSession(Long sessionId, String date, Long sessionChairId, Long conferenceId, Set<Paper> papers, Set<Long> listeners);

    Session createSession(String date,  Long sessionChairId, Long conferenceId);

    void deleteSession(Long sessionId);
}
