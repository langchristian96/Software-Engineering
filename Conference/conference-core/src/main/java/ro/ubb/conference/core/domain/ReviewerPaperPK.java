package ro.ubb.conference.core.domain;

import lombok.*;

import java.io.Serializable;

/**
 * Created by CristianCosmin on 18.05.2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ReviewerPaperPK implements Serializable {
    private Reviewer reviewer;
    private Paper reviewerPaper;
}
