package ro.ubb.conference.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
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
    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<ReviewerPaper> reviewerPapers = new HashSet<>();

    public Set<Paper> getPapers(){
        return Collections.unmodifiableSet(this.reviewerPapers.stream().map(ReviewerPaper::getReviewerPaper).collect(Collectors.toSet()));
    }

    public void addPaper(Paper paper){
        boolean isAdded = false;
        for(ReviewerPaper reviewerPaper: reviewerPapers){
            if(reviewerPaper.getReviewerPaper().getId().equals(paper.getId())){
                isAdded = true;
                break;
            }
        }
        if(!isAdded) {
            ReviewerPaper reviewerPaper = new ReviewerPaper();
            reviewerPaper.setReviewerPaper(paper);
            reviewerPaper.setReviewer(this);
            reviewerPapers.add(reviewerPaper);
        }
    }

    public void removePaper(Paper paper){
        ReviewerPaper reviewerPaper = new ReviewerPaper();
        reviewerPaper.setReviewerPaper(paper);
        reviewerPaper.setReviewer(this);
        reviewerPapers.remove(reviewerPaper);
    }

    public void updatePaper(Paper paper, int grade){
        ReviewerPaper reviewerPaper = new ReviewerPaper();
        reviewerPaper.setReviewerPaper(paper);
        reviewerPaper.setReviewer(this);
        reviewerPaper.setGrade(grade);
        for(ReviewerPaper reviewerPaper1: reviewerPapers){
            if(reviewerPaper1.getReviewerPaper().getId().equals(paper.getId())){
                reviewerPapers.remove(reviewerPaper1);
                break;
            }
        }
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