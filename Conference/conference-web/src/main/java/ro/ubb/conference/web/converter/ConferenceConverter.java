package ro.ubb.conference.web.converter;

import ro.ubb.conference.core.domain.Conference;
import ro.ubb.conference.web.dto.ConferenceDto;

/**
 * Created by user on 5/4/2017.
 */
public class ConferenceConverter extends BaseConverter<Conference, ConferenceDto> {

    @Override
    public ConferenceDto convertModelToDto(Conference conference) {
        ConferenceDto conferenceDto = new ConferenceDto(conference.getName(), conference.getEdition(), conference.getStartDate(), conference.getEndDate(), conference.getCallDate(), conference.getPapersDeadline(), conference.getCommittee(), conference.getSections());
        conferenceDto.setId(conference.getId());
        return conferenceDto;

    }
}
