package ro.ubb.conference.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.ubb.conference.core.domain.Author;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by paul on 5/19/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration()
public class AuthorServiceTest {

    @Autowired
    private AuthorServiceImpl authorService;

    @Test
    public void TestService() throws Exception{
        authorService.createAuthor("Yass","1234","Messi","Master","messi@master.com");
        List<Author> authors =authorService.findAll();
        assertEquals(1,authors.size());

    }

}
