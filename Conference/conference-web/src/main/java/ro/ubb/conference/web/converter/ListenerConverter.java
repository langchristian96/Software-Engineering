package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.BaseEntity;
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.web.dto.ListenerDto;
import ro.ubb.conference.web.dto.PersonDto;

import java.util.stream.Collectors;

/**
 * Created by langchristian96 on 5/18/2017.
 */



@Component
public class ListenerConverter extends BaseConverter<Listener, ListenerDto> {


    @Override
    public ListenerDto convertModelToDto(Listener listener) {
        ListenerDto listenerDto = ListenerDto.builder()
                .usern(listener.getUsern())
                .name(listener.getName())
                .password(listener.getPassword())
                .email(listener.getEmail())
                .affiliation(listener.getAffiliation())
                .build();
        listenerDto.setId(listener.getId());
        listenerDto.setSessions(listener.getSessions().stream()
                .map(BaseEntity::getId).collect(Collectors.toSet())
        );
        return listenerDto;
    }
}