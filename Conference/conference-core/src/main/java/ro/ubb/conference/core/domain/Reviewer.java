package ro.ubb.conference.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Created by langchristian96 on 5/18/2017.
 */





@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="reviewer")
public class Reviewer extends Person {

    @Override
    public String toString() {
        return "Reviewer{}"+super.toString();
    }
}