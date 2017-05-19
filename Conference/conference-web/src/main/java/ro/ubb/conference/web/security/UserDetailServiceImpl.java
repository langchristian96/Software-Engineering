package ro.ubb.conference.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.core.service.PersonService;

/**
 * Created by langchristian96 on 5/18/2017.
 */


@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PersonService service;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Person account = service.findPersonByUser(name);
        if(account == null) {
            throw new UsernameNotFoundException("no user found with " + name);
        }
        return new PersonUserDetails(account);
    }
}