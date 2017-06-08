package ro.ubb.conference.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Reviewer;
import ro.ubb.conference.core.domain.UserRole;
import ro.ubb.conference.core.repository.AuthorRepository;
import ro.ubb.conference.core.repository.PaperRepository;
import ro.ubb.conference.core.repository.ReviewerRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by langchristian96 on 5/18/2017.
 */




@Service
public class ReviewerServiceImpl implements ReviewerService {

    private static final Logger log = LoggerFactory.getLogger(ReviewerServiceImpl.class);

    @Autowired
    private ReviewerRepository personRepository;

    @Autowired
    private PaperRepository paperRepository;

    @Override
    public List<Reviewer> findAll() {
        log.trace("findAll");

        List<Reviewer> persons = personRepository.findAll();

        log.trace("findAll: Authors={}", persons);

        return persons;
    }

    @Override
    public Set<Reviewer> findAllReviewersByUsernames(Set<String> reviewersUsername) {
        log.trace("findAllReviewersByUsernames");

        List<Reviewer> reviewers = personRepository.findAll();

        Set<Reviewer> reviewerSet = reviewers.stream()
                .filter(e -> reviewersUsername.contains(e.getUsern()))
                .collect(Collectors.toSet());

        log.trace("findAllReviewersByUsernames: Authors={}", reviewerSet);

        return reviewerSet;
    }

    @Override
    public Reviewer findReviewer(Long reviewerId){
        log.trace("findReviewer: ReviewerId={}",reviewerId);

        Reviewer reviewer=(Reviewer) personRepository.findOne(reviewerId);

        log.trace("findReviewer: ReviewerId={}",reviewer);
        return reviewer;
    }

    @Override
    @Transactional
    public Reviewer updateReviewerGrade(Long personId, Long paperId, int grade) {
        log.trace("updateReviewerGrade: personId={}, paperId={}, grade={}",
                personId, paperId, grade);

//        Reviewer person = (Reviewer) personRepository.findOne(personId);
//        Paper paper = (Paper) paperRepository.findOne(paperId);
//        person.updatePaper(paper, grade);
//        paper.updateReviewer(person, grade);
        Reviewer person = (Reviewer) paperRepository.updateReviewerGrade(personId, paperId, grade);

//        person.getPapers().stream()
//                .map(d->d.getId())
//                .forEach(i->
//                {
//                    if(papers.contains(i)){
//                        papers.remove(i);
//                    }
//                });
//
//        List<Paper> paperList = paperRepository.findAll(papers);
//        paperList.forEach(person::addPaper);

        log.trace("updateReviewerGrade: Reviewer={}", person);

        return person;
    }

    @Override
    @Transactional
    public Reviewer updateReviewer(Long personId, String password, String name, String affiliation, String email, Set<Long> papers) {
        log.trace("updateAuthor: personId={}, password={}, name={}, affiliation={}, email={}",
                personId, password, name, affiliation, email);

        Reviewer person = (Reviewer) personRepository.findOne(personId);
        person.setPassword(password);
        person.setName(name);
        person.setAffiliation(affiliation);
        person.setEmail(email);

//        List<Paper> paperList = paperRepository.findAll(papers.stream().map(paper -> paper.getId()).collect(Collectors.toSet()));
//        paperList.forEach(paper -> person.addPaper(paper));

//        person.getPapers().stream()
//                .map(d->d.getId())
//                .forEach(i->
//                {
//                    if(papers.contains(i)){
//                        papers.remove(i);
//                    }
//                });
//
//        List<Paper> paperList = paperRepository.findAll(papers);
//        paperList.forEach(person::addPaper);

        log.trace("updateReviewer: Reviewer={}", person);

        return person;
    }

    @Override
    public Reviewer createReviewer(String user, String password, String name, String affiliation, String email) {
        log.trace("user={}, password={}, name={}, affiliation={}, email={}",
                user, password, name, affiliation, email);

        Reviewer person = new Reviewer();
        person.setUsern(user);
        person.setEmail(email);
        person.setAffiliation(affiliation);
        person.setPassword(password);
        person.setName(name);
        person.setUserRole(UserRole.NORMAL);

        person = (Reviewer) personRepository.save(person);

        log.trace("createReviewer: Reviewer={}", person);

        return person;
    }

    @Override
    public void deleteReviewer(Long personId) {
        log.trace("deleteReviewer: personId={}", personId);

        personRepository.delete(personId);

        log.trace("deleteReviewer - method end");
    }


}