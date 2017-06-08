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
import ro.ubb.conference.core.domain.Reviewer;
import ro.ubb.conference.core.service.ReviewerService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/reviewer-data.xml")
public class ReviewerServiceTest {

    @Autowired
    private ReviewerService reviewerService;

    @Test
    public void findAll() throws Exception {
        List<Reviewer> reviewers = reviewerService.findAll();
        assertEquals("there should be one reviewer", 0, reviewers.size());
    }


    @Test
    public void updateReviewer() throws Exception {

        reviewerService.createReviewer("Critique","0000", "Michael Jackson", "corpo", "mickie@gmail.com");
        Reviewer r = reviewerService.findAll().get(0);
        r.setName("testName");
        Set<Long> set = new HashSet<>();
        reviewerService.updateReviewer(r.getId(),r.getPassword(), r.getName(), r.getAffiliation(),r.getEmail(), set);
        assertEquals("Reviewer should be updated","testName",reviewerService.findAll().get(0).getName());
        Long id = reviewerService.findAll().get(0).getId();
        reviewerService.deleteReviewer(id);

    }

    @Test
    public void createReviewer() throws Exception {

        reviewerService.createReviewer("Critique","0000", "Michael Jackson", "corpo", "mickie@gmail.com");
        assertEquals("Reviewer ",1l,(long)reviewerService.findAll().size());
        Long id = reviewerService.findAll().get(0).getId();
        reviewerService.deleteReviewer(id);

    }


    @Test
    public void deleteReviewer() throws Exception {

        reviewerService.createReviewer("Critique","0000", "Michael Jackson", "corpo", "mickie@gmail.com");
        Long id = reviewerService.findAll().get(0).getId();
        reviewerService.deleteReviewer(id);
        assertEquals("Reviewer with id 5 should be removed",0,reviewerService.findAll().size());

    }

}