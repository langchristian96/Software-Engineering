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
import ro.ubb.conference.core.domain.Conference;
import ro.ubb.conference.core.service.ConferenceService;
import ro.ubb.conference.core.service.SessionService;
import ro.ubb.conference.web.converter.ConferenceConverter;
import ro.ubb.conference.web.dto.ConferenceDto;

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
public class ConferenceControllerTests {
    private MockMvc mockMvc;

    @InjectMocks
    private ConferenceController conferenceController;

    @Mock
    private ConferenceService conferenceService;

    @Mock
    private ConferenceConverter conferenceConverter;

    @Mock
    private SessionService sesionService;

    private Conference conference1;
    private Conference conference2;
    private ConferenceDto conferenceDto1;
    private ConferenceDto conferenceDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(conferenceController)
                .build();
        initData();
    }



    @Test
    public void getConferences() throws Exception {
        List<Conference> conferences = Arrays.asList(conference1, conference2);
        Set<ConferenceDto> conferenceDtos =
                new HashSet<>(Arrays.asList(conferenceDto1,conferenceDto2));
        when(conferenceService.findAll()).thenReturn(conferences);
        when(conferenceConverter.convertModelsToDtos(conferences)).thenReturn(conferenceDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/conferences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.conferences", hasSize(2)))
                .andExpect(jsonPath("$.conferences[0].name", anyOf(is("name1"), is("name2"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        verify(conferenceService, times(1)).findAll();
        verify(conferenceConverter, times(1)).convertModelsToDtos(conferences);
        verifyNoMoreInteractions(conferenceService, conferenceConverter);


    }

    @Test
    public void deleteConference() throws Exception{
        doNothing().when(conferenceService).deleteConference(conference1.getId());
        ResultActions resultActions=mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/conferences/{conferenceId}",conference1.getId()))


                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));



        verify(conferenceService, times(1)).deleteConference(conference1.getId());
        verifyNoMoreInteractions(conferenceService, conferenceConverter);

    }

    @Test
    public void createConference() throws Exception{
        Set<String> authors = new HashSet<>();
        authors.add("a1");

        Conference c= Conference.builder().name("newConference").startDate("2017-20-20").endDate("2017-20-23").edition(1).callDate("2017-20-20").papersDeadline("today").build();
        c.setId((long) 31);
        ConferenceDto conferenceDto= createConferenceDto(c);
        when(conferenceService.createConference(c.getName(),c.getEdition(),c.getStartDate(),c.getEndDate(),c.getCallDate(),c.getPapersDeadline())).thenReturn(c);
        when(conferenceConverter.convertModelToDto(c)).thenReturn(conferenceDto);
        Map<String,ConferenceDto> conferenceDtoMap=new HashMap<>();
        conferenceDtoMap.put("conference",conferenceDto);

        ResultActions resultActions=mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/conferences",conferenceDto)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(conferenceDtoMap)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.conference.name", is("newConference")))
                .andExpect(jsonPath("$.conference.callDate", is("2017-20-20")));



        verify(conferenceService, times(1)).createConference(c.getName(),c.getEdition(),c.getStartDate(),c.getEndDate(),c.getCallDate(),c.getPapersDeadline());

    }

    @Test
    public void updateConference() throws Exception {

        when(conferenceService.updateConference(conference1.getId()
                ,conferenceDto1.getName(),conferenceDto1.getEdition(),conferenceDto1.getStartDate(),conferenceDto1.getEndDate(),conferenceDto1.getCallDate(),conferenceDto1.getPapersDeadline(),sesionService.findAll(conferenceDto1.getSessions())))
                .thenReturn(conference1);

        when(conferenceConverter.convertModelToDto(conference1)).thenReturn(conferenceDto1);

        Map<String, ConferenceDto> conferenceDtoHashMap = new HashMap<>();
        conferenceDtoHashMap.put("conference", conferenceDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/conferences/{conferenceId}", conference1.getId(), conferenceDtoHashMap)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(conferenceDtoHashMap)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.conference.name", is("name1")))
                .andExpect(jsonPath("$.conference.startDate", is("2017-20-20")));

        verify(conferenceService, times(1)).updateConference(conference1.getId()
                ,conferenceDto1.getName(),conferenceDto1.getEdition(),conferenceDto1.getStartDate(),conferenceDto1.getEndDate(),conferenceDto1.getCallDate(),conferenceDto1.getPapersDeadline(),sesionService.findAll(conferenceDto1.getSessions()));
    }



    private String toJsonString(Map<String, ConferenceDto> conferenceDtoMap) {
        try {
            return new ObjectMapper().writeValueAsString(conferenceDtoMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initData(){

        conference1=Conference.builder().name("name1").startDate("2017-20-20").endDate("2017-20-23").edition(1).callDate("2017-20-20").papersDeadline("today").build();
        conference1.setId(2l);
        conference2=Conference.builder().name("name2").startDate("2017-20-20").endDate("2017-20-23").edition(1).callDate("2017-20-20").papersDeadline("today").build();
        conference2.setId(3l);

        conferenceDto1=createConferenceDto(conference1);
        conferenceDto2=createConferenceDto(conference2);

    }
    private ConferenceDto createConferenceDto(Conference conference) {
        ConferenceDto conferenceDto;
        Set<Long> sessions = new HashSet<>();
        sessions.add((long)1);

        conferenceDto = ConferenceDto.builder()
                .name(conference.getName())
                .startDate(conference.getStartDate())
                .endDate(conference.getEndDate())
                .edition(conference.getEdition())
                .sessions(sessions)
                .callDate(conference.getCallDate())
                .papersDeadline(conference.getPapersDeadline())
                .build();

        return conferenceDto;

    }
}
