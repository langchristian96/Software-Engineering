package ro.ubb.conference.web.dto;

import lombok.*;

/**
 * Created by Budu.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SessionDto extends BaseEntityDto {
    private String date;
    private Long conferenceId;
    private Person sessionChair;
	private ArrayList<Listener> listeners;
    private ArrayList<Paper> papers;

    @Override
    public String toString() {
        return "SessionDto{" +
                "date='" + date + '\'' +
                ", conferenceId='" + conferenceId + '\'' +
                ", sessionChair=" + sessionChair +
                "} " + super.toString();
    }
}
