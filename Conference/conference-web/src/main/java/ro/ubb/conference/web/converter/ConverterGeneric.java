package ro.ubb.conference.web.converter;

/**
 * Created by langchristian96 on 5/18/2017.
 */


public interface ConverterGeneric<Model, Dto> {
    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);
}

