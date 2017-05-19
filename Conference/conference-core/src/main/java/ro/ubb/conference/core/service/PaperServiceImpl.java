package ro.ubb.conference.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Reviewer;
import ro.ubb.conference.core.domain.Session;
import ro.ubb.conference.core.repository.PaperRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by anca.
 */
@Service
public class PaperServiceImpl implements PaperService {

    private static final Logger log = LoggerFactory.getLogger(PaperServiceImpl.class);

    @Autowired
    private PaperRepository paperRepository;

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
    @Transactional
    public Paper updatePaper(Long paperId, String title, String content, Set<Long> authors, Set<Long> reviewers, Session sessionId ) {
        log.trace("updatePaper: paperId={}, title={}, author={}, content={}",
                paperId, title, authors, content);

        Paper paper = (Paper) paperRepository.findOne(paperId);
        paper.setTitle(title);
        paper.setContent(content);
        paper.setSession(sessionId);

        paper.getAuthors().stream()
                .map(d->d.getId())
                .forEach(i->
                {
                    if(authors.contains(i)){
                        authors.remove(i);
                    }
                });
        List<Author> authorList=paperRepository.findAll(authors);
        authorList.forEach(paper::addAuthor);

        paper.getReviewers().stream()
                .map(d->d.getId())
                .forEach(i->
                {
                    if(reviewers.contains(i)){
                        reviewers.remove(i);
                    }
                });
        List<Reviewer> reviewerList=paperRepository.findAll(reviewers);
        reviewerList.forEach(paper::addReviewer);

        log.trace("updatePaper: paper={}", paper);

        return paper;
    }

    @Override
    public Paper createPaper(String title, String content) {
        log.trace("createPaper: title={}, author={}, content={}",
               title, content);

        Paper paper = new Paper(title, content);
        paper = (Paper) paperRepository.save(paper);

        log.trace("createPaper: paper={}", paper);

        return paper;
    }

    @Override
    public void deletePaper(Long paperId) {
        log.trace("deletePaper: paperId={}", paperId);

        paperRepository.delete(paperId);

        log.trace("deletePaper - method end");
    }


}
