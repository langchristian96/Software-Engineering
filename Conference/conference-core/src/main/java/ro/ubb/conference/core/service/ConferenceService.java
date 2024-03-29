package ro.ubb.conference.core.service;

import org.springframework.stereotype.Service;
import ro.ubb.conference.core.domain.Conference;
import ro.ubb.conference.core.domain.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 5/4/2017.
 */
@Service
public interface ConferenceService {
    List<Conference> findAll();

    Conference findOne(Long id);

//    Conference updateConference(Long id, String name, int edition, String startDate, String endDate, String callDate, String papersDeadline, ArrayList<String> commitee, ArrayList<String> sections);

//    Conference createConference(String name, int edition, String startDate, String endDate, String callDate, String papersDeadline, ArrayList<String> commitee, ArrayList<String> sections);

    Conference updateConference(Long id, String name, int edition, String startDate, String endDate, String callDate, String papersDeadline, Set<Session> sessions);

    Conference createConference(String name, int edition, String startDate, String endDate, String callDate, String papersDeadline);

    void deleteConference(Long id);
}
