package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.BaseEntity;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.web.dto.PaperDto;

import java.util.stream.Collectors;

/**
 * Created by anca.
 */

@Component
public class PaperConverter extends BaseConverter<Paper, PaperDto> {
    @Override
    public PaperDto convertModelToDto(Paper paper) {
        PaperDto paperDto = PaperDto.builder()
                .title(paper.getTitle())
                .abstractText(paper.getAbstractText())
                .keywords(paper.getKeywords())
                .topics(paper.getTopics())
                .build();
        if(paper.getContentPath() != null){
            paperDto.setContentPath(paper.getContentPath());
        }
        if(paper.getSession() != null){
            paperDto.setSessionId(paper.getSession().getId());
        }
        paperDto.setId(paper.getId());
        paperDto.setAuthorsUsername(paper.getAuthors().stream()
                .map(Person::getUsern).collect(Collectors.toSet())
        );
        paperDto.setReviewers(paper.getReviewers().stream().map(BaseEntity::getId).collect(Collectors.toSet()));
        return paperDto;
    }
}
