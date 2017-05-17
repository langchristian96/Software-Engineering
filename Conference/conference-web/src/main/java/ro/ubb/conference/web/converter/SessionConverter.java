package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.Session;
import ro.ubb.conference.web.dto.SessionDto;

/**
 * Created by Budu.
 */

@Component
public class SessionConverter extends BaseConverter<Session, SessionDto> {
    @Override
    public SessionDto convertModelToDto(Session session) {
        SessionDto sessionDto = new SessionDto(session.getDate(), session.getConferenceId(), session.getSessionChairId());
        sessionDto.setId(session.getId());
        return sessionDto;
    }
}
