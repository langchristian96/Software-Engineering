package ro.ubb.conference.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by langchristian96 on 5/18/2017.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorDto extends BaseEntityDto {

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


    @Override
    public String toString() {
        return "AuthorDto{" +
                "user='" + usern + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", email='" + email + '\'' +
                '}'+super.toString();
    }
}


