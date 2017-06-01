package ro.ubb.conference.web.dto;

import lombok.*;

import java.util.Set;

/**
 * Created by langchristian96 on 5/18/2017.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class AuthorDto extends BaseEntityDto {
    private String usern;
    private String password;
    private String name;
    private String affiliation;
    private String email;
    private Set<String> papers;

    @Override
    public String toString() {
        return "AuthorDto{" +
                "usern='" + usern + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", email='" + email + '\'' +
                ", papers=" + papers +
                '}';
    }


}


