package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.BaseEntity;
import ro.ubb.conference.core.domain.Reviewer;
import ro.ubb.conference.web.dto.AuthorDto;
import ro.ubb.conference.web.dto.ReviewerDto;

import java.util.stream.Collectors;

/**
 * Created by langchristian96 on 5/18/2017.
 */
@Component
public class ReviewerConverter extends BaseConverter<Reviewer, ReviewerDto> {


    @Override
    public ReviewerDto convertModelToDto(Reviewer reviewer) {
        ReviewerDto reviewerDto = ReviewerDto.builder()
                .usern(reviewer.getUsern())
                .name(reviewer.getName())
                .password(reviewer.getPassword())
                .email(reviewer.getEmail())
                .affiliation(reviewer.getAffiliation())
                .build();
        reviewerDto.setId(reviewer.getId());
        reviewerDto.setPapers(reviewer.getPapers().stream()
                .map(BaseEntity::getId).collect(Collectors.toSet())
        );
        return reviewerDto;
    }
}
