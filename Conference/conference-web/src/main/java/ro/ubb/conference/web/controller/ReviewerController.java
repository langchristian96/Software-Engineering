package ro.ubb.conference.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ro.ubb.conference.core.domain.Reviewer;
import ro.ubb.conference.core.service.ReviewerService;
import ro.ubb.conference.web.converter.ReviewerConverter;
import ro.ubb.conference.web.dto.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReviewerController {

    private static final Logger log = LoggerFactory.getLogger(ReviewerController.class);


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReviewerConverter reviewerConverter;

    @RequestMapping(value = "/reviewers", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    public ReviewersDto getReviewers() {
        log.trace("getReviewers");

        List<Reviewer> reviewers = reviewerService.findAll();

        log.trace("getReviewers: reviewer={}", reviewers);

        return new ReviewersDto(reviewerConverter.convertModelsToDtos(reviewers));
    }

    @RequestMapping(value = "/reviewers/{reviewerId}", method = RequestMethod.PUT)
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, ReviewerDto> updateReviewer(
            @PathVariable final Long reviewerId,
            @RequestBody final Map<String, ReviewerDto> reviewerDtoMap) {
        log.trace("updateReviewer: personId={}, reviewerDtoMap={}", reviewerId, reviewerDtoMap);

        ReviewerDto reviewerDto = reviewerDtoMap.get("reviewer");
        Reviewer reviewer = reviewerService.updateReviewer(reviewerId, passwordEncoder.encode(reviewerDto.getPassword()), reviewerDto.getName(), reviewerDto.getAffiliation(), reviewerDto.getEmail(), reviewerDto.getPapers());

        Map<String, ReviewerDto> result = new HashMap<>();
        result.put("reviewer", reviewerConverter.convertModelToDto(reviewer));

        log.trace("updateReviewer: result={}", result);

        return result;
    }

    @RequestMapping(value = "/reviewers", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, ReviewerDto> createReviewer(
            @RequestBody final Map<String, ReviewerDto> reviewerDtoMap) {
        log.trace("createReviewer: reviewerDtoMap={}", reviewerDtoMap);

        ReviewerDto personDto = reviewerDtoMap.get("reviewer");
        Reviewer person = reviewerService.createReviewer(personDto.getUsern(),passwordEncoder.encode(personDto.getPassword()),personDto.getName(),personDto.getAffiliation(),personDto.getEmail());

        Map<String, ReviewerDto> result = new HashMap<>();
        result.put("reviewer", reviewerConverter.convertModelToDto(person));

        log.trace("updateReviewer: result={}", result);

        return result;
    }

    @RequestMapping(value = "/reviewers/{reviewerId}", method = RequestMethod.DELETE)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity deleteReviewer(@PathVariable final Long reviewerId) {
        log.trace("deleteReviewer: reviewerId={}", reviewerId);

        reviewerService.deleteReviewer(reviewerId);

        log.trace("deleteReviewer - method end");

        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }
}

