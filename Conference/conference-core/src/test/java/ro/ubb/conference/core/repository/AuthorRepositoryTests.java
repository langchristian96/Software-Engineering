package ro.ubb.conference.core.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.http.auth.AUTH;
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
import ro.ubb.conference.core.domain.Author;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by paul on 6/6/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/db-data3.xml")
public class AuthorRepositoryTests {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PaperRepository paperRepository;

    @Test
    public void findAll() throws Exception {
        List<Author> persons = authorRepository.findAll();
        assertEquals("there should be four authors", 0, persons.size());
    }

    @Test
    public void createAuthor() throws Exception {

        Author c=new Author("aaaaa","aaaaaaa","Adriana", "afiliation1", "email@gmail.com");
        authorRepository.save(c);
        //nu stiu cate elemente sunt in baza de date
        assertEquals("Author ",1,(long)authorRepository.findAll().size());
        //authorRepository.delete(1l);
    }

    @Test
    public void deleteAuthor() throws Exception {
        Author c=new Author("aaaaa","aaaaaaa","Adriana", "afiliation1", "email@gmail.com");
        authorRepository.save(c);
        authorRepository.delete(1l);
        assertEquals("Author with id 1 should be removed",0,authorRepository.findAll().size());

    }

    @Test
    public void findOne() throws Exception{
        Author c=new Author("bbbbbb","aaaaaaa","Adriana", "afiliation1", "email@gmail.com");
        authorRepository.save(c);
        Author author=(Author) authorRepository.findOne(2l);
        Long authorId = 1l;
        assertEquals("This author does not exist",c, author);
        authorRepository.delete(2l);
    }

}
