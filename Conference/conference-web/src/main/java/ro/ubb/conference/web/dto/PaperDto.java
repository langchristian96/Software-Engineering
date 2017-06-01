package ro.ubb.conference.web.dto;

import lombok.*;

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
    private String abstractText;
    private String contentPath;
    private String keywords;
    private String topics;
    private Set<String> authorsUsername;
    private Set<Long> reviewers;
    private Long sessionId;

    @Override
    public String toString() {
        return "PaperDto{" +
                "title='" + title + '\'' +
                ", abstractText='" + abstractText + '\'' +
                ", contentPath='" + contentPath + '\'' +
                ", keywords='" + keywords + '\'' +
                ", topics='" + topics + '\'' +
                ", authorsUsername=" + authorsUsername +
                ", reviewers=" + reviewers +
                ", sessionId=" + sessionId +
                '}';
    }
}
