package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

/**
 * Created by user on 5/3/2017.
 */

@Entity
@Table(name = "conference", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Conference extends BaseEntity<Long> {
    @Column(name = "Name", nullable = false)
    private String name;

    // Edition number
    @Column(name = "Edition", nullable = false)
    private int edition;

    // Start date of the conference
    @Column(name = "StartDate", nullable = false)
    private String startDate;

    // End date of the conference
    @Column(name = "EndDate", nullable = false)
    private String endDate;

    // Call for papers date
    @Column(name = "CallDate", nullable = false)
    private String callDate;

    // Papers submission deadline
    @Column(name = "PapersDeadline", nullable = false)
    private String papersDeadline;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Session> sessions = new HashSet<>();

    public Set<Session> getSessions() {
        return this.sessions;
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

//    // Committee - list of names of people being part of the commitee
//    @Column(name = "committee", nullable = false)
//    private ArrayList<String> committee;

//    // Conference program
//    @Column(name = "program")
//    private String program;

//    Conference sections
//    @Column(name = "sections", nullable = false)
//    private ArrayList<String> sections;

    @Override
    public String toString() {
        return "Conference{" +
                "name='" + name + '\'' +
                ", edition=" + edition +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", callDate='" + callDate + '\'' +
                ", papersDeadline='" + papersDeadline + '\'' +
                ", sessions=" + sessions +
                '}';
    }
}
