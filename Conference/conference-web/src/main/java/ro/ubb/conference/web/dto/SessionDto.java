package ro.ubb.conference.web.dto;

import lombok.*;
import ro.ubb.conference.core.domain.Conference;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Budu.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SessionDto extends BaseEntityDto {
    private String date;
    private Long sessionChairId;
    private Long conferenceId;
    private Set<Long> papers;
    private Set<Long> listeners;
//	private ArrayList<Long> listeners;
//    private ArrayList<Long> papers;

    @Override
    public String toString() {
        return "SessionDto{" +
                "date='" + date + '\'' +
                ", conferenceId='" + conferenceId + '\'' +
                ", sessionChairId=" + sessionChairId +
                "} " + super.toString();
    }
}
