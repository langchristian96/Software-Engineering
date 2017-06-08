package ro.ubb.conference.core.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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
import ro.ubb.conference.core.domain.Conference;
import ro.ubb.conference.core.domain.Session;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adriana on 6/6/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/db-data.xml")
public class SessionRepositoryTests {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private ListenerRepository listenerRepository;
    @Test
    public void findAll() throws Exception {
        List<Session> sessions = sessionRepository.findAll();
        //nu stiu cate elemente sunt in baza de date
        assertEquals("there should be four sessions", 0, sessions.size());
    }

    @Test
    public void createSession() throws Exception {
        Conference c=new Conference("Confname2", 1,"11102017","15102017","10092017", "15092017", new HashSet<>());
        conferenceRepository.save(c);
        Long conferenceId = c.getId();
        Session s=new Session("20062017",4l,(Conference) conferenceRepository.findOne(conferenceId), new HashSet<>(), new HashSet<>());
        sessionRepository.save(s);
        Long sId =s.getId();
        //nu stiu cate elemente sunt in baza de date
        assertEquals("Session ",1,(long)sessionRepository.findAll().size());
        sessionRepository.delete(sId);
        conferenceRepository.delete(conferenceId);
    }

    @Test
    public void deleteSession() throws Exception {
        Conference c=new Conference("Confname2", 1,"11102017","15102017","10092017", "15092017", new HashSet<>());
        conferenceRepository.save(c);
        Long conferenceId = c.getId();
        Session s=new Session("20062017",4l,(Conference) conferenceRepository.findOne(conferenceId), new HashSet<>(), new HashSet<>());
        sessionRepository.save(s);
        Long sessionId = s.getId();
        sessionRepository.delete(sessionId);
        conferenceRepository.delete(conferenceId);
        assertEquals("Session with id 4 should be removed",0,sessionRepository.findAll().size());

    }

    @Test
    public void findOne() throws Exception{
        Conference c=new Conference("Confname2", 1,"11102017","15102017","10092017", "15092017", new HashSet<>());
        conferenceRepository.save(c);
        Long conferenceId = c.getId();
        Session s=new Session("20062017",4l,(Conference) conferenceRepository.findOne(conferenceId), new HashSet<>(), new HashSet<>());
        sessionRepository.save(s);
        Long sessionId = s.getId();

        Session session=(Session) sessionRepository.findOne(sessionId);

        assertEquals("This session does not exist",session, s);
        sessionRepository.delete(sessionId);
        conferenceRepository.delete(conferenceId);
    }

}
