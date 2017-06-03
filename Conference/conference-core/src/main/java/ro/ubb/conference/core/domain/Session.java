package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Budu.
 */

@Entity
@Table(name = "Session")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Session extends BaseEntity<Long> {

    private static final int SERIAL_NUMBER_LENGTH = 16;

    @Column(name = "Date", nullable = false)
    private String date;

//    @Column(name = "conferenceId", nullable = false)
//    private Long conferenceId;

    @Column(name = "SessionChair", nullable = false)
    private Long sessionChairId;

    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    @JoinColumn(name="ConferenceId", nullable = false)
    private Conference conference;

    @OneToMany(mappedBy = "paperSession", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Paper> papers = new HashSet<>();

    public Conference getConference() {
        return this.conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public Set<Paper> getPapers(){
        return this.papers;
    }

    public void setPapers(){
        this.papers = papers;
    }

    @OneToMany(mappedBy = "listenerSession", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SessionListener> sessionListeners = new HashSet<>();

    public Set<Listener> getListeners(){
        return Collections.unmodifiableSet(this.sessionListeners.stream().map(SessionListener::getListener).collect(Collectors.toSet()));
    }

    public void addListener(Listener listener){
        SessionListener sessionListener = new SessionListener();
        sessionListener.setListenerSession(this);
        sessionListener.setListener(listener);
        sessionListeners.add(sessionListener);
    }

    public void addListeners(Set<Listener> listeners){
        listeners.forEach(this::addListener);
    }

    @Override
    public String toString() {
        return "Session{" +
                "date='" + date + '\'' +
                ", sessionChairId=" + sessionChairId +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        if (date != null ? !date.equals(session.date) : session.date != null) return false;
        return sessionChairId != null ? sessionChairId.equals(session.sessionChairId) : session.sessionChairId == null;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (sessionChairId != null ? sessionChairId.hashCode() : 0);
        return result;
    }
}
