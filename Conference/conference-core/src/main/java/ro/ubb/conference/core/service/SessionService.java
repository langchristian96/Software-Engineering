package ro.ubb.conference.core.service;
import ro.ubb.conference.core.domain.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Budu.
 */
public interface SessionService {
    List<Session> findAll();

    Session updateSession(Long sessionId, String date, Long conferenceId, Long sessionChairId, ArrayList<Long> listeners, ArrayList<Long> papers);

    Session createSession(String date, Long conferenceId, Long sessionChairId, ArrayList<Long> listeners, ArrayList<Long> papers);

    void deleteSession(Long sessionId);
}
