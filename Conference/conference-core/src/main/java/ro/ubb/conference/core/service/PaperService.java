package ro.ubb.conference.core.service;
import ro.ubb.conference.core.domain.Paper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anca.
 */
public interface PaperService {
    List<Paper> findAll();

    Paper updatePaper(Long paperId, String title, Long personId, String content);

    Paper createPaper(String title, Long personId, String content);

    void deletePaper(Long paperId);
}
