package ro.ubb.conference.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.repository.AuthorRepository;
import ro.ubb.conference.core.repository.ListenerRepository;

import java.util.List;

/**
 * Created by langchristian96 on 5/18/2017.
 */

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Autowired
    private AuthorRepository personRepository;

    @Override
    public List<Author> findAll() {
        log.trace("findAll");

        List<Author> persons = personRepository.findAll();

        log.trace("findAll: Authors={}", persons);

        return persons;
    }

    @Override
    @Transactional
    public Author updateAuthor(Long personId, String password, String name, String affiliation, String email) {
        log.trace("updateAuthor: personId={}, password={}, name={}, affiliation={}, email={}",
                personId, password, name, affiliation, email);

        Author person = (Author) personRepository.findOne(personId);
        person.setPassword(password);
        person.setName(name);
        person.setAffiliation(affiliation);
        person.setEmail(email);

        log.trace("updateListener: Listener={}", person);

        return person;
    }

    @Override
    public Author createAuthor(String user, String password, String name, String affiliation, String email) {
        log.trace("user={}, password={}, name={}, affiliation={}, email={}",
                user, password, name, affiliation, email);

        Author person = new Author();
        person.setUsern(user);
        person.setEmail(email);
        person.setAffiliation(affiliation);
        person.setPassword(password);
        person.setName(name);

        person = (Author) personRepository.save(person);

        log.trace("createAuthor: Author={}", person);

        return person;
    }

    @Override
    public void deleteAuthor(Long personId) {
        log.trace("deleteAuthor: personId={}", personId);

        personRepository.delete(personId);

        log.trace("deleteAuthor - method end");
    }


}