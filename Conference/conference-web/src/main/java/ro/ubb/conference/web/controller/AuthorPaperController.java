package ro.ubb.conference.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.AuthorPaper;
import ro.ubb.conference.core.service.AuthorService;
import ro.ubb.conference.web.converter.AuthorPaperConverter;
import ro.ubb.conference.web.dto.AuthorPaperDto;
import ro.ubb.conference.web.dto.AuthorPapersDto;

import java.util.Set;

/**
 * Created by langchristian96 on 5/18/2017.
 */




@RestController
public class AuthorPaperController {
    private static final Logger log = LoggerFactory.getLogger(AuthorPaperController.class);

    @Autowired
    private AuthorService clientService;

    @Autowired
    private AuthorPaperConverter clientBookConverter;

    @RequestMapping(value="authorpapers/{clientId}",method= RequestMethod.GET)
    public AuthorPapersDto getClientBooks(
            @PathVariable final Long clientId
    ) {
        log.trace("getAuthorPapers : clientId={}",clientId);

        Author client=clientService.findAuthor(clientId);
        Set<AuthorPaper> clientBookSet=client.getAuthorPapers();
        Set<AuthorPaperDto> clientBookDtos=clientBookConverter.convertModelsToDtos(clientBookSet);

        AuthorPapersDto clientBooksDto=new AuthorPapersDto(clientBookDtos);

        log.trace("getAuthorPapers: result={}",clientBooksDto);
        return clientBooksDto;


    }



}