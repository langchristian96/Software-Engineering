package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by langchristian96 on 5/5/2017.
 */


@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Person extends BaseEntity<Long> {

    //username
    @Column(name = "user", nullable = false)
    private String user;

    //password
    @Column(name="password",nullable = false)
    private String password;


    //name
    @Column(name="name",nullable = false)
    private String name;

    //affiliation
    @Column(name="affiliation",nullable=false)
    private String affiliation;

    //email
    @Column(name="email",nullable = false)
    private String email;

    @Override
    public String toString() {
        return "Person{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

