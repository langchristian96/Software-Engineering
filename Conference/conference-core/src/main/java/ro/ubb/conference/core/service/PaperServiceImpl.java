package ro.ubb.conference.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.*;
import ro.ubb.conference.core.repository.AuthorRepository;
import ro.ubb.conference.core.repository.PaperRepository;
import ro.ubb.conference.core.repository.ReviewerRepository;
import ro.ubb.conference.core.repository.SessionRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by anca.
 */
@Service
public class PaperServiceImpl implements PaperService {

    private static final Logger log = LoggerFactory.getLogger(PaperServiceImpl.class);

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public List<Paper> findAll() {
        log.trace("findAll");

        List<Paper> papers = paperRepository.findAll();

        log.trace("findAll: papers={}", papers);

        return papers;
    }

    @Override
    public Set<Paper> findAll(Set<Long> papers) {
        log.trace("findAll");

        Set<Paper> paperSet = (Set<Paper>) paperRepository.findAll(papers);

        log.trace("findAll: papers={}", paperSet);

        return paperSet;
    }

    @Override
    public Paper findPaper(Long paperId){
        log.trace("findReviewer: paperId={}",paperId);

        Paper paper = (Paper) paperRepository.findOne(paperId);

        log.trace("findReviewer: paperId={}",paper);
        return paper;
    }

    @Override
    public Set<Paper> findAllPapersOfReviewer(Long reviewerId){
        log.trace("findAllPapersOfReviewer");

        List<Paper> papers = paperRepository.findAll();

        Reviewer reviewer = reviewerRepository.findOne(reviewerId);

        Set<Paper> paperSet = papers.stream()
                .filter(e -> e.getReviewers().contains(reviewer))
                .collect(Collectors.toSet());

        log.trace("findAllPapersOfReviewer: Papers={}", paperSet);

        return paperSet;
    }


    @Override
    public Set<Paper> findAllPapersOfAuthor(Long authorId){
        log.trace("findAllPapersOfAuthor");

        List<Paper> papers = paperRepository.findAll();

        Author author = authorRepository.findOne(authorId);

        Set<Paper> paperSet = papers.stream()
                .filter(e -> e.getAuthors().contains(author))
                .collect(Collectors.toSet());

        log.trace("findAllPapersOfAuthor: Papers={}", paperSet);

        return paperSet;
    }




    @Override
    public Set<Paper> findAllPapersOfChair(Long chairId){
        log.trace("findAllPapersOfChair");

        List<Paper> papers = paperRepository.findAll();



        Set<Paper> paperSet = papers.stream()
                .filter(e -> {Conference c=e.getConference();if(c==null)return false;return c.getChairID()==chairId;})
                .collect(Collectors.toSet());

        log.trace("findAllPapersOfChair: Papers={}", paperSet);

        return paperSet;
    }

    @Override
    public Set<Paper> findAllPapersByTitle(Set<String> papersTitle) {
        log.trace("findAllPapersByTitle");

        List<Paper> papers = paperRepository.findAll();

        Set<Paper> paperSet = papers.stream()
                .filter(e -> papersTitle.contains(e.getTitle()))
                .collect(Collectors.toSet());

        log.trace("findAllPapersByTitle: Papers={}", paperSet);

        return paperSet;
    }

    @Override
    @Transactional
    public Paper updatePaper(Long paperId, String title, String abstractText, String contentPath, String keywords, String topics, Set<Author> authors, Set<Reviewer> reviewers, Long sessionId) {
        log.trace("updatePaper: paperId={}, title={}, author={}, contentPath={}",
                paperId, title, keywords, topics, authors, contentPath);

        Session session = (Session) sessionRepository.findOne(sessionId);
        Paper paper = (Paper) paperRepository.findOne(paperId);
        paper.setTitle(title);
        paper.setAbstractText(abstractText);
        paper.setContentPath(contentPath);
        paper.setKeywords(keywords);
        paper.setTopics(topics);
        paper.setSession(session);
        List<Author> authorList = authorRepository.findAll(authors.stream().map(author -> author.getId()).collect(Collectors.toSet()));
        authorList.forEach(author -> paper.addAuthor(author));

        List<Reviewer> reviewerList = reviewerRepository.findAll(reviewers.stream().map(reviewer -> reviewer.getId()).collect(Collectors.toSet()));
        reviewerList.forEach(reviewer -> paper.addReviewer(reviewer));
//        paper.getReviewers().stream()
//                .map(d->d.getId())
//                .forEach(i->
//                {
//                    if(reviewers.contains(i)){
//                        reviewers.remove(i);
//                    }
//                });
//        List<Reviewer> reviewerList=paperRepository.findAll(reviewers);
//        reviewerList.forEach(paper::addReviewer);

        log.trace("updatePaper: paper={}", paper);

        return paper;
    }

    @Override
    public Paper createPaper(String title, String abstractText,String contentPath, String keywords, String topics, Conference conference) {
        log.trace("createPaper: title={}, author={}, contentPath={}",
               title, contentPath, keywords, topics);
        Paper paper = new Paper(title, abstractText,contentPath, keywords, topics);
        paper.setConference(conference);
        conference.addPaper(paper);
        paper = (Paper) paperRepository.save(paper);
        log.trace("createPaper: paper={}", paper);

        return paper;
    }

    @Override
    @Transactional
    public Paper updatePaperAuthors(Long paperId, Set<Author> authors){
        Paper paper = (Paper) paperRepository.findOne(paperId);
        List<Author> authorList = authorRepository.findAll(authors.stream().map(author -> author.getId()).collect(Collectors.toSet()));
        authorList.forEach(author -> paper.addAuthor(author));
        return paper;
    }

    @Override
    public void deletePaper(Long paperId) {
        log.trace("deletePaper: paperId={}", paperId);

        paperRepository.delete(paperId);

        log.trace("deletePaper - method end");
    }


}
