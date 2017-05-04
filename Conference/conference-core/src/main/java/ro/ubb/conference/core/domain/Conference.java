package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/3/2017.
 */

@Entity
@Table(name = "conference")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Conference extends BaseEntity<Long> {
    @Column(name = "name", nullable = false)
    private String name;

    // Edition number
    @Column(name = "edition", nullable = false)
    private int edition;

    // Start date of the conference
    @Column(name = "start_date", nullable = false)
    private String startDate;

    // End date of the conference
    @Column(name = "end_date", nullable = false)
    private String endDate;

    // Call for papers date
    @Column(name = "call_date", nullable = false)
    private String callDate;

    // Papers submission deadline
    @Column(name = "papers_deadline", nullable = false)
    private String papersDeadline;

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
                /*", committee=" + committee +
                ", sections=" + sections +*/
                '}' + super.toString();
    }
}
