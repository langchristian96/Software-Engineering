package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.BaseEntity;
import ro.ubb.conference.core.domain.Conference;
import ro.ubb.conference.web.dto.ConferenceDto;

import java.util.stream.Collectors;

/**
 * Created by user on 5/4/2017.
 */
@Component
public class ConferenceConverter extends BaseConverter<Conference, ConferenceDto> {

    @Override
    public ConferenceDto convertModelToDto(Conference conference) {
//        ConferenceDto conferenceDto = new ConferenceDto(conference.getName(), conference.getEdition(), conference.getStartDate(), conference.getEndDate(), conference.getCallDate(), conference.getPapersDeadline(), conference.getSessions());
//        conferenceDto.setId(conference.getId());
        ConferenceDto conferenceDto = ConferenceDto.builder()
                .name(conference.getName())
                .edition(conference.getEdition())
                .startDate(conference.getStartDate())
                .endDate(conference.getEndDate())
                .callDate(conference.getCallDate())
                .papersDeadline(conference.getPapersDeadline())
                .build();
        conferenceDto.setId(conference.getId());
        conferenceDto.setSessions(conference.getSessions().stream()
                .map(BaseEntity::getId).collect(Collectors.toSet())
        );
        return conferenceDto;

    }
}
