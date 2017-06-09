package ro.ubb.conference.core.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ro.ubb.conference.core.ITConfig;
import ro.ubb.conference.core.domain.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/session-data.xml")
public class SessionServiceTest {

    @Autowired
    private SessionService sessionService;

    @Test
    public void findAll() throws Exception {
        List<Session> sessions = sessionService.findAll();
        assertEquals("there should be one session", 1, sessions.size());
    }

    @Test

    public void deleteSession() throws Exception {

        sessionService.deleteSession(-11l);
        assertEquals("Session should be deleted ",1,sessionService.findAll().size());


    }
    @Test
    public void updateSession() throws Exception {

        /*
        Session s=sessionService.findAll().get(0);
        s.setDate("dateSession");
        Set<Long> lis = new HashSet<>();
        sessionService.updateSession(s.getId(), s.getDate(), s.getSessionChairId(), s.getConference().getId(), s.getPapers(), lis);
        assertEquals("Session should be updated","dateSession",sessionService.findAll().get(0).getDate());
        */
    }

    @Test
    public void createSession() throws Exception {
        /*
        sessionService.createSession("10-10-2010", 3l, 11l);
        assertEquals("Session should be added ",2l,(long)sessionService.findAll().size());
        */
    }







}