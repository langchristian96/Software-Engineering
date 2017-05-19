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
public class ListenerDto extends BaseEntityDto {

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

    private Set<Long> sessions;


    @Override
    public String toString() {
        return "ListenerDto{" +
                "user='" + usern + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", email='" + email + '\'' +
                '}'+super.toString();
    }
}

