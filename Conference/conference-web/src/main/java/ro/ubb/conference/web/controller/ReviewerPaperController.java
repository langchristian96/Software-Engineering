package ro.ubb.conference.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Reviewer;
import ro.ubb.conference.core.domain.ReviewerPaper;
import ro.ubb.conference.core.service.PaperService;
import ro.ubb.conference.core.service.ReviewerService;
import ro.ubb.conference.web.converter.ReviewerPaperConverter;
import ro.ubb.conference.web.dto.EmptyJsonResponse;
import ro.ubb.conference.web.dto.ReviewerPaperDto;
import ro.ubb.conference.web.dto.ReviewerPapersDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class ReviewerPaperController {
    private static final Logger log = LoggerFactory.getLogger(AuthorPaperController.class);

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private ReviewerPaperConverter reviewerPaperConverter;

    @RequestMapping(value="/reviewerpapers/{reviewerId}",method= RequestMethod.GET)
    public ReviewerPapersDto getReviewerPapers(
            @PathVariable final Long reviewerId
    ) {
        log.trace("getAuthorPapers : reviewerId={}",reviewerId);

        Reviewer reviewer = reviewerService.findReviewer(reviewerId);
        Set<ReviewerPaper> reviewerPapers = reviewer.getReviewerPapers();
        Set<ReviewerPaperDto> reviewerPaperDtos = reviewerPaperConverter.convertModelsToDtos(reviewerPapers);

        ReviewerPapersDto reviewerPapersDto = new ReviewerPapersDto(reviewerPaperDtos);

        log.trace("getReviewerPapers: result={}",reviewerPapersDto);
        return reviewerPapersDto;
    }

    @RequestMapping(value = "reviewerpapers/{paperId}/{reviewerId}", method = RequestMethod.PUT)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity updateGrade(
            @PathVariable("paperId") final Long paperId,
            @PathVariable("reviewerId") final Long reviewerId,
            @RequestBody Map<String, Integer> data) {
        log.trace("updatePaper: paperId={}, paperDtoMap={}", paperId, reviewerId);

        Reviewer r = reviewerService.updateReviewerGrade(reviewerId, paperId, data.get("data"));

//        Map<String, ReviewerPaperDto> result = new HashMap<>();
//        result.put("reviewerPaper", reviewerConverter.convertModelToDto(reviewerPaper));

//        log.trace("updatePaper: result={}", result);

        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
