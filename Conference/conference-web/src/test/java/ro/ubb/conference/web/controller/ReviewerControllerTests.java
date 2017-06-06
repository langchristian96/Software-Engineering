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
import ro.ubb.conference.core.domain.Reviewer;
import ro.ubb.conference.core.service.ReviewerService;
import ro.ubb.conference.web.converter.ReviewerConverter;
import ro.ubb.conference.web.dto.ReviewerDto;

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
 * Created by paul on 6/5/2017.
 */
public class ReviewerControllerTests {
    private MockMvc mockMvc;

    @InjectMocks
    private ReviewerController reviewerController;

    @Mock
    private ReviewerService reviewerService;

    @Mock
    private ReviewerConverter reviewerConverter;


    private Reviewer reviewer1;
    private Reviewer reviewer2;
    private ReviewerDto reviewerDto1;
    private ReviewerDto reviewerDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(reviewerController)
                .build();
        initData();
    }



    @Test
    public void getReviewers() throws Exception {
        List<Reviewer> reviewers = Arrays.asList(reviewer1, reviewer2);
        Set<ReviewerDto> reviewerDtos =
                new HashSet<>(Arrays.asList(reviewerDto1,reviewerDto2));
        when(reviewerService.findAll()).thenReturn(reviewers);
        when(reviewerConverter.convertModelsToDtos(reviewers)).thenReturn(reviewerDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/reviewers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.reviewers", hasSize(2)))
                .andExpect(jsonPath("$.reviewers[0].name", anyOf(is("name1"), is("name2"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        verify(reviewerService, times(1)).findAll();
        verify(reviewerConverter, times(1)).convertModelsToDtos(reviewers);
        verifyNoMoreInteractions(reviewerService, reviewerConverter);


    }

    @Test
    public void deleteReviewer() throws Exception{
        doNothing().when(reviewerService).deleteReviewer(reviewer1.getId());
        ResultActions resultActions=mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/reviewers/{reviewerId}",reviewer1.getId()))


                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));



        verify(reviewerService, times(1)).deleteReviewer(reviewer1.getId());
        verifyNoMoreInteractions(reviewerService, reviewerConverter);

    }

    @Test
    public void createReviewer() throws Exception{
        Reviewer r=new Reviewer();
        r.setUsern("newReviewer");
        r.setName("newReviewer");
        r.setPassword("1234");
        r.setEmail("name2@name2.com");
        r.setAffiliation("affiliation2");
        r.setId(2l);
        ReviewerDto reviewerDto=createReviewerDto(r);
        when(reviewerService.createReviewer(r.getUsern(),r.getPassword(),r.getName(),r.getAffiliation(),r.getEmail()))
                .thenReturn(r);
        when(reviewerConverter.convertModelToDto(r)).thenReturn(reviewerDto);
        Map<String,ReviewerDto> reviewerDtoMap=new HashMap<>();
        reviewerDtoMap.put("reviewer",reviewerDto);

        ResultActions resultActions=mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/reviewers",reviewerDto)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(reviewerDtoMap)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.reviewer.usern", is("newReviewer")))
                .andExpect(jsonPath("$.reviewer.name", is("newReviewer")));



        verify(reviewerService, times(1)).createReviewer(r.getUsern(),r.getPassword(),r.getName(),r.getAffiliation(),r.getEmail());
        //verifyNoMoreInteractions(reviewerService, reviewerConverter);
    }

    @Test
    public void updateReviewer() throws Exception {

        when(reviewerService.updateReviewer(reviewer1.getId()
                ,reviewerDto1.getPassword(),reviewerDto1.getName(),reviewerDto1.getAffiliation(),reviewerDto1.getEmail(),null))
                .thenReturn(reviewer1);

        when(reviewerConverter.convertModelToDto(reviewer1)).thenReturn(reviewerDto1);

        Map<String, ReviewerDto> reviewerDtoHashMap = new HashMap<>();
        reviewerDtoHashMap.put("reviewer", reviewerDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/reviewers/{reviewerId}", reviewer1.getId(), reviewerDtoHashMap)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(reviewerDtoHashMap)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.reviewer.name", is("name2")))
                .andExpect(jsonPath("$.reviewer.usern", is("name2")));

        verify(reviewerService, times(1)).updateReviewer(reviewer1.getId()
                ,reviewerDto1.getPassword(),reviewerDto1.getName(),reviewerDto1.getAffiliation(),reviewerDto1.getEmail(),null);
        //verifyNoMoreInteractions(reviewerService, reviewerConverter);
    }



    private String toJsonString(Map<String, ReviewerDto> reviewerDtoMap) {
        try {
            return new ObjectMapper().writeValueAsString(reviewerDtoMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initData(){
        reviewer1= new Reviewer();
        reviewer1.setUsern("name2");
        reviewer1.setName("name2");
        reviewer1.setPassword("1234");
        reviewer1.setEmail("name2@name2.com");
        reviewer1.setAffiliation("affiliation2");
        reviewer1.setId(2l);
        reviewer2= new Reviewer();
        reviewer2.setUsern("name2");
        reviewer2.setName("name2");
        reviewer2.setPassword("1234");
        reviewer2.setEmail("name2@name2.com");
        reviewer2.setAffiliation("affiliation2");

        reviewer2.setId(3l);

        reviewerDto1=createReviewerDto(reviewer1);
        reviewerDto2=createReviewerDto(reviewer2);

    }


    private ReviewerDto createReviewerDto(Reviewer reviewer) {
        ReviewerDto reviewerDto=ReviewerDto.builder().usern(reviewer.getUsern())
                .name(reviewer.getName())
                .affiliation(reviewer.getAffiliation())
                .email(reviewer.getEmail())
                .password(reviewer.getPassword())
                .build();
        reviewerDto.setId(reviewer.getId());
        return reviewerDto;

    }

}
