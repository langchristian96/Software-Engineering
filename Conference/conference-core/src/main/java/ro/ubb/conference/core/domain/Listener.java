package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by langchristian96 on 5/17/2017.
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="listener")
public class Listener extends Person {

    @Override
    public String toString() {
        return "Listener{}"+super.toString();
    }
}
