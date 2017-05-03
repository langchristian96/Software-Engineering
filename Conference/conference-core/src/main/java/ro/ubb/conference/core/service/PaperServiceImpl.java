package ro.ubb.conference.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.repository.PaperRepository;

import java.util.List;
import java.util.ArrayList;

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
    @Transactional
    public Paper updatePaper(Long paperId, String title, Long personId, String content ) {
        log.trace("updatePaper: paperId={}, title={}, author={}, content={}",
                paperId, title, personId, content);

        Paper paper = paperRepository.findOne(paperId);
        paper.setTitle(title);
        paper.setAuthor(personId);
        paper.setContent(content);

        log.trace("updatePaper: paper={}", paper);

        return paper;
    }

    @Override
    public Paper createPaper(String title, Long personId, String content) {
        log.trace("createPaper: title={}, author={}, content={}",
               title, personId, content);

        Paper paper = new Paper(title, personId, content);
        paper = paperRepository.save(paper);

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
