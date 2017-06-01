package ro.ubb.conference.core.service;

import ro.ubb.conference.core.domain.Person;

import java.util.List;

/**
 * Created by langchristian96 on 5/5/2017.
 */
public interface PersonService {
    List<Person> findAll();

    Person findOne(Long personId);

    Person updatePerson(Long personId, String password, String name, String affiliation, String email);

    Person createPerson(String user, String password, String name, String affiliation, String email);

    void deletePerson(Long personId);

}
