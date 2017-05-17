package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.Conference;
import ro.ubb.conference.web.dto.ConferenceDto;

/**
 * Created by user on 5/4/2017.
 */
@Component
public class ConferenceConverter extends BaseConverter<Conference, ConferenceDto> {

    @Override
    public ConferenceDto convertModelToDto(Conference conference) {
        ConferenceDto conferenceDto = new ConferenceDto(conference.getName(), conference.getEdition(), conference.getStartDate(), conference.getEndDate(), conference.getCallDate(), conference.getPapersDeadline());
        conferenceDto.setId(conference.getId());
        return conferenceDto;

    }
}
