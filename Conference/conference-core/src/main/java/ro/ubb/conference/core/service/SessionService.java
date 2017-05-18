package ro.ubb.conference.core.service;
import org.springframework.stereotype.Service;
import ro.ubb.conference.core.domain.Conference;
import ro.ubb.conference.core.domain.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Budu.
 */
@Service
public interface SessionService {
    List<Session> findAll();

//    Session updateSession(Long sessionId, String date, Long conferenceId, Long sessionChairId, ArrayList<Long> listeners, ArrayList<Long> papers);

//    Session createSession(String date, Long conferenceId, Long sessionChairId, ArrayList<Long> listeners, ArrayList<Long> papers);

    Session updateSession(Long sessionId, String date, Long sessionChairId, Long conferenceId);

    Session createSession(String date,  Long sessionChairId, Long conferenceId);

    void deleteSession(Long sessionId);
}
