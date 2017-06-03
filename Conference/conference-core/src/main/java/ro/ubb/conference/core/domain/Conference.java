package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

/**
 * Created by user on 5/3/2017.
 */

@Entity
@Table(name = "Conference", uniqueConstraints = {
        @UniqueConstraint(columnNames = "Name")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conference that = (Conference) o;

        if (edition != that.edition) return false;
        if (!name.equals(that.name)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!callDate.equals(that.callDate)) return false;
        if (!papersDeadline.equals(that.papersDeadline)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + edition;
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + callDate.hashCode();
        result = 31 * result + papersDeadline.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "name='" + name + '\'' +
                ", edition=" + edition +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", callDate='" + callDate + '\'' +
                ", papersDeadline='" + papersDeadline + '\'' +
                '}';
    }
}
