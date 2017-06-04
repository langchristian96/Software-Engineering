package ro.ubb.conference.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.core.repository.AuthorRepository;
import ro.ubb.conference.core.repository.PaperRepository;
import ro.ubb.conference.core.repository.PersonRepository;

import java.util.List;
import java.util.Set;

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
    public Author findAuthor(Long authorId){
        log.trace("findAuthor: AuthorId={}",authorId);

        Author client=(Author) authorRepository.findOne(authorId);

        log.trace("findAuthor: Author={}",client);
        return client;
    }

    @Override
    @Transactional
    public Author updateAuthor(Long personId, String password, String name, String affiliation, String email, Set<Long> papers) {
        log.trace("updateAuthor: personId={}, password={}, name={}, affiliation={}, email={}",
                personId, password, name, affiliation, email);

        Author person = (Author) authorRepository.findOne(personId);
        person.setPassword(password);
        person.setName(name);
        person.setAffiliation(affiliation);
        person.setEmail(email);

        person.getPapers().stream()
                .map(d->d.getId())
                .forEach(i->
                {
                    if(papers.contains(i)){
                        papers.remove(i);
                    }
                });
        List<Paper> paperList=paperRepository.findAll(papers);
        paperList.forEach(person::addPaper);


        log.trace("updateAuthor: Author={}", person);

        return person;
    }

    @Override
    public Author createAuthor(String user, String password, String name, String affiliation, String email) {
        log.trace("user={}, password={}, name={}, affiliation={}, email={}",
                user, password, name, affiliation, email);

        Author person = new Author();
        Person p=personRepository.getUserByUserName(user);
        person.setUsern(user);
        person.setEmail(email);
        person.setAffiliation(affiliation);
        person.setPassword(password);
        person.setName(name);
        person.setId(p.getId());

        person = (Author) authorRepository.save(person);

        log.trace("createAuthor: Author={}", person);

        return person;
    }

    @Override
    public void deleteAuthor(Long personId) {
        log.trace("deleteAuthor: personId={}", personId);

        authorRepository.delete(personId);

        log.trace("deleteAuthor - method end");
    }


}