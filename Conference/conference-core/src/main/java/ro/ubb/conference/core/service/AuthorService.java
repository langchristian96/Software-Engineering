package ro.ubb.conference.core.service;

import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Listener;

import java.util.List;

/**
 * Created by langchristian96 on 5/18/2017.
 */



public interface AuthorService {
    List<Author> findAll();

    Author updateAuthor(Long personId, String password, String name, String affiliation, String email);

    Author createAuthor(String user, String password, String name, String affiliation, String email);

    void deleteAuthor(Long personId);

}
