package ro.ubb.conference.core.repository;

import org.springframework.data.jpa.repository.Query;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.domain.Reviewer;

/**
 * Created by langchristian96 on 5/18/2017.
 */
public interface ReviewerRepository extends Repository<Reviewer,Long> {


    @Query("select u from Reviewer u where u.usern=?1")
    Reviewer getUserByUserName(String userName);
}

