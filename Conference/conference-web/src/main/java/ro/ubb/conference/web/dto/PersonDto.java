package ro.ubb.conference.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by langchristian96 on 5/5/2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonDto extends BaseEntityDto {

    //username
    private String user;

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
        return "PersonDto{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", email='" + email + '\'' +
                '}'+super.toString();
    }
}

