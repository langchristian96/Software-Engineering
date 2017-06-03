package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.SessionListener;
import ro.ubb.conference.web.dto.SessionListenerDto;

/**
 * Created by CristianCosmin on 19.05.2017.
 */
@Component
public class SessionListenerConverter extends BaseConverterGeneric<SessionListener, SessionListenerDto> {

    @Override
    public SessionListener convertDtoToModel(SessionListenerDto sessionListenerDto){
        throw new RuntimeException("not impl");
    }

    @Override
    public SessionListenerDto convertModelToDto(SessionListener sessionListener){

        SessionListenerDto sessionListenerDto = SessionListenerDto.builder().sessionId(sessionListener.getListenerSession().getId())
                .listenerId(sessionListener.getListener().getId()).build();
        return sessionListenerDto;
    }
}
