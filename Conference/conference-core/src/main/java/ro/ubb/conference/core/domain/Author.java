package ro.ubb.conference.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
@Table(name="Author")
@Inheritance(strategy = InheritanceType.JOINED)
public class Author extends Person {
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AuthorPaper> authorPapers = new HashSet<>();

    public Set<Paper> getPapers(){
        return Collections.unmodifiableSet(this.authorPapers.stream().map(AuthorPaper::getAuthorPaper).collect(Collectors.toSet()));
    }

    public void addPaper(Paper paper){
        AuthorPaper authorPaper = new AuthorPaper();
        authorPaper.setAuthorPaper(paper);
        authorPaper.setAuthor(this);
        authorPapers.add(authorPaper);
    }

    public void addPapers(Set<Paper> papers){
        papers.forEach(this::addPaper);
    }

    public Author(String usern, String password, String name, String affiliation, String email){
        super(usern,password,name,affiliation,email);
    }

    @Override
    public String toString() {
        return "Author{}"+super.toString();
    }
}
