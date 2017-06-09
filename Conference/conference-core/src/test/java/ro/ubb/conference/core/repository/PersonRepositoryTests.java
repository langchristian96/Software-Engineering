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
import ro.ubb.conference.core.domain.Person;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by paul on 6/6/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/db-data.xml")
public class PersonRepositoryTests {
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void findAll() throws Exception {
        List<Person> authors = personRepository.findAll();
        //nu stiu cate elemente sunt in baza de date
        assertEquals("there should be four authors", 4, authors.size());

    }
    @Test
    public void findOne() throws Exception{
        Long personId = 4l;
        Person person=(Person) personRepository.findOne(personId);

        assertEquals("This person does not exist",personId, person.getId());
    }
    @Test
    public void createPerson() throws Exception {

        Person c=new Person("aaaaa","aaaaaaa","Adriana", "afiliation1", "email@gmail.com");
        personRepository.save(c);
        assertEquals("Author ",5l,(long)personRepository.findAll().size());

    }

    @Test
    public void deleteAuthor() throws Exception {

        personRepository.delete(4l);
        assertEquals("Person with id 4 should be removed",3,personRepository.findAll().size());

    }

    @Test
    public void getUserByUserName() throws Exception {
        String userName = "Klang";
        Person p = personRepository.getUserByUserName(userName);
        assertEquals("This person does not exist", userName, p.getUsern());
    }

}
