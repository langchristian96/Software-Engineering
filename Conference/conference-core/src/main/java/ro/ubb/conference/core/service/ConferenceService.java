package ro.ubb.conference.core.service;

import ro.ubb.conference.core.domain.Conference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/4/2017.
 */
public interface ConferenceService {
    List<Conference> findAll();

    Conference updateConference(Long id, String name, int edition, String startDate, String endDate, String callDate, String papersDeadline, ArrayList<String> commitee, ArrayList<String> sections);

    Conference createConference(String name, int edition, String startDate, String endDate, String callDate, String papersDeadline, ArrayList<String> commitee, ArrayList<String> sections);

    void deleteConference(Long id);
}
