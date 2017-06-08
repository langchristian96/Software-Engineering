package ro.ubb.conference.core.service;

import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.core.domain.Session;

import java.util.List;
import java.util.Set;

/**
 * Created by langchristian96 on 5/17/2017.
 */
public interface ListenerService {
    List<Listener> findAll();



    Listener getUserByUserName(String userName);

    Session findSession(Long sessionId);

    Listener updateListener(Long personId, String password, String name, String affiliation, String email, Set<Long> sessions);

    Listener createListener(String user, String password, String name, String affiliation, String email);

    void deleteListener(Long personId);

}