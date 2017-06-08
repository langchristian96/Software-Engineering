package ro.ubb.conference.core.service;

import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Reviewer;

import java.util.List;
import java.util.Set;

/**
 * Created by langchristian96 on 5/18/2017.
 */



public interface ReviewerService {
    List<Reviewer> findAll();

    Reviewer findReviewer(Long reviewerId);

    Reviewer updateReviewerGrade(Long personId, Long paperId, int grade);

    Reviewer updateReviewer(Long personId, String password, String name, String affiliation, String email, Set<Long> papers);

    Reviewer createReviewer(String user, String password, String name, String affiliation, String email);

    Set<Reviewer> findAllReviewersByUsernames(Set<String> reviewersUsername);

    void deleteReviewer(Long personId);

}
