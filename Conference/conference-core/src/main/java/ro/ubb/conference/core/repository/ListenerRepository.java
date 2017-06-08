package ro.ubb.conference.core.repository;

import org.springframework.data.jpa.repository.Query;
import ro.ubb.conference.core.domain.Listener;

/**
 * Created by langchristian96 on 5/17/2017.
 */
public interface ListenerRepository extends Repository<Listener,Long> {


    @Query("select u from Listener u where u.usern=?1")
    Listener getUserByUserName(String userName);
}
