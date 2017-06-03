package ro.ubb.conference.core.domain;

import lombok.*;

import java.io.Serializable;

/**
 * Created by langchristian96 on 5/18/2017.
 */



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AuthorPaperPK implements Serializable {
    private Author author;
    private Paper authorPaper;
}