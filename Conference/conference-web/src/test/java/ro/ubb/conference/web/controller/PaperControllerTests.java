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
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.core.domain.Session;
import ro.ubb.conference.core.service.AuthorService;
import ro.ubb.conference.core.service.PaperService;
import ro.ubb.conference.web.converter.PaperConverter;
import ro.ubb.conference.web.dto.PaperDto;

import java.util.*;
import java.util.stream.Collectors;

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
public class PaperControllerTests {
    private MockMvc mockMvc;

    @InjectMocks
    private PaperController paperController;

    @Mock
    private PaperService paperService;

    @Mock
    private PaperConverter paperConverter;

    @Mock
    private AuthorService authorService;

    private Paper paper1;
    private Paper paper2;
    private PaperDto paperDto1;
    private PaperDto paperDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(paperController)
                .build();
        initData();
    }



    @Test
    public void getPapers() throws Exception {
        List<Paper> papers = Arrays.asList(paper1, paper2);
        Set<PaperDto> paperDtos =
                new HashSet<>(Arrays.asList(paperDto1,paperDto2));
        when(paperService.findAll()).thenReturn(papers);
        when(paperConverter.convertModelsToDtos(papers)).thenReturn(paperDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/papers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.papers", hasSize(2)))
                .andExpect(jsonPath("$.papers[0].title", anyOf(is("name1"), is("name2"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        verify(paperService, times(1)).findAll();
        verify(paperConverter, times(1)).convertModelsToDtos(papers);
        verifyNoMoreInteractions(paperService, paperConverter);


    }

    @Test
    public void deletePaper() throws Exception{
        doNothing().when(paperService).deletePaper(paper1.getId());
        ResultActions resultActions=mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/papers/{paperId}",paper1.getId()))


                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));



        verify(paperService, times(1)).deletePaper(paper1.getId());
        verifyNoMoreInteractions(paperService, paperConverter);

    }

    @Test
    public void createPaper() throws Exception{
        Set<String> authors = new HashSet<>();
        authors.add("a1");

        Paper p= Paper.builder().title("newPaper").abstractText("1234").contentPath("newPaper").keywords("keywors").topics("topic1").build();
        p.setId((long) 31);
        PaperDto paperDto= createPaperDto(p);
        when(paperService.createPaper(p.getTitle(),p.getAbstractText(),p.getContentPath(),p.getKeywords(),p.getTopics())).thenReturn(p);
        when(paperService.updatePaperAuthors(p.getId(),authorService.findAllAuthorsByUsernames(paperDto1.getAuthorsUsername()))).thenReturn(p);
        when(paperConverter.convertModelToDto(p)).thenReturn(paperDto);
        Map<String,PaperDto> paperDtoMap=new HashMap<>();
        paperDtoMap.put("paper",paperDto);

        ResultActions resultActions=mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/papers",paperDto)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(paperDtoMap)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.paper.title", is("newPaper")))
                .andExpect(jsonPath("$.paper.contentPath", is("newPaper")));



        verify(paperService, times(1)).createPaper(p.getTitle(),p.getAbstractText(),p.getContentPath(),p.getKeywords(),p.getTopics());
        //verifyNoMoreInteractions(paperService, paperConverter);
    }

    @Test
    public void updatePaper() throws Exception {

        when(paperService.updatePaper(paper1.getId()
                ,paperDto1.getTitle(),paperDto1.getAbstractText(),paperDto1.getContentPath(),paperDto1.getKeywords(),paperDto1.getTopics(),authorService.findAllAuthorsByUsernames(paperDto1.getAuthorsUsername()),null))
                .thenReturn(paper1);

        when(paperConverter.convertModelToDto(paper1)).thenReturn(paperDto1);

        Map<String, PaperDto> paperDtoHashMap = new HashMap<>();
        paperDtoHashMap.put("paper", paperDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/papers/{paperId}", paper1.getId(), paperDtoHashMap)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(paperDtoHashMap)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.paper.title", is("name1")))
                .andExpect(jsonPath("$.paper.contentPath", is("name1")));

        verify(paperService, times(1)).updatePaper(paper1.getId()
                ,paperDto1.getTitle(),paperDto1.getAbstractText(),paperDto1.getContentPath(),paperDto1.getKeywords(),paperDto1.getTopics(),authorService.findAllAuthorsByUsernames(paperDto1.getAuthorsUsername()),null);
    }



    private String toJsonString(Map<String, PaperDto> paperDtoMap) {
        try {
            return new ObjectMapper().writeValueAsString(paperDtoMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initData(){

        paper1=Paper.builder().title("name1").abstractText("abstractText1").contentPath("name1").keywords("keywords1").topics("topics1").build();
        paper1.setId(2l);
        paper2=Paper.builder().title("name2").abstractText("abstractText2").contentPath("name2").keywords("keywords2").topics("topics2").build();
        paper2.setId(3l);

        paperDto1=createPaperDto(paper1);
        paperDto2=createPaperDto(paper2);

    }
    private PaperDto createPaperDto(Paper paper) {
        PaperDto paperDto;
        Set<String> authors = new HashSet<>();
        authors.add("a1");

        paperDto = PaperDto.builder()
                .title(paper.getTitle())
                .abstractText(paper.getAbstractText())
                .contentPath(paper.getContentPath())
                .keywords(paper.getKeywords())
                .authorsUsername(authors)
                .topics(paper.getTopics())
                .build();

        return paperDto;

    }

}
