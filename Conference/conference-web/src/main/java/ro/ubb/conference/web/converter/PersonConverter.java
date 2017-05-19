package ro.ubb.conference.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.web.dto.PersonDto;

/**
 * Created by langchristian96 on 5/5/2017.
 */

@Component
public class PersonConverter extends BaseConverter<Person, PersonDto> {
    @Override
    public PersonDto convertModelToDto(Person person) {
        PersonDto personDto= new PersonDto(person.getUsern(),person.getPassword(),person.getName(),person.getAffiliation(),person.getEmail());
        personDto.setId(person.getId());
        return personDto;
    }
}
