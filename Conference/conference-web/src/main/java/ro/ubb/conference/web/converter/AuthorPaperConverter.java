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
    public AuthorPaperDto convertModelToDto(AuthorPaper clientBook){

        AuthorPaperDto clientBookDt=AuthorPaperDto.builder().paperId(clientBook.getPaper().getId())
                .authorId(clientBook.getAuthor().getId()).paperTitle(clientBook.getPaper().getTitle()).build();
        return clientBookDt;

    }


}
