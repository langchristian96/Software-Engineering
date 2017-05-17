package ro.ubb.conference.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Reviewer;
import ro.ubb.conference.core.service.AuthorService;
import ro.ubb.conference.core.service.ReviewerService;
import ro.ubb.conference.web.converter.AuthorConverter;
import ro.ubb.conference.web.converter.ReviewerConverter;
import ro.ubb.conference.web.dto.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by langchristian96 on 5/18/2017.
 */

@RestController
public class ReviewerController {

    private static final Logger log = LoggerFactory.getLogger(ReviewerController.class);

    @Autowired
    private ReviewerService personService;

    @Autowired
    private ReviewerConverter personConverter;

    @RequestMapping(value = "/reviewers", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    public ReviewersDto getReviewers() {
        log.trace("getReviewers");

        List<Reviewer> persons = personService.findAll();

        log.trace("getReviewers: persons={}", persons);

        return new ReviewersDto(personConverter.convertModelsToDtos(persons));
    }

    @RequestMapping(value = "/reviewers/{personId}", method = RequestMethod.PUT)
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, ReviewerDto> updateReviewer(
            @PathVariable final Long personId,
            @RequestBody final Map<String, ReviewerDto> personDtoMap) {
        log.trace("updateReviewer: personId={}, personDtoMap={}", personId, personDtoMap);

        ReviewerDto personDto = personDtoMap.get("reviewer");
        Reviewer person = personService.updateReviewer(personId,personDto.getPassword(),personDto.getName(),personDto.getAffiliation(),personDto.getEmail());

        Map<String, ReviewerDto> result = new HashMap<>();
        result.put("reviewer", personConverter.convertModelToDto(person));

        log.trace("updateReviewer: result={}", result);

        return result;
    }

    @RequestMapping(value = "/reviewers", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, ReviewerDto> createReviewer(
            @RequestBody final Map<String, ReviewerDto> personDtoMap) {
        log.trace("createReviewer: personDtoMap={}", personDtoMap);

        ReviewerDto personDto = personDtoMap.get("reviewer");
        Reviewer person = personService.createReviewer(personDto.getUsern(),personDto.getPassword(),personDto.getName(),personDto.getAffiliation(),personDto.getEmail());

        Map<String, ReviewerDto> result = new HashMap<>();
        result.put("reviewer", personConverter.convertModelToDto(person));

        log.trace("updateReviewer: result={}", result);

        return result;
    }

    @RequestMapping(value = "/reviewers/{personId}", method = RequestMethod.DELETE)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity deleteReviewer(@PathVariable final Long personId) {
        log.trace("deleteReviewer: personId={}", personId);



        personService.deleteReviewer(personId);

        log.trace("deleteReviewer - method end");

        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }
}

