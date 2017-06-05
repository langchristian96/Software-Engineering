package ro.ubb.conference.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.core.service.AuthorService;
import ro.ubb.conference.core.service.PaperService;
import ro.ubb.conference.web.converter.AuthorConverter;
import ro.ubb.conference.web.dto.AuthorDto;

import java.util.*;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by paul on 6/4/2017.
 */
public class AuthorControllerTests {
    private MockMvc mockMvc;

    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorService authorService;

    @Mock
    private AuthorConverter authorConverter;

    @Mock
    private PaperService paperService;


    private Author author1;
    private Author author2;
    private AuthorDto authorDto1;
    private AuthorDto authorDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(authorController)
                .build();
        initData();
    }



    @Test
    public void getAuthors() throws Exception {
        List<Author> authors = Arrays.asList(author1, author2);
        Set<AuthorDto> authorDtos =
                new HashSet<>(Arrays.asList(authorDto1,authorDto2));
        when(authorService.findAll()).thenReturn(authors);
        when(authorConverter.convertModelsToDtos(authors)).thenReturn(authorDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                //.andDo(print())
                .andExpect(jsonPath("$.persons", hasSize(2)))
                .andExpect(jsonPath("$.persons[0].name", anyOf(is("name1"), is("name2"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        verify(authorService, times(1)).findAll();
        verify(authorConverter, times(1)).convertModelsToDtos(authors);
        verifyNoMoreInteractions(authorService, authorConverter);


    }

    @Test
    public void deleteAuthor() throws Exception{
        doNothing().when(authorService).deleteAuthor(author1.getId());
        ResultActions resultActions=mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/authors/{personId}",author1.getId()))


                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));



        verify(authorService, times(1)).deleteAuthor(author1.getId());
        verifyNoMoreInteractions(authorService, authorConverter);

    }

    @Test
    public void createAuthor() throws Exception{
        Author p=new Author("newAuthor","1234","newAuthor","nasdf","newAuthor@newAuthor.com");
        p.setId((long) 31);
        AuthorDto authorDto=createAuthorDto(p);
        when(authorService.createAuthor(p.getUsern(),p.getPassword(),p.getName(),p.getAffiliation(),p.getEmail()))
                .thenReturn(p);
        when(authorService.updateAuthorPapers(p.getId(), paperService.findAllPapersByTitle(authorDto.getPapers()))).thenReturn(p);
        when(authorConverter.convertModelToDto(p)).thenReturn(authorDto);
        Map<String,AuthorDto> authorDtoMap=new HashMap<>();
        authorDtoMap.put("author",authorDto);

        ResultActions resultActions=mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/authors",authorDto)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(authorDtoMap)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.author.usern", is("newAuthor")))
                .andExpect(jsonPath("$.author.name", is("newAuthor")));



        verify(authorService, times(1)).createAuthor(p.getUsern(),p.getPassword(),p.getName(),p.getAffiliation(),p.getEmail());
    }

    @Test
    public void updateAuthor() throws Exception {

        when(authorService.updateAuthor(author1.getId()
                ,authorDto1.getPassword(),authorDto1.getName(),authorDto1.getAffiliation(),authorDto1.getEmail(),paperService.findAllPapersByTitle(authorDto1.getPapers())))
                .thenReturn(author1);

        when(authorConverter.convertModelToDto(author1)).thenReturn(authorDto1);

        Map<String, AuthorDto> authorDtoHashMap = new HashMap<>();
        authorDtoHashMap.put("author", authorDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/authors/{personId}", author1.getId(), authorDtoHashMap)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(authorDtoHashMap)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.author.name", is("name1")))
                .andExpect(jsonPath("$.author.usern", is("name1")));

        verify(authorService, times(1)).updateAuthor(author1.getId()
                ,authorDto1.getPassword(),authorDto1.getName(),authorDto1.getAffiliation(),authorDto1.getEmail(),paperService.findAllPapersByTitle(authorDto1.getPapers()));
    }



    private String toJsonString(Map<String, AuthorDto> authorDtoMap) {
        try {
            return new ObjectMapper().writeValueAsString(authorDtoMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initData(){
        author1= new Author("name1","1234","name1","affiliation1","name1@name1.com");
        author1.setId(2l);
        author2=new Author("name2","1234","name2","affiliation1","name2@name2.com");
        author2.setId(3l);

        authorDto1=createAuthorDto(author1);
        authorDto2=createAuthorDto(author2);

    }


    private AuthorDto createAuthorDto(Author author) {
        AuthorDto authorDto=AuthorDto.builder().usern(author.getUsern())
                .name(author.getName())
                .affiliation(author.getAffiliation())
                .email(author.getEmail())
                .password(author.getPassword())
                .build();
        authorDto.setId(author.getId());
        return authorDto;

    }

}
