package ro.ubb.conference.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Created by CristianCosmin on 19.05.2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SessionListenersDto {
    private Set<SessionListenerDto> sessionListeners;
}
