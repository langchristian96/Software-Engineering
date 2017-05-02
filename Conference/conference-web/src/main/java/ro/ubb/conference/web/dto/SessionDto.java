package ro.ubb.conference.web.dto;

import lombok.*;

import java.util.ArrayList;

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
    private Long sessionChairId;
	private ArrayList<Long> listeners;
    private ArrayList<Long> papers;

    @Override
    public String toString() {
        return "SessionDto{" +
                "date='" + date + '\'' +
                ", conferenceId='" + conferenceId + '\'' +
                ", sessionChairId=" + sessionChair +
                "} " + super.toString();
    }
}
