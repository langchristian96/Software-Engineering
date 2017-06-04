package ro.ubb.conference.core.repository;

import org.springframework.data.jpa.repository.Query;
import ro.ubb.conference.core.domain.Person;

/**
 * Created by langchristian96 on 5/5/2017.
 */
public interface PersonRepository extends Repository<Person,Long> {

    @Query("select u from Person u where u.usern=?1")
    Person getUserByUserName(String userName);
}
