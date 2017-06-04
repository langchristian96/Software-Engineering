package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by langchristian96 on 5/5/2017.
 */

@Entity

@Table(name = "person",uniqueConstraints = {
            @UniqueConstraint(columnNames = "usern")
            })
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.JOINED)
public class Person extends BaseEntity<Long> {

    //username
    @Column(name = "usern", nullable = false)
    private String usern;

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

    public Person(String usern, String password, String name, String affiliation, String email) {
        this.usern = usern;
        this.password = password;
        this.name = name;
        this.affiliation = affiliation;
        this.email = email;
        this.userRole=UserRole.NORMAL;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

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

