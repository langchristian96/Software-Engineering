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
@Table(name = "paper")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Paper extends BaseEntity<Long> {

    //title of the paper
    @Column(name = "title", nullable = false)
    private String title;

    //author of the paper
    @Column(name = "author", nullable = false)
    private String author;

    //content of the paper
	@Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "paper", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AuthorPaper> authors=new HashSet<>();




    public Set<Author> getAuthors(){
        return Collections.unmodifiableSet(authors.stream().map(cb->cb.getAuthor()).collect(Collectors.toSet()));
    }

    public void addAuthor(Author client){
        AuthorPaper clientBook=new AuthorPaper();
        clientBook.setPaper(this);
        clientBook.setAuthor(client);
        authors.add(clientBook);
    }


    public void addAuthors(Set<Author> clients){
        clients.stream().forEach(c->addAuthor(c));
    }


    public Paper(String title, String author, String content){
        this.title=title;
        this.author=author;
        this.content=content;
    }


    @Override
    public String toString() {
        return "Paper{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content=" + content +
                "} " + super.toString();
    }
}
