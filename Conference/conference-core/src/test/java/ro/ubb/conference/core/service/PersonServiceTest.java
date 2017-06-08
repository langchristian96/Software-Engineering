package ro.ubb.conference.core.server;



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
import ro.ubb.conference.core.domain.Person;
import ro.ubb.conference.core.service.PersonService;

import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/person-data.xml")
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    public void findAll() throws Exception {
        List<Person> persons = personService.findAll();
        assertEquals("there should be six persons", 6, persons.size());
    }


    @Test
    public void updatePerson() throws Exception {

        Person p=personService.getUserByUserName("john");
        p.setName("Johnson");
        personService.updatePerson(p.getId(),p.getPassword(),p.getName(),p.getAffiliation(), p.getEmail());

        p=personService.getUserByUserName("john");
        assertEquals("Client should change his name to Johnson","Johnson",p.getName());

    }

    @Test
    public void createPerson() throws Exception {

        personService.createPerson("dark_lord","pass10", "David Bowie", "corpo", "dave_bowie@gmail.com");
        assertEquals("Person ",7l,(long)personService.findAll().size());

    }


    @Test
    public void deletePerson() throws Exception {

        personService.deletePerson(-1l);
        assertEquals("Person with id 4 should be removed",5,personService.findAll().size());

    }

}