package ro.ubb.conference.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Created by langchristian96 on 5/18/2017.
 */



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorPapersDto {
    private Set<AuthorPaperDto> authorPapers;
}
