package ro.ubb.conference.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.ubb.conference.web.dto.PaperDto;

import java.util.Set;

/**
 * Created by anca.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PapersDto {
    private Set<PaperDto> papers;
}
