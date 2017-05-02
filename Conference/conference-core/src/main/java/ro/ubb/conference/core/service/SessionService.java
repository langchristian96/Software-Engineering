package ro.ubb.conference.core.service;
import ro.ubb.conference.core.domain.Session;

import java.util.List;

/**
 * Created by Budu.
 */
public interface SessionService {
    List<Session> findAll();

    Session updateSession(Long sessionId, String date, Long conferenceId, Person sessionChair, ArrayList<Listener> listeners, ArrayList<Paper> papers);

    Session createSession(String date, Long conferenceId, Person sessionChair, ArrayList<Listener> listeners, ArrayList<Paper> papers);

    void deleteSession(Long sessionId);
}
