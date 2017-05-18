package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created by Budu.
 */

@Entity
@Table(name = "session")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Session extends BaseEntity<Long> {

    private static final int SERIAL_NUMBER_LENGTH = 16;

    @Column(name = "Date", nullable = false)
    private String date;

//    @Column(name = "conferenceId", nullable = false)
//    private Long conferenceId;

    @Column(name = "SessionChair", nullable = false)
    private Long sessionChairId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ConferenceId", nullable = false)
    private Conference conference;

    public Conference getConference() {
        return this.conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }
	
//	@Column(name = "session listeners", nullable = false)
//    private ArrayList<Long> listeners;
//
//	@Column(name = "session papers", nullable = false)
//    private ArrayList<Long> papers;


    @Override
    public String toString() {
        return "Session{" +
                "date='" + date + '\'' +
                ", sessionChairId=" + sessionChairId +
                ", conference=" + conference +
                '}';
    }
}
