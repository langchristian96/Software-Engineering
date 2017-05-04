package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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


    @Override
    public String toString() {
        return "Paper{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content=" + content +
                "} " + super.toString();
    }
}
