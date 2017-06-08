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
import ro.ubb.conference.core.domain.Reviewer;
import ro.ubb.conference.core.domain.UserRole;

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
@DatabaseSetup("/META-INF.dbtest/db-data2.xml")
public class ReviewerRepositoryTests {
    @Autowired
    private ReviewerRepository personRepository;

    @Autowired
    private PaperRepository paperRepository;

    @Test
    public void findAll() throws Exception {
        List<Reviewer> reviewers = personRepository.findAll();
        //nu stiu cate elemente sunt in baza de date
        assertEquals("there should be four authors", 0, reviewers.size());

    }
    /*
    @Test
    public void findOne() throws Exception{

        Reviewer person1 = new Reviewer();
        person1.setUsern("andrei123");
        person1.setEmail("email@gmail.com");
        person1.setAffiliation("afiliation1");
        person1.setPassword("123456");
        person1.setName("Andrei");
        person1.setUserRole(UserRole.NORMAL);
        person1 = (Reviewer) personRepository.save(person1);

        Long id = person1.getId();
        Reviewer reviewer = personRepository.findOne(id);
        assertEquals("This reviewer does not exist",person1, reviewer);
        personRepository.delete(id);
    }

    @Test
    public void createReviewer() throws Exception {

        Reviewer person = new Reviewer();
        person.setUsern("andrei");
        person.setEmail("email@gmail.com");
        person.setAffiliation("afiliation1");
        person.setPassword("123456");
        person.setName("jonny");
        person.setUserRole(UserRole.NORMAL);
        person = (Reviewer) personRepository.save(person);

        assertEquals("Author ",1l,(long)personRepository.findAll().size());
        //Long id = person.getId();
       // personRepository.delete(id);

    }
*/
    @Test
    public void deleteAuthor() throws Exception {
        Reviewer person = new Reviewer();
        person.setUsern("uuuuu");
        person.setEmail("email@gmail.com");
        person.setAffiliation("afiliation1");
        person.setPassword("123456");
        person.setName("jonny");
        person.setUserRole(UserRole.NORMAL);
        person = (Reviewer) personRepository.save(person);

        Long id = person.getId();
        personRepository.delete(id);
        assertEquals("Author ",0,(long)personRepository.findAll().size());
    }

}
