package ro.ubb.conference.core.service;

import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.domain.Paper;

import java.util.List;
import java.util.Set;

/**
 * Created by langchristian96 on 5/18/2017.
 */



public interface AuthorService {
    List<Author> findAll();

    Author findAuthor(Long authorId);

    Set<Author> findAllAuthorsByUsernames(Set<String> authorsUsername);

    Author updateAuthor(Long personId, String password, String name, String affiliation, String email, Set<Paper> papers);

    Author updateAuthorPapers(Long authorId, Set<Paper> papers);

    Author createAuthor(String user, String password, String name, String affiliation, String email);

    void deleteAuthor(Long personId);

}
