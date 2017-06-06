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
import ro.ubb.conference.core.domain.BaseEntity;
import ro.ubb.conference.core.domain.Conference;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Session;
import ro.ubb.conference.core.service.SessionService;
import ro.ubb.conference.core.service.PaperService;
import ro.ubb.conference.web.converter.SessionConverter;
import ro.ubb.conference.web.dto.SessionDto;

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
 * Created by paul on 6/6/2017.
 */
public class SessionControllerTests {
    private MockMvc mockMvc;

    @InjectMocks
    private SessionController sessionController;

    @Mock
    private SessionService sessionService;

    @Mock
    private SessionConverter sessionConverter;

    @Mock
    private PaperService paperService;

    private Conference conference1;
    private Paper paper1,paper2;
    private Set<Paper> papers;
    private Session session1;
    private Session session2;
    private SessionDto sessionDto1;
    private SessionDto sessionDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(sessionController)
                .build();
        initData();
    }



    @Test
    public void getSessions() throws Exception {
        List<Session> sessions = Arrays.asList(session1, session2);
        Set<SessionDto> sessionDtos =
                new HashSet<>(Arrays.asList(sessionDto1,sessionDto2));
        when(sessionService.findAll()).thenReturn(sessions);
        when(sessionConverter.convertModelsToDtos(sessions)).thenReturn(sessionDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/sessions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                //.andDo(print())
                .andExpect(jsonPath("$.sessions", hasSize(2)))
                .andExpect(jsonPath("$.sessions[0].date", anyOf(is("2017-12-12"), is("2017-12-13"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        verify(sessionService, times(1)).findAll();
        verify(sessionConverter, times(1)).convertModelsToDtos(sessions);
        verifyNoMoreInteractions(sessionService, sessionConverter);


    }



    @Test
    public void deleteSession() throws Exception{
        doNothing().when(sessionService).deleteSession(session1.getId());
        ResultActions resultActions=mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/sessions/{personId}",session1.getId()))


                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));



        verify(sessionService, times(1)).deleteSession(session1.getId());
        verifyNoMoreInteractions(sessionService, sessionConverter);

    }

    @Test
    public void createSession() throws Exception{

        Session p=new Session("2017-12-15",(long)4,conference1,papers,null);
        p.setId((long) 31);
        SessionDto sessionDto=createSessionDto(p);
        when(sessionService.createSession(p.getDate(),p.getSessionChairId(),p.getConference().getId()))
                .thenReturn(p);
        when(sessionConverter.convertModelToDto(p)).thenReturn(sessionDto);
        Map<String,SessionDto> sessionDtoMap=new HashMap<>();
        sessionDtoMap.put("session",sessionDto);

        ResultActions resultActions=mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/sessions",sessionDto)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(sessionDtoMap)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.session.date", is("2017-12-15")))
                .andExpect(jsonPath("$.session.sessionChairId", is(4)));



        verify(sessionService, times(1)).createSession(p.getDate(),p.getSessionChairId(),p.getConference().getId());
    }

    @Test
    public void updateSession() throws Exception {

        when(sessionService.updateSession(session1.getId()
                ,sessionDto1.getDate(),sessionDto1.getSessionChairId(),sessionDto1.getConferenceId(),paperService.findAll(sessionDto1.getPapers()),sessionDto1.getListeners()))
                .thenReturn(session1);

        when(sessionConverter.convertModelToDto(session1)).thenReturn(sessionDto1);

        Map<String, SessionDto> sessionDtoHashMap = new HashMap<>();
        sessionDtoHashMap.put("session", sessionDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/sessions/{personId}", session1.getId(), sessionDtoHashMap)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(sessionDtoHashMap)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.session.date", is("2017-12-12")))
                .andExpect(jsonPath("$.session.sessionChairId", is(4)));

        verify(sessionService, times(1)).updateSession(session1.getId()
                ,sessionDto1.getDate(),sessionDto1.getSessionChairId(),sessionDto1.getConferenceId(),paperService.findAll(sessionDto1.getPapers()),sessionDto1.getListeners());
    }



    private String toJsonString(Map<String, SessionDto> sessionDtoMap) {
        try {
            return new ObjectMapper().writeValueAsString(sessionDtoMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initData(){
        conference1= Conference.builder().name("name1").startDate("2017-20-20").endDate("2017-20-23").edition(1).callDate("2017-20-20").papersDeadline("today").build();
        paper1= Paper.builder().title("name1").abstractText("abstractText1").contentPath("name1").keywords("keywords1").topics("topics1").build();
        paper1.setId(2l);
        paper2=Paper.builder().title("name2").abstractText("abstractText2").contentPath("name2").keywords("keywords2").topics("topics2").build();
        paper2.setId(3l);
        papers = new HashSet<>();
        papers.add(paper1);
        papers.add(paper2);
        session1= new Session("2017-12-12",(long)4,conference1,papers,null);
        session1.setId(2l);
        session2=new Session("2017-12-13",(long)2,conference1,papers,null);
        session2.setId(3l);

        sessionDto1=createSessionDto(session1);
        sessionDto2=createSessionDto(session2);

    }


    private SessionDto createSessionDto(Session session) {
        SessionDto sessionDto=SessionDto.builder().date(session.getDate())
                .sessionChairId(session.getSessionChairId())
                .papers(session.getPapers().stream()
                        .map(BaseEntity::getId).collect(Collectors.toSet()))
                .conferenceId(session.getConference().getId())
                .build();
        sessionDto.setId(session.getId());
        return sessionDto;

    }
}
