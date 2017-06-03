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
import ro.ubb.conference.core.repository.AuthorRepository;
import ro.ubb.conference.core.repository.PaperRepository;

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
    public Paper updatePaper(Long paperId, String title, String abstractText,String contentPath, String keywords, String topics, Set<Author> authors/*, Set<Long> reviewers*/, Session sessionId) {
        log.trace("updatePaper: paperId={}, title={}, author={}, contentPath={}",
                paperId, title, keywords, topics, authors, contentPath);

        Paper paper = (Paper) paperRepository.findOne(paperId);
        paper.setTitle(title);
        paper.setAbstractText(abstractText);
        paper.setContentPath(contentPath);
        paper.setKeywords(keywords);
        paper.setTopics(topics);
        paper.setSession(sessionId);
        List<Author> authorList = authorRepository.findAll(authors.stream().map(author -> author.getId()).collect(Collectors.toSet()));
        authorList.forEach(author -> paper.addAuthor(author));

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
    public Paper createPaper(String title, String abstractText,String contentPath, String keywords, String topics) {
        log.trace("createPaper: title={}, author={}, contentPath={}",
               title, contentPath, keywords, topics);
        Paper paper = new Paper(title, abstractText,contentPath, keywords, topics);
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
