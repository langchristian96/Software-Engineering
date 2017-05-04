package ro.ubb.conference.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.Conference;
import ro.ubb.conference.core.repository.ConferenceRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/4/2017.
 */
public class ConferenceServiceImpl implements ConferenceService {

    private static final Logger log = LoggerFactory.getLogger(ConferenceServiceImpl.class);

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Override
    public List<Conference> findAll() {
        log.trace("findAll");

        List<Conference> conferences = conferenceRepository.findAll();

        log.trace("findAll: conference={}", conferences);

        return conferences;
    }

    @Override
    @Transactional
    public Conference updateConference(Long id, String name, int edition, String startDate, String endDate, String callDate, String papersDeadline, ArrayList<String> commitee, ArrayList<String> sections) {
        log.trace("updateConference: id={}, name={}, edition={}, startDate={}, endDate={}, callDate={}, papersDeadline={}, committee={}, sections={}", id, name, edition, startDate, endDate, callDate, papersDeadline, commitee, sections);

        Conference conference = conferenceRepository.findOne(id);

        conference.setName(name);
        conference.setEdition(edition);
        conference.setStartDate(startDate);
        conference.setEndDate(endDate);
        conference.setCallDate(callDate);
        conference.setPapersDeadline(papersDeadline);
        conference.setCommittee(commitee);
        conference.setSections(sections);

        log.trace("updateConference: conference={}", conference);

        return conference;
    }

    @Override
    public Conference createConference(String name, int edition, String startDate, String endDate, String callDate, String papersDeadline, ArrayList<String> commitee, ArrayList<String> sections) {
        log.trace("createConference: name={}, edition={}, startDate={}, endDate={}, callDate={}, papersDeadline={}, committee={}, sections={}");

        Conference conference = new Conference(name, edition, startDate, endDate, callDate, papersDeadline, commitee, sections);
        conference = conferenceRepository.save(conference);

        log.trace("createConference: conferece={}", conference);

        return conference;
    }

    @Override
    public void deleteConference(Long id) {
        log.trace("deleteConference: id={}", id);

        conferenceRepository.delete(id);

        log.trace("deleteConference - method end");
    }
}
