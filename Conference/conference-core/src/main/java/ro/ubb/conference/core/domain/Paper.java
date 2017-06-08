package ro.ubb.conference.core.domain;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by anca.
 */

@Entity
@Table(name = "Paper")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Paper extends BaseEntity<Long> {
    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "AbstractText", nullable = true)
    private String abstractText;

	@Column(name = "ContentPath", nullable = false)
    private String contentPath;

	@Column(name = "Keywords", nullable = false)
    private String keywords;

	@Column(name = "Topics", nullable = false)
    private String topics;

    @OneToMany(mappedBy = "authorPaper",cascade = CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
    private Set<AuthorPaper> authors=new HashSet<>();

    @OneToMany(mappedBy = "reviewerPaper",cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ReviewerPaper> reviewers=new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SessionId")
    private Session paperSession;

    public Session getSession() {
        return this.paperSession;
    }

    public void setSession(Session paperSession) {
        this.paperSession = paperSession;
    }

    public Set<Reviewer> getReviewers(){
//        return Collections.unmodifiableSet(reviewers.stream().map(ReviewerPaper::getReviewer).collect(Collectors.toSet()));
        return reviewers.stream().map(ReviewerPaper::getReviewer).collect(Collectors.toSet());
    }

    public void addReviewer(Reviewer reviewer){
        ReviewerPaper reviewerPaper = new ReviewerPaper();
        reviewerPaper.setReviewerPaper(this);
        reviewerPaper.setReviewer(reviewer);
        reviewers.add(reviewerPaper);
    }

    public void updateReviewer(Reviewer reviewer, int grade){
        ReviewerPaper reviewerPaper = new ReviewerPaper();
        reviewerPaper.setReviewerPaper(this);
        reviewerPaper.setReviewer(reviewer);
        reviewerPaper.setGrade(grade);
        for(ReviewerPaper reviewerPaper1: reviewers){
            if(reviewerPaper1.getReviewer().getId().equals(reviewer.getId())){
                reviewers.remove(reviewerPaper1);
                break;
            }
        }
        reviewers.add(reviewerPaper);
    }

    public void addReviewers(Set<Reviewer> reviewers){
        reviewers.forEach(this::addReviewer);
    }

    public Set<Author> getAuthors(){
        Set<Author> a = authors.stream().map(AuthorPaper::getAuthor).collect(Collectors.toSet());
        return authors.stream().map(AuthorPaper::getAuthor).collect(Collectors.toSet());
    }

    public void setAuthor(Author author){
        for (AuthorPaper ap: authors) {
            if(Objects.equals(ap.getAuthor().getId(), author.getId())){
                authors.remove(ap);
                this.addAuthor(author);
                break;
            }
        }
    }

    public void addAuthor(Author author){
        boolean isAdded = false;
        for(AuthorPaper authorPaper: authors){
            if(authorPaper.getAuthor().getId().equals(author.getId())){
                isAdded = true;
                break;
            }
        }
        if(!isAdded) {
            AuthorPaper authorPaper=new AuthorPaper();
            authorPaper.setAuthorPaper(this);
            authorPaper.setAuthor(author);
            authors.add(authorPaper);
        }
    }

    public void addAuthors(Set<Author> authors){
        authors.forEach(this::addAuthor);
    }

    public void setAuthors(Set<Author> authors) { authors.forEach(this::setAuthor);}

    public Paper(String title, String abstractText, String contentPath, String keywords, String topics){
        this.title = title;
        this.abstractText = abstractText;
        this.contentPath = contentPath;
        this.keywords = keywords;
        this.topics = topics;
        this.authors = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Paper{" +
                "title='" + title + '\'' +
                ", contentPath=" + contentPath +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Paper paper = (Paper) o;

        if (title != null ? !title.equals(paper.title) : paper.title != null) return false;
        return (contentPath != null ? !contentPath.equals(paper.contentPath) : paper.contentPath != null);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (contentPath != null ? contentPath.hashCode() : 0);
        return result;
    }
}
