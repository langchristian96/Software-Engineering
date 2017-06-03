package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.web.dto.AuthorDto;
import ro.ubb.conference.web.dto.ListenerDto;

import java.util.stream.Collectors;

/**
 * Created by langchristian96 on 5/18/2017.
 */
@Component
public class AuthorConverter extends BaseConverter<Author, AuthorDto> {


    @Override
    public AuthorDto convertModelToDto(Author client){
        AuthorDto authorDto = AuthorDto.builder()
                .usern(client.getUsern())
                .name(client.getName())
                .password(client.getPassword())
                .email(client.getEmail())
                .affiliation(client.getAffiliation())
                .build();
        authorDto.setId(client.getId());
        authorDto.setPapers(client.getPapers().stream()
                .map(b->b.getTitle()).collect(Collectors.toSet())
        );
        return authorDto;

    }
}