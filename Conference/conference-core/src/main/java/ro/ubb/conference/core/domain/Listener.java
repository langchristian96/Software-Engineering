package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by langchristian96 on 5/17/2017.
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="Listener")
@Inheritance(strategy = InheritanceType.JOINED)
public class Listener extends Person {
    @OneToMany(mappedBy = "listener", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SessionListener> listenerSessions = new HashSet<>();

    public Set<Session> getSessions(){
        return Collections.unmodifiableSet(this.listenerSessions.stream().map(SessionListener::getListenerSession).collect(Collectors.toSet()));
    }

    public void addSession(Session session){
        SessionListener sessionListener = new SessionListener();
        sessionListener.setListenerSession(session);
        sessionListener.setListener(this);
        listenerSessions.add(sessionListener);
    }

    public void addSessions(Set<Session> sessions){
        sessions.forEach(this::addSession);
    }

    public Listener(String usern, String password, String name, String affiliation, String email){
        super(usern,password,name,affiliation,email);
    }

    @Override
    public String toString() {
        return "Listener{}"+super.toString();
    }
}
