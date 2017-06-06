package ro.ubb.conference.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Reviewer;
import ro.ubb.conference.core.domain.ReviewerPaper;
import ro.ubb.conference.core.service.ReviewerService;
import ro.ubb.conference.web.converter.ReviewerPaperConverter;
import ro.ubb.conference.web.dto.ReviewerPaperDto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by paul on 6/6/2017.
 */
public class ReviewerPaperControllerTests{
    private MockMvc mockMvc;

    @InjectMocks
    private ReviewerPaperController reviewerPaperController;

    @Mock
    private ReviewerService reviewerService;

    @Mock
    private ReviewerPaperConverter reviewerPaperConverter;

    private Reviewer reviewer1;
    private Paper paper1;
    private Paper paper2;
    private ReviewerPaper reviewerPaper1;
    private ReviewerPaper reviewerPaper2;
    private ReviewerPaperDto reviewerPaperDto1;
    private ReviewerPaperDto reviewerPaperDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(reviewerPaperController)
                .build();
        initData();
    }



    @Test
    public void getReviewerPapers() throws Exception {

        Set<ReviewerPaper> reviewerPapers = reviewer1.getReviewerPapers();
        Set<ReviewerPaperDto> reviewerPaperDtos =
                new HashSet<>(Arrays.asList(reviewerPaperDto1,reviewerPaperDto2));
        when(reviewerService.findReviewer(reviewer1.getId())).thenReturn(reviewer1);
        when(reviewerPaperConverter.convertModelsToDtos(reviewerPapers)).thenReturn(reviewerPaperDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/reviewerpapers/{reviewerId}",reviewer1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
//                .andExpect(jsonPath("$.reviewers", hasSize(2)))
//                .andExpect(jsonPath("$.reviewers[0].id", anyOf(is("1"), is("2"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        verify(reviewerService, times(1)).findReviewer(reviewer1.getId());
        verify(reviewerPaperConverter, times(1)).convertModelsToDtos(reviewerPapers);
        verifyNoMoreInteractions(reviewerService, reviewerPaperConverter);


    }

    private void initData(){
        paper1 = Paper.builder().title("name1").abstractText("abstractText1").contentPath("name1").keywords("keywords1").topics("topics1").build();
        paper1.setId((long) 1);
        reviewer1 = new Reviewer();
        reviewer1.setUsern("name2");
        reviewer1.setName("name2");
        reviewer1.setPassword("1234");
        reviewer1.setEmail("name2@name2.com");
        reviewer1.setAffiliation("affiliation2");
        reviewer1.setId((long) 1);
        reviewerPaper1= new ReviewerPaper();
        reviewerPaper1.setReviewer(reviewer1);
        reviewerPaper1.setReviewerPaper(paper1);

        paper2 = new Paper();
        paper2.setId((long) 2);
        reviewerPaper2= new ReviewerPaper();
        reviewerPaper2.setReviewer(reviewer1);
        reviewerPaper2.setReviewerPaper(paper2);

        reviewerPaperDto1=createReviewerPaperDto(reviewerPaper1);
        reviewerPaperDto2=createReviewerPaperDto(reviewerPaper2);

    }


    private ReviewerPaperDto createReviewerPaperDto(ReviewerPaper reviewerPaperPaper) {
        ReviewerPaperDto reviewerPaperDto=ReviewerPaperDto.builder().reviewerId(reviewerPaperPaper.getReviewer().getId()).paperId(reviewerPaperPaper.getReviewerPaper().getId()).build();
        return reviewerPaperDto;

    }

}
