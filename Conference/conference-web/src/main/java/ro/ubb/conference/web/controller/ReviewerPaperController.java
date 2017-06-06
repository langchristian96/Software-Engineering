package ro.ubb.conference.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.conference.core.domain.Reviewer;
import ro.ubb.conference.core.domain.ReviewerPaper;
import ro.ubb.conference.core.service.ReviewerService;
import ro.ubb.conference.web.converter.ReviewerPaperConverter;
import ro.ubb.conference.web.dto.ReviewerPaperDto;
import ro.ubb.conference.web.dto.ReviewerPapersDto;

import java.util.Set;

@RestController
public class ReviewerPaperController {
    private static final Logger log = LoggerFactory.getLogger(AuthorPaperController.class);

    @Autowired
    private ReviewerService reviewerService;

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
}
