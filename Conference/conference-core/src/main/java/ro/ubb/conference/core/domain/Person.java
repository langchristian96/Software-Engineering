package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by langchristian96 on 5/5/2017.
 */

@Entity
@Table(name = "Person")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.JOINED)
public class Person extends BaseEntity<Long> {

    //username
    @Column(name = "Username", nullable = false)
    private String usern;

    //password
    @Column(name="Password",nullable = false)
    private String password;


    //name
    @Column(name="Name",nullable = false)
    private String name;

    //affiliation
    @Column(name="Affiliation",nullable=false)
    private String affiliation;

    //email
    @Column(name="Email",nullable = false)
    private String email;

    public Person(String usern, String password, String name, String affiliation, String email) {
        this.usern = usern;
        this.password = password;
        this.name = name;
        this.affiliation = affiliation;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "usern='" + usern + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", email='" + email + '\'' +
                '}'+super.toString();
    }
}

