package ro.ubb.conference.web.dto;

import lombok.*;

/**
 * Created by langchristian96 on 5/5/2017.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonDto extends BaseEntityDto {

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
        return "PersonDto{" +
                "user='" + usern + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", email='" + email + '\'' +
                '}'+super.toString();
    }
}

