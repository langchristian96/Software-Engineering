package ro.ubb.conference.web.dto;

import lombok.*;

/**
 * Created by CristianCosmin on 18.05.2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReviewerPaperDto {
    private Long reviewerId;
    private Long paperId;
    private String paperTitle;
}