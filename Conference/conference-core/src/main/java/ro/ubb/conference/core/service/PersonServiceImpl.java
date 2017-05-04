package ro.ubb.conference.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.core.repository.PersonRepository;

import java.util.List;

/**
 * Created by langchristian96 on 5/5/2017.
 */



@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> findAll() {
        log.trace("findAll");

        List<Person> persons = personRepository.findAll();

        log.trace("findAll: persons={}", persons);

        return persons;
    }

    @Override
    @Transactional
    public Person updatePerson(Long personId, String password, String name, String affiliation, String email) {
        log.trace("updatePerson: personId={}, password={}, name={}, affiliation={}, email={}",
                personId, password, name, affiliation, email);

        Person person = (Person) personRepository.findOne(personId);
        person.setPassword(password);
        person.setName(name);
        person.setAffiliation(affiliation);
        person.setEmail(email);

        log.trace("updatePerson: person={}", person);

        return person;
    }

    @Override
    public Person createPerson(String user, String password, String name, String affiliation, String email) {
        log.trace("user={}, password={}, name={}, affiliation={}, email={}",
                user, password, name, affiliation, email);

        Person person = new Person(user, password, name, affiliation, email);
        person = (Person) personRepository.save(person);

        log.trace("createPerson: person={}", person);

        return person;
    }

    @Override
    public void deletePerson(Long personId) {
        log.trace("deletePerson: personId={}", personId);

        personRepository.delete(personId);

        log.trace("deletePerson - method end");
    }


}
