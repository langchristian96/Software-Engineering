package ro.ubb.conference.core.repository;

import org.springframework.stereotype.*;
import org.springframework.stereotype.Repository;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Reviewer;

/**
 * Created by CristianCosmin on 31.05.2017.
 */
@Repository
public interface CustomPaperRepository {
    void delete(Long paperId);
    Reviewer updateReviewerGrade(Long personId, Long paperId, int grade);
}
