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
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.domain.Person;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adriana on 6/6/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/db-data5.xml")
public class ListenerRepositoryTests {
    @Autowired
    private ListenerRepository listenerRepository;

    @Test
    public void findAll() throws Exception {
        List<Listener> listeners = listenerRepository.findAll();
        assertEquals("there should be four listeners", 0, listeners.size());

    }
    @Test
    public void findOne() throws Exception{
        Listener c=new Listener("bbbbbbbb","aaaaaaa","Adriana", "afiliation1", "email@gmail.com");
        listenerRepository.save(c);
        Long listenerId = c.getId();
        Listener listener=(Listener) listenerRepository.findOne(listenerId);

        assertEquals("This listener does not exist",listener, c);
        listenerRepository.delete(listenerId);
    }

    @Test
    public void createListener() throws Exception {

        Listener c=new Listener("aaaaa","aaaaaaa","Adriana", "afiliation1", "email@gmail.com");

        //System.out.println(c+ "!!!!!!!!!!!!!!!!!!!!!!!!");
        //listenerRepository.save( c);
        assertEquals("Author ",0,(long)listenerRepository.findAll().size());
        Long listenerId = c.getId();
        //System.out.println(c + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //listenerRepository.delete(listenerId);
    }

    @Test
    public void deleteListener() throws Exception {
        Listener c=new Listener("ccccccc","aaaaaaa","Adriana", "afiliation1", "email@gmail.com");
        listenerRepository.save(c);
        Long id = c.getId();
        listenerRepository.delete(id);
        assertEquals("Listener should be removed",0,listenerRepository.findAll().size());

    }


}
