package ro.ubb.conference.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.core.service.AuthorService;
import ro.ubb.conference.core.service.ListenerService;
import ro.ubb.conference.core.service.PaperService;
import ro.ubb.conference.core.service.PersonService;
import ro.ubb.conference.web.converter.AuthorConverter;
import ro.ubb.conference.web.converter.ListenerConverter;
import ro.ubb.conference.web.dto.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by langchristian96 on 5/18/2017.
 */

@RestController
public class AuthorController {

    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PersonService personService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private AuthorConverter personConverter;

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    public AuthorsDto getAuthors() {
        log.trace("getAuthors");

        List<Author> persons = authorService.findAll();

        log.trace("getAuthors: persons={}", persons);

        return new AuthorsDto(personConverter.convertModelsToDtos(persons));
    }

    @RequestMapping(value = "/authors/{personId}", method = RequestMethod.PUT)
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, AuthorDto> updateAuthor(
            @PathVariable final Long personId,
            @RequestBody final Map<String, AuthorDto> personDtoMap) {
        log.trace("updateAuthor: personId={}, personDtoMap={}", personId, personDtoMap);

        AuthorDto personDto = personDtoMap.get("author");
        Author person = authorService.updateAuthor(personId,passwordEncoder.encode(personDto.getPassword()),personDto.getName(),personDto.getAffiliation(),personDto.getEmail(),paperService.findAllPapersByTitle(personDto.getPapers()));

        Map<String, AuthorDto> result = new HashMap<>();
        result.put("author", personConverter.convertModelToDto(person));

        log.trace("updateAuthor: result={}", result);

        return result;
    }

    @RequestMapping(value = "/authors", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, AuthorDto> createAuthor(
            @RequestBody final Map<String, AuthorDto> personDtoMap) {
        log.trace("createAuthor: personDtoMap={}", personDtoMap);

        AuthorDto authorDto = personDtoMap.get("author");
//        Person p=personService.getUserByUserName(authorDto.getUsern());
        System.out.println("Prev test author create");
        Author author = authorService.createAuthor(authorDto.getUsern(), passwordEncoder.encode(authorDto.getPassword()), authorDto.getName(), authorDto.getAffiliation(), authorDto.getEmail());
        System.out.println(author);
        author = authorService.updateAuthorPapers(author.getId(), paperService.findAllPapersByTitle(authorDto.getPapers()));

        Map<String, AuthorDto> result = new HashMap<>();
        result.put("author", personConverter.convertModelToDto(author));

        log.trace("updateAuthor: result={}", result);

        return result;
    }

    @RequestMapping(value = "/authors/{personId}", method = RequestMethod.DELETE)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity deleteAuthor(@PathVariable final Long personId) {
        log.trace("deleteAuthor: personId={}", personId);



        authorService.deleteAuthor(personId);

        log.trace("deleteAuthor - method end");

        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }
}

