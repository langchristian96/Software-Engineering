package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.ReviewerPaper;
import ro.ubb.conference.web.dto.ReviewerPaperDto;

/**
 * Created by CristianCosmin on 18.05.2017.
 */
@Component
public class ReviewerPaperConverter extends BaseConverterGeneric<ReviewerPaper, ReviewerPaperDto> {

    @Override
    public ReviewerPaper convertDtoToModel(ReviewerPaperDto reviewerPaperDto){
        throw new RuntimeException("not impl");
    }

    @Override
    public ReviewerPaperDto convertModelToDto(ReviewerPaper reviewerPaper){
        ReviewerPaperDto reviewerPaperDto = ReviewerPaperDto.builder().paperId(reviewerPaper.getReviewerPaper().getId())
                .reviewerId(reviewerPaper.getReviewer().getId()).paperTitle(reviewerPaper.getReviewerPaper().getTitle()).build();
        return reviewerPaperDto;
    }
}