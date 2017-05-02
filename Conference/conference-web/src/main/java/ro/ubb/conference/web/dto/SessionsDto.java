package ro.ubb.conference.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.ubb.conference.web.dto.SessionDto;

import java.util.Set;

/**
 * Created by Budu.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SessionsDto {
    private Set<SessionDto> sessions;
}
