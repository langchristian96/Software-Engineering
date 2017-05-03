package ro.ubb.conference.web.dto;

import lombok.*;

import java.util.ArrayList;

/**
 * Created by anca.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaperDto extends BaseEntityDto {
    private String title;
    private Long personId;
    private String content;

    @Override
    public String toString() {
        return "PapaerDto{" +
                "title='" + title + '\'' +
                ", author='" + personId + '\'' +
                ", content=" + content +
                "} " + super.toString();
    }
}
