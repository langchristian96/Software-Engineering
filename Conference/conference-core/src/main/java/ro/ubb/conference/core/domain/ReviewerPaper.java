package ro.ubb.conference.core.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by CristianCosmin on 18.05.2017.
 */

@Entity
@Table(name="ReviewerPaper")
@IdClass(ReviewerPaperPK.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ReviewerPaper implements Serializable {
    @Id
    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    @JoinColumn(name="ReviewerId")
    private Reviewer reviewer;


    @Id
    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="PaperId")
    private Paper reviewerPaper;
}