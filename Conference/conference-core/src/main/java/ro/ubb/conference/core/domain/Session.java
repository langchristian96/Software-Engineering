package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "conferenceId", nullable = false)
    private Long conferenceId;

    @Column(name = "session chair", nullable = false)
    private Person sessionChair;
	
	@Column(name = "session listeners", nullable = false)
    private ArrayList<Listener> listeners;
	
	@Column(name = "session papers", nullable = false)
    private ArrayList<Paper> papers;


    @Override
    public String toString() {
        return "Session{" +
                "date='" + date + '\'' +
                ", conferenceId='" + conferenceId + '\'' +
                ", sessionChair=" + sessionChair +
                "} " + super.toString();
    }
}
