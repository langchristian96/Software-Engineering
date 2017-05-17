package ro.ubb.conference.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Reviewer;
import ro.ubb.conference.core.repository.AuthorRepository;
import ro.ubb.conference.core.repository.ReviewerRepository;

import java.util.List;

/**
 * Created by langchristian96 on 5/18/2017.
 */




@Service
public class ReviewerServiceImpl implements ReviewerService {

    private static final Logger log = LoggerFactory.getLogger(ReviewerServiceImpl.class);

    @Autowired
    private ReviewerRepository personRepository;

    @Override
    public List<Reviewer> findAll() {
        log.trace("findAll");

        List<Reviewer> persons = personRepository.findAll();

        log.trace("findAll: Authors={}", persons);

        return persons;
    }

    @Override
    @Transactional
    public Reviewer updateReviewer(Long personId, String password, String name, String affiliation, String email) {
        log.trace("updateAuthor: personId={}, password={}, name={}, affiliation={}, email={}",
                personId, password, name, affiliation, email);

        Reviewer person = (Reviewer) personRepository.findOne(personId);
        person.setPassword(password);
        person.setName(name);
        person.setAffiliation(affiliation);
        person.setEmail(email);

        log.trace("updateReviewer: Reviewer={}", person);

        return person;
    }

    @Override
    public Reviewer createReviewer(String user, String password, String name, String affiliation, String email) {
        log.trace("user={}, password={}, name={}, affiliation={}, email={}",
                user, password, name, affiliation, email);

        Reviewer person = new Reviewer();
        person.setUsern(user);
        person.setEmail(email);
        person.setAffiliation(affiliation);
        person.setPassword(password);
        person.setName(name);

        person = (Reviewer) personRepository.save(person);

        log.trace("createReviewer: Reviewer={}", person);

        return person;
    }

    @Override
    public void deleteReviewer(Long personId) {
        log.trace("deleteReviewer: personId={}", personId);

        personRepository.delete(personId);

        log.trace("deleteReviewer - method end");
    }


}