package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
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

    //title of the paper
    @Column(name = "Title", nullable = false)
    private String title;

    //content of the paper
	@Column(name = "Content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "authorPaper", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AuthorPaper> authors=new HashSet<>();

    @OneToMany(mappedBy = "reviewerPaper", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ReviewerPaper> reviewers=new HashSet<>();

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "SessionId", nullable = false)
    private Session paperSession;

    public Session getSession() {
        return this.paperSession;
    }

    public void setSession(Session paperSession) {
        this.paperSession = paperSession;
    }

    public Set<Reviewer> getReviewers(){
        return Collections.unmodifiableSet(reviewers.stream().map(ReviewerPaper::getReviewer).collect(Collectors.toSet()));
    }

    public void addReviewer(Reviewer reviewer){
        ReviewerPaper reviewerPaper = new ReviewerPaper();
        reviewerPaper.setReviewerPaper(this);
        reviewerPaper.setReviewer(reviewer);
        reviewers.add(reviewerPaper);
    }

    public void addReviewers(Set<Reviewer> reviewers){
        reviewers.forEach(this::addReviewer);
    }

    public Set<Author> getAuthors(){
        return Collections.unmodifiableSet(authors.stream().map(AuthorPaper::getAuthor).collect(Collectors.toSet()));
    }

    public void addAuthor(Author author){
        AuthorPaper authorPaper=new AuthorPaper();
        authorPaper.setAuthorPaper(this);
        authorPaper.setAuthor(author);
        authors.add(authorPaper);
    }

    public void addAuthors(Set<Author> authors){
        authors.forEach(this::addAuthor);
    }

    public Paper(String title, String content){
        this.title=title;
        this.content=content;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "title='" + title + '\'' +
                ", content=" + content +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Paper paper = (Paper) o;

        if (title != null ? !title.equals(paper.title) : paper.title != null) return false;
        return (content != null ? !content.equals(paper.content) : paper.content != null);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
