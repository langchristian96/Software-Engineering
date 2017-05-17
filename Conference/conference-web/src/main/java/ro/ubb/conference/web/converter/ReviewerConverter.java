package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Reviewer;
import ro.ubb.conference.web.dto.AuthorDto;
import ro.ubb.conference.web.dto.ReviewerDto;

/**
 * Created by langchristian96 on 5/18/2017.
 */
@Component
public class ReviewerConverter extends BaseConverter<Reviewer, ReviewerDto> {


    @Override
    public ReviewerDto convertModelToDto(Reviewer person) {
        ReviewerDto personDto= new ReviewerDto(person.getUsern(),person.getPassword(),person.getName(),person.getAffiliation(),person.getEmail());
        personDto.setId(person.getId());
        return personDto;
    }
}
