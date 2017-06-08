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
import ro.ubb.conference.core.domain.Paper;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adriana on 6/6/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/db-data2.xml")
public class PaperRepositoryTests {
    @Autowired
    private PaperRepository paperRepository;

    @Test
    public void findAll() throws Exception {
        List<Paper> papers = paperRepository.findAll();
        //nu stiu cate elemente sunt in baza de date
        assertEquals("there should be four papers", 4, papers.size());
    }

    @Test
    public void createPaper() throws Exception {

        Paper c=new Paper("Titlepaper","aaaaaaa","aaaaaaa", "aaaaa", "aaaaa");
        paperRepository.save(c);
        //nu stiu cate elemente sunt in baza de date
        assertEquals("Paper ",5l,(long)paperRepository.findAll().size());

    }

    @Test
    public void deletePaper() throws Exception {

        paperRepository.delete(4l);
        assertEquals("Paper with id 4 should be removed",3,paperRepository.findAll().size());

    }

    @Test
    public void findOne() throws Exception{
        Long paperId = 4l;
        Paper paper=(Paper) paperRepository.findOne(paperId);

        assertEquals("This paper does not exist",paperId, paper.getId());
    }
}
