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
import ro.ubb.conference.core.domain.Listener;
import ro.ubb.conference.core.service.ListenerService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/listener-data.xml")
public class ListenerServiceTest {

    @Autowired
    private ListenerService listenerService;

    @Test
    public void findAll() throws Exception {
        List<Listener> listeners = listenerService.findAll();
        assertEquals("there should be one listener", 0, listeners.size());
    }


    @Test
    public void updateListener() throws Exception {

        listenerService.createListener("listenerUsername", "password", "Listenerul", "corporatie", "listener@gamil.com");
        Listener l=listenerService.findAll().get(0);
        l.setName("testListener");
        Set<Long> set=new HashSet<>();
        listenerService.updateListener(l.getId(), l.getPassword(), l.getName(), l.getAffiliation(), l.getEmail(), set);
        assertEquals("Listener should be updated","testListener",listenerService.findAll().get(0).getName());
        Long id = listenerService.findAll().get(0).getId();
        listenerService.deleteListener(id);

    }

    @Test
    public void createListener() throws Exception {

        listenerService.createListener("listenerUsername", "password", "Listenerul", "corporatie", "listener@gamil.com");
        assertEquals("Listener should be added ",1l,(long)listenerService.findAll().size());
        Long id = listenerService.findAll().get(0).getId();
        listenerService.deleteListener(id);

    }


    @Test
    public void deleteListener() throws Exception {

        listenerService.createListener("listenerUsername", "password", "Listenerul", "corporatie", "listener@gamil.com");
        Long id = listenerService.findAll().get(0).getId();
        listenerService.deleteListener(id);
        assertEquals("Listener should be deleted ",0,listenerService.findAll().size());

    }

}