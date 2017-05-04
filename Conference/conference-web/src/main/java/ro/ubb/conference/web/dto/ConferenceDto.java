package ro.ubb.conference.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Created by user on 5/4/2017.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConferenceDto extends BaseEntityDto {
    private String name;
    private int edition;
    private String startDate;
    private String endDate;
    private String callDate;
    private String papersDeadline;
    private ArrayList<String> committee;
    private ArrayList<String> sections;

    @Override
    public String toString() {
        return "ConferenceDto{" +
                "name='" + name + '\'' +
                ", edition=" + edition +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", callDate='" + callDate + '\'' +
                ", papersDeadline='" + papersDeadline + '\'' +
                ", committee=" + committee +
                ", sections=" + sections +
                '}' + super.toString();
    }
}
