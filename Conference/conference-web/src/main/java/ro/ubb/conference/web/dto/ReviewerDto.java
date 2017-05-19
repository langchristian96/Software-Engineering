package ro.ubb.conference.web.dto;

import lombok.*;

import java.util.Set;

/**
 * Created by langchristian96 on 5/18/2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReviewerDto extends BaseEntityDto {

    //username
    private String usern;

    //password
    private String password;


    //name
    private String name;

    //affiliation
    private String affiliation;

    //email
    private String email;

    private Set<Long> papers;

    @Override
    public String toString() {
        return "ReviewerDto{" +
                "user='" + usern + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", email='" + email + '\'' +
                '}'+super.toString();
    }
}

