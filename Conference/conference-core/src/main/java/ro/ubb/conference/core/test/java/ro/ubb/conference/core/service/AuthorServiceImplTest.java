package ro.ubb.conference.core.service;

/**
 * Created by paul on 5/20/2017.
 */

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.repository.AuthorRepository;



@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceImplTest {
    @Mock
    private static AuthorRepository authorRepository;

    @InjectMocks
    private static AuthorService authorService= new AuthorServiceImpl();

    private Author author1, author2;

    @Test
    public void TestService(){
        Long authorID= new Long(1);
        author1 = new Author();
        author2 = new Author();
        author1.setId(authorID);
        author1.setName("AuthorName");
        author1.setEmail("Author");
        Mockito.when(authorService.createAuthor("Yass","1234","Messi","Master","messi@master.com")).thenReturn(author1);
        Mockito.when(authorRepository.findOne(authorID)).thenReturn(author2);
        Assert.assertEquals(author1.getEmail(),"Author");
    }
}
