package ro.ubb.conference.core.service;
import org.springframework.stereotype.Service;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Session;

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

    Paper updatePaper(Long paperId, String title, String content, Set<Long> authors, Set<Long> reviewers, Session sessionId );

    Paper createPaper(String title, String content);

    void deletePaper(Long paperId);
}
