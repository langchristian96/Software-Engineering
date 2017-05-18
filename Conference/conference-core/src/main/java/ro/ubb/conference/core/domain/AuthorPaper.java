package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by langchristian96 on 5/18/2017.
 */





@Entity
@Table(name="Author_Paper")
@IdClass(AuthorPaperPK.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AuthorPaper implements Serializable {

    @Id
    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    @JoinColumn(name="author")
    private Author author;


    @Id
    @ManyToOne(optional=false,fetch=FetchType.EAGER)
    @JoinColumn(name="paper")
    private Paper paper;



}