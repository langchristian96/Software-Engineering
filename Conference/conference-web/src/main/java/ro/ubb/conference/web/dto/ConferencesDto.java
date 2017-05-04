package ro.ubb.conference.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Created by user on 5/4/2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConferencesDto {
    private Set<ConferenceDto> conferences;
}
