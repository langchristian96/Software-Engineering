package ro.ubb.conference.core.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by langchristian96 on 5/18/2017.
 */

@Entity
@Table(name="AuthorPaper")
@IdClass(AuthorPaperPK.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AuthorPaper implements Serializable {

    @Id
    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    @JoinColumn(name="AuthorId")
    private Author author;

    @Id
    @ManyToOne(optional=false, cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="PaperId")
    private Paper authorPaper;
}