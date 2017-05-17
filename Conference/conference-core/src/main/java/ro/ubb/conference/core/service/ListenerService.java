package ro.ubb.conference.core.service;

import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.domain.Person;

import java.util.List;

/**
 * Created by langchristian96 on 5/17/2017.
 */
public interface ListenerService {
    List<Listener> findAll();

    Listener updateListener(Long personId, String password, String name, String affiliation, String email);

    Listener createListener(String user, String password, String name, String affiliation, String email);

    void deleteListener(Long personId);

}