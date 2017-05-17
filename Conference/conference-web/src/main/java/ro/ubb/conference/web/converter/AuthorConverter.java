package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.web.dto.AuthorDto;
import ro.ubb.conference.web.dto.ListenerDto;

/**
 * Created by langchristian96 on 5/18/2017.
 */
@Component
public class AuthorConverter extends BaseConverter<Author, AuthorDto> {


    @Override
    public AuthorDto convertModelToDto(Author person) {
        AuthorDto personDto= new AuthorDto(person.getUsern(),person.getPassword(),person.getName(),person.getAffiliation(),person.getEmail());
        personDto.setId(person.getId());
        return personDto;
    }
}