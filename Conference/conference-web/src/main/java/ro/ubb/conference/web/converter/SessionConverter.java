package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.BaseEntity;
import ro.ubb.conference.core.domain.Session;
import ro.ubb.conference.web.dto.SessionDto;

import java.util.stream.Collectors;

/**
 * Created by Budu.
 */

@Component
public class SessionConverter extends BaseConverter<Session, SessionDto> {
    @Override
    public SessionDto convertModelToDto(Session session) {
        SessionDto sessionDto = SessionDto.builder()
                .date(session.getDate())
                .sessionChairId(session.getSessionChairId())
                .conferenceId(session.getConference().getId())
                .build();
        sessionDto.setId(session.getId());
        sessionDto.setPapers(session.getPapers().stream()
                .map(BaseEntity::getId).collect(Collectors.toSet())
        );
        sessionDto.setListeners(session.getListeners().stream()
                .map(BaseEntity::getId).collect(Collectors.toSet())
        );
        return sessionDto;
    }
}
