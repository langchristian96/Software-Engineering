package ro.ubb.conference.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.web.dto.PaperDto;

/**
 * Created by anca.
 */

@Component
public class PaperConverter extends BaseConverter<Paper, PaperDto> {


    @Override
    public PaperDto convertModelToDto(Paper paper) {
        PaperDto paperDto = new PaperDto(paper.getTitle(), paper.getPaperId, paper.getContent());
        paperDto.setId(paper.getId());
        return personDto;
    }
}
