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
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.service.PaperService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/paper-data.xml")
public class PaperServiceTest {

    @Autowired
    private PaperService paperService;

    @Test
    public void findAll() throws Exception {
        List<Paper> papers = paperService.findAll();
        assertEquals("there should be four papers", 4, papers.size());
    }


    @Test
    public void updatePaper() throws Exception {


        Paper p=paperService.findAll().get(0);
        p.setTitle("testPaper");
        paperService.updatePaper(p.getId(), p.getTitle(), p.getAbstractText(), p.getContentPath(), p.getKeywords(), p.getTopics(), p.getAuthors(), p.getSession());
        assertEquals("Paper should be updated","testPaper",paperService.findAll().get(0).getTitle());

    }

    @Test
    public void createPaper() throws Exception {

        paperService.createPaper("Teoria punctelor fixe", "This is a random text", "random", "puncte", "mathematics" );
        assertEquals("Paper should be added ",5l,(long)paperService.findAll().size());

    }


    @Test
    public void deletePaper() throws Exception {

        paperService.deletePaper(4l);
        assertEquals("Paper should be deleted ",3,paperService.findAll().size());

    }

}