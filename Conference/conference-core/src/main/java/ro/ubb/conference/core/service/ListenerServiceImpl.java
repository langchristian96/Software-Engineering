package ro.ubb.conference.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.domain.Session;
import ro.ubb.conference.core.domain.UserRole;
import ro.ubb.conference.core.repository.ListenerRepository;
import ro.ubb.conference.core.repository.SessionRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by langchristian96 on 5/17/2017.
 */



@Service
public class ListenerServiceImpl implements ListenerService {

    private static final Logger log = LoggerFactory.getLogger(ListenerServiceImpl.class);

    @Autowired
    private ListenerRepository listenerRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public List<Listener> findAll() {
        log.trace("findAll");

        List<Listener> persons = listenerRepository.findAll();

        log.trace("findAll: persons={}", persons);

        return persons;
    }

    @Override
    public Session findSession(Long sessionId){
        log.trace("findSession: SessionId={}",sessionId);

        Session session = (Session)sessionRepository.findOne(sessionId);

        log.trace("findSession: Session={}",session);
        return session;
    }


    @Override
    public Listener getUserByUserName(String userName) {

        return personRepository.getUserByUserName(userName);
    }


    @Override
    @Transactional
    public Listener updateListener(Long personId, String password, String name, String affiliation, String email, Set<Long> sessions) {
        log.trace("updatePerson: personId={}, password={}, name={}, affiliation={}, email={}",
                personId, password, name, affiliation, email);

        Listener person = (Listener) listenerRepository.findOne(personId);
        person.setPassword(password);
        person.setName(name);
        person.setAffiliation(affiliation);
        person.setEmail(email);
        person.setUserRole(UserRole.NORMAL);

        person.getSessions().stream()
                .map(d->d.getId())
                .forEach(i->
                {
                    if(sessions.contains(i)){
                        sessions.remove(i);
                    }
                });
        List<Session> sessionList = sessionRepository.findAll(sessions);
        sessionList.forEach(person::addSession);

        log.trace("updateListener: Listener={}", person);

        return person;
    }

    @Override
    public Listener createListener(String user, String password, String name, String affiliation, String email) {

        log.trace("user={}, password={}, name={}, affiliation={}, email={}",
                user, password, name, affiliation, email);
        log.trace("test");
        Listener person = new Listener();
        person.setUsern(user);
        person.setEmail(email);
        person.setUserRole(UserRole.NORMAL);
        person.setAffiliation(affiliation);
        person.setPassword(password);
        person.setName(name);

        person = (Listener) listenerRepository.save(person);

        log.trace("createListener: Listener={}", person);

        return person;
    }

    @Override
    public void deleteListener(Long personId) {
        log.trace("deleteListener: personId={}", personId);

        listenerRepository.delete(personId);

        log.trace("deleteListener - method end");
    }


}
