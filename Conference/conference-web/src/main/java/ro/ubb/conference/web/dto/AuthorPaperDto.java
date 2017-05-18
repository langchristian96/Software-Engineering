package ro.ubb.conference.web.dto;

import lombok.*;

/**
 * Created by langchristian96 on 5/18/2017.
 */




@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthorPaperDto {
    private Long authorId;
    private Long paperId;
    private String paperTitle;


}