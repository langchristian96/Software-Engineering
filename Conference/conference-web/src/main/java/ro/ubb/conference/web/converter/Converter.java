package ro.ubb.conference.web.converter;

import ro.ubb.conference.core.domain.BaseEntity;
import ro.ubb.conference.web.dto.BaseEntityDto;

public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseEntityDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}