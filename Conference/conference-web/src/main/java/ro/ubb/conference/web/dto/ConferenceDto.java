package ro.ubb.conference.web.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by user on 5/4/2017.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ConferenceDto extends BaseEntityDto {
    private String name;
    private int edition;
    private String startDate;
    private String endDate;
    private String callDate;
    private String papersDeadline;
    private Set<Long> sessions;
//    private ArrayList<String> committee;
//    private ArrayList<String> sections;

    @Override
    public String toString() {
        return "ConferenceDto{" +
                "name='" + name + '\'' +
                ", edition=" + edition +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", callDate='" + callDate + '\'' +
                ", papersDeadline='" + papersDeadline + '\'' +
                '}' + super.toString();
    }
}
