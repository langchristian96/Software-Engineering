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
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.service.ListenerService;
import ro.ubb.conference.web.converter.ListenerConverter;
import ro.ubb.conference.web.dto.ListenerDto;

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
public class ListenerControllerTests {
    private MockMvc mockMvc;

    @InjectMocks
    private ListenerController listenerController;

    @Mock
    private ListenerService listenerService;

    @Mock
    private ListenerConverter listenerConverter;


    private Listener listener1;
    private Listener listener2;
    private ListenerDto listenerDto1;
    private ListenerDto listenerDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(listenerController)
                .build();
        initData();
    }



    @Test
    public void getListeners() throws Exception {
        List<Listener> listeners = Arrays.asList(listener1, listener2);
        Set<ListenerDto> listenerDtos =
                new HashSet<>(Arrays.asList(listenerDto1,listenerDto2));
        when(listenerService.findAll()).thenReturn(listeners);
        when(listenerConverter.convertModelsToDtos(listeners)).thenReturn(listenerDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/listeners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.listeners", hasSize(2)))
                .andExpect(jsonPath("$.listeners[0].name", anyOf(is("name1"), is("name2"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        verify(listenerService, times(1)).findAll();
        verify(listenerConverter, times(1)).convertModelsToDtos(listeners);
        verifyNoMoreInteractions(listenerService, listenerConverter);


    }

    @Test
    public void deleteListener() throws Exception{
        doNothing().when(listenerService).deleteListener(listener1.getId());
        ResultActions resultActions=mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/listeners/{listenerId}",listener1.getId()))


                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));



        verify(listenerService, times(1)).deleteListener(listener1.getId());
        verifyNoMoreInteractions(listenerService, listenerConverter);

    }

    @Test
    public void createListener() throws Exception{
        Listener r=new Listener();
        r.setUsern("newListener");
        r.setName("newListener");
        r.setPassword("1234");
        r.setEmail("name2@name2.com");
        r.setAffiliation("affiliation2");
        r.setId(2l);
        ListenerDto listenerDto=createListenerDto(r);
        when(listenerService.createListener(r.getUsern(),r.getPassword(),r.getName(),r.getAffiliation(),r.getEmail()))
                .thenReturn(r);
        when(listenerConverter.convertModelToDto(r)).thenReturn(listenerDto);
        Map<String,ListenerDto> listenerDtoMap=new HashMap<>();
        listenerDtoMap.put("listener",listenerDto);

        ResultActions resultActions=mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/listeners",listenerDto)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(listenerDtoMap)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.listener.usern", is("newListener")))
                .andExpect(jsonPath("$.listener.name", is("newListener")));



        verify(listenerService, times(1)).createListener(r.getUsern(),r.getPassword(),r.getName(),r.getAffiliation(),r.getEmail());
        //verifyNoMoreInteractions(listenerService, listenerConverter);
    }

    @Test
    public void updateListener() throws Exception {

        when(listenerService.updateListener(listener1.getId()
                ,listenerDto1.getPassword(),listenerDto1.getName(),listenerDto1.getAffiliation(),listenerDto1.getEmail(),null))
                .thenReturn(listener1);

        when(listenerConverter.convertModelToDto(listener1)).thenReturn(listenerDto1);

        Map<String, ListenerDto> listenerDtoHashMap = new HashMap<>();
        listenerDtoHashMap.put("listener", listenerDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/listeners/{listenerId}", listener1.getId(), listenerDtoHashMap)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(listenerDtoHashMap)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.listener.name", is("name1")))
                .andExpect(jsonPath("$.listener.usern", is("name1")));

        verify(listenerService, times(1)).updateListener(listener1.getId()
                ,listenerDto1.getPassword(),listenerDto1.getName(),listenerDto1.getAffiliation(),listenerDto1.getEmail(),null);
        //verifyNoMoreInteractions(listenerService, listenerConverter);
    }



    private String toJsonString(Map<String, ListenerDto> listenerDtoMap) {
        try {
            return new ObjectMapper().writeValueAsString(listenerDtoMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initData(){
        listener1= new Listener();
        listener1.setUsern("name1");
        listener1.setName("name1");
        listener1.setPassword("1234");
        listener1.setEmail("name1@name1.com");
        listener1.setAffiliation("affiliation1");
        listener1.setId(2l);
        listener2= new Listener();
        listener2.setUsern("name2");
        listener2.setName("name2");
        listener2.setPassword("1234");
        listener2.setEmail("name2@name2.com");
        listener2.setAffiliation("affiliation2");

        listener2.setId(3l);

        listenerDto1=createListenerDto(listener1);
        listenerDto2=createListenerDto(listener2);

    }


    private ListenerDto createListenerDto(Listener listener) {
        ListenerDto listenerDto=ListenerDto.builder().usern(listener.getUsern())
                .name(listener.getName())
                .affiliation(listener.getAffiliation())
                .email(listener.getEmail())
                .password(listener.getPassword())
                .build();
        listenerDto.setId(listener.getId());
        return listenerDto;

    }
}
