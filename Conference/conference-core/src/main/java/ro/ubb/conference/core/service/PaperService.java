package ro.ubb.conference.core.service;
import org.springframework.stereotype.Service;
import ro.ubb.conference.core.domain.Paper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anca.
 */
@Service
public interface PaperService {
    List<Paper> findAll();

    Paper updatePaper(Long paperId, String title, String author, String content);

    Paper createPaper(String title, String author, String content);

    void deletePaper(Long paperId);
}
