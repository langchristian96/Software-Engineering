package ro.ubb.conference.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by langchristian96 on 5/18/2017.
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="Reviewer")
public class Reviewer extends Author {
    @OneToMany(mappedBy = "reviewer", orphanRemoval = true, fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<ReviewerPaper> reviewerPapers = new HashSet<>();

    public Set<Paper> getPapers(){
        return Collections.unmodifiableSet(this.reviewerPapers.stream().map(ReviewerPaper::getReviewerPaper).collect(Collectors.toSet()));
    }

    public void addPaper(Paper paper){
        ReviewerPaper reviewerPaper=new ReviewerPaper();
        reviewerPaper.setReviewerPaper(paper);
        reviewerPaper.setReviewer(this);
        reviewerPapers.add(reviewerPaper);
    }

    public void addPapers(Set<Paper> papers){
        papers.forEach(this::addPaper);
    }

    @Override
    public String toString() {
        return "Reviewer{}"+super.toString();
    }
}