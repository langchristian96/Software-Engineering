package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.AuthorPaper;
import ro.ubb.conference.web.dto.AuthorPaperDto;

/**
 * Created by langchristian96 on 5/18/2017.
 */


@Component
public class AuthorPaperConverter extends BaseConverterGeneric<AuthorPaper, AuthorPaperDto> {

    @Override
    public AuthorPaper convertDtoToModel(AuthorPaperDto clientBookDto){
        throw new RuntimeException("not impl");
    }

    @Override
    public AuthorPaperDto convertModelToDto(AuthorPaper authorPaper){

        AuthorPaperDto authorPaperDto=AuthorPaperDto.builder().paperId(authorPaper.getAuthorPaper().getId())
                .authorId(authorPaper.getAuthor().getId()).paperTitle(authorPaper.getAuthorPaper().getTitle()).build();
        return authorPaperDto;
    }
}
