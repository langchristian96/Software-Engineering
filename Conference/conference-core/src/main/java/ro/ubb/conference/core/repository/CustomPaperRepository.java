package ro.ubb.conference.core.repository;

import org.springframework.stereotype.*;
import org.springframework.stereotype.Repository;

/**
 * Created by CristianCosmin on 31.05.2017.
 */
@Repository
public interface CustomPaperRepository {
    void delete(Long paperId);
}
