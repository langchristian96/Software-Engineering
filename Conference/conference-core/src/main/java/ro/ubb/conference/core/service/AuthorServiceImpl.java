package ro.ubb.conference.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.*;
import ro.ubb.conference.core.repository.AuthorRepository;
import ro.ubb.conference.core.repository.ListenerRepository;
import ro.ubb.conference.core.repository.PaperRepository;
import ro.ubb.conference.core.repository.PersonRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by langchristian96 on 5/18/2017.
 */

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PaperRepository paperRepository;

    @Override
    public List<Author> findAll() {
        log.trace("findAll");

        List<Author> persons = authorRepository.findAll();

        log.trace("findAll: Authors={}", persons);

        return persons;
    }

    @Override
    public Set<Author> findAllAuthorsByUsernames(Set<String> authorsUsername) {
        log.trace("findAllAuthorsByUsername");

        List<Author> authors = authorRepository.findAll();

        Set<Author> authorSet = authors.stream()
                        .filter(e -> authorsUsername.contains(e.getUsern()))
                        .collect(Collectors.toSet());

        log.trace("findAllAuthorsByUsername: Authors={}", authors);

        return authorSet;
    }

    @Override
    public Author findAuthor(Long authorId){
        log.trace("findAuthor: AuthorId={}",authorId);

        Author client=(Author) authorRepository.findOne(authorId);

        log.trace("findAuthor: Author={}",client);
        return client;
    }

    @Override
    @Transactional
    public Author updateAuthor(Long personId, String password, String name, String affiliation, String email, Set<Paper> papers) {
        log.trace("updateAuthor: personId={}, password={}, name={}, affiliation={}, email={}",
                personId, password, name, affiliation, email);

        Author person = (Author) authorRepository.findOne(personId);
        person.setPassword(password);
        person.setName(name);
        person.setAffiliation(affiliation);
        person.setEmail(email);
//        person.getPapers().stream()
//                .map(d->d.getId())
//                .forEach(i->
//                {
//                    if(papers.contains(i)){
//                        papers.remove(i);
//                    }
//                });
        List<Paper> paperList = paperRepository.findAll(papers.stream().map(paper -> paper.getId()).collect(Collectors.toSet()));
        paperList.forEach(paper -> person.addPaper(paper));


//        List<Paper> paperList=paperRepository.findAll(papers);
//        paperList.forEach(person::addPaper);


        log.trace("updateAuthor: Author={}", person);

        return person;
    }

    @Override
    public Author createAuthor(String user, String password, String name, String affiliation, String email) {
        log.trace("user={}, password={}, name={}, affiliation={}, email={}",
                user, password, name, affiliation, email);

        Author person = new Author();
        //Person p=personRepository.getUserByUserName(user);
        person.setUsern(user);
        person.setEmail(email);
        person.setAffiliation(affiliation);
        person.setPassword(password);
        person.setName(name);
        person.setUserRole(UserRole.NORMAL);
        //person.setId(p.getId());

        person = (Author) authorRepository.save(person);

        log.trace("createAuthor: Author={}", person);

        return person;
    }

    @Override
    @Transactional
    public Author updateAuthorPapers(Long authorId, Set<Paper> papers){
        Author author = (Author) authorRepository.findOne(authorId);
        List<Paper> paperList = paperRepository.findAll(papers.stream().map(paper -> paper.getId()).collect(Collectors.toSet()));
        paperList.forEach(paper -> author.addPaper(paper));
        return author;
    }

    @Override
    public void deleteAuthor(Long personId) {
        log.trace("deleteAuthor: personId={}", personId);

        authorRepository.delete(personId);

        log.trace("deleteAuthor - method end");
    }


}