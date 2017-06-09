package ro.ubb.conference.core.service;
import org.springframework.stereotype.Service;
import ro.ubb.conference.core.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by anca.
 */
@Service
public interface PaperService {
    List<Paper> findAll();

    Set<Paper> findAll(Set<Long> papers);

    Paper findPaper(Long paperId);

    Set<Paper> findAllPapersOfReviewer(Long reviewerId);
    Set<Paper> findAllPapersOfAuthor(Long authorId);


    Set<Paper> findAllPapersOfChair(Long chairId);


    Set<Paper> findAllPapersByTitle(Set<String> papersTitle);

    Paper updatePaper(Long paperId, String title, String abstractText, String contentPath, String keywords, String topics, Set<Author> authors, Set<Reviewer> reviewers, Long sessionId);

    Paper createPaper(String title, String abstractText, String contentPath, String keywords, String topics, Conference conference);

    Paper updatePaperAuthors(Long paperId, Set<Author> authors);

    void deletePaper(Long paperId);
}
