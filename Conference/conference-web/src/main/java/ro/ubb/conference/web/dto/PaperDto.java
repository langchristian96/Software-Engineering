package ro.ubb.conference.web.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by anca.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PaperDto extends BaseEntityDto {
    private String title;
    private String content;
    private Set<Long> authors;
    private Set<Long> reviewers;
    private Long sessionId;

    @Override
    public String toString() {
        return "PaperDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", authors=" + authors +
                ", reviewers=" + reviewers +
                ", sessionIs=" + sessionId +
                '}';
    }
}
