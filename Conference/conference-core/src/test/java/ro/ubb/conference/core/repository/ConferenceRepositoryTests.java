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
@DatabaseSetup("/META-INF.dbtest/db-data4.xml")
public class ConferenceRepositoryTests {
    @Autowired
    private ConferenceRepository conferenceRepository;

    @Test
    public void findAll() throws Exception {
        List<Conference> conferences = conferenceRepository.findAll();
        assertEquals("there should be four conferences", 0, conferences.size());

    }
    @Test
    public void findOne() throws Exception{
        Conference c=new Conference("Confname2", 1,"11102017","15102017","10092017", "15092017", new HashSet<>());
        conferenceRepository.save(c);
        Long id = c.getId();
        Conference conference=(Conference) conferenceRepository.findOne(id);

        assertEquals("This conference does not exist",c, conference);

    }
    @Test
    public void createConference() throws Exception {

        Conference c=new Conference("Confname", 1,"11102017","15102017","10092017", "15092017", new HashSet<>());
        conferenceRepository.save(c);
        //nu stiu cate elemente sunt in baza de date
        assertEquals("Conference ",1l,(long)conferenceRepository.findAll().size());
        Long id = c.getId();
        conferenceRepository.delete(id);
    }

    @Test
    public void deleteAuthor() throws Exception {
        Conference c=new Conference("Confname", 1,"11102017","15102017","10092017", "15092017", new HashSet<>());
        conferenceRepository.save(c);
        Long id = c.getId();
        conferenceRepository.delete(id);
        assertEquals("Conference with id 4 should be removed",0,conferenceRepository.findAll().size());

    }


}
