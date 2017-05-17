package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.web.dto.ListenerDto;
import ro.ubb.conference.web.dto.PersonDto;

/**
 * Created by langchristian96 on 5/18/2017.
 */



@Component
public class ListenerConverter extends BaseConverter<Listener, ListenerDto> {


    @Override
    public ListenerDto convertModelToDto(Listener person) {
        ListenerDto personDto= new ListenerDto(person.getUsern(),person.getPassword(),person.getName(),person.getAffiliation(),person.getEmail());
        personDto.setId(person.getId());
        return personDto;
    }
}