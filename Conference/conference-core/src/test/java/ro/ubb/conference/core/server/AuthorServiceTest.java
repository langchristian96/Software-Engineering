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
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.service.AuthorService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/author-data.xml")
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Test
    public void findAll() throws Exception {
        List<Author> authors = authorService.findAll();
        assertEquals("there should be one author", 0, authors.size());
    }


    @Test
    public void updateAuthor() throws Exception {

        authorService.createAuthor("Author1","pass","David Bowie","corporatie", "authoristic@gmail.com");
        Author a=authorService.findAll().get(0);
        a.setName("testAuthor");
        Set<Paper> set=new HashSet<>();
        authorService.updateAuthor(a.getId(),a.getPassword(), a.getName(), a.getAffiliation(),a.getEmail(), set);
        assertEquals("Author should be updated","testAuthor",authorService.findAll().get(0).getName());
        Long id = authorService.findAll().get(0).getId();
        authorService.deleteAuthor(id);

    }

    @Test
    public void createAuthor() throws Exception {

        authorService.createAuthor("Author1","pass","David Bowie","corporatie", "authoristic@gmail.com");
        assertEquals("Author should be added ",1l,(long)authorService.findAll().size());
        Long id = authorService.findAll().get(0).getId();
        authorService.deleteAuthor(id);

    }


    @Test
    public void deleteAuthor() throws Exception {

        authorService.createAuthor("Author1","pass","David Bowie","corporatie", "authoristic@gmail.com");
        Long id = authorService.findAll().get(0).getId();
        authorService.deleteAuthor(id);
        assertEquals("Author should be deleted ",0,authorService.findAll().size());

    }

}