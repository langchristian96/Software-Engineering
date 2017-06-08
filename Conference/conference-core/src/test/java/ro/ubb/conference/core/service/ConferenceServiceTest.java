package ro.ubb.conference.core.service;

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
import ro.ubb.conference.core.service.ConferenceService;

import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/conference-data.xml")
public class ConferenceServiceTest {

    @Autowired
    private ConferenceService conferenceService;

    @Test
    public void findAll() throws Exception {
        List<Conference> conferences = conferenceService.findAll();
        assertEquals("there should be four conferences", 4, conferences.size());
    }


    @Test
    public void updateConference() throws Exception {


        Conference c=conferenceService.findAll().get(0);
        c.setName("testConference");
        conferenceService.updateConference(c.getId(), c.getName(), c.getEdition(), c.getStartDate(), c.getEndDate(), c.getCallDate(), c.getPapersDeadline(), c.getSessions());
        assertEquals("Conference should be updated","testConference",conferenceService.findAll().get(0).getName());

    }

    @Test
    public void createConference() throws Exception {

        conferenceService.createConference("Conference1",1,"10-12-2017","12-12-2017", "11-12-2017", "01-12-2017");
        assertEquals("Conference should be added ",5l,(long)conferenceService.findAll().size());

    }


    @Test
    public void deleteConference() throws Exception {

        conferenceService.deleteConference(-4l);
        assertEquals("Conference should be deleted ",3,conferenceService.findAll().size());

    }

}