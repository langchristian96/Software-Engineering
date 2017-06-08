package ro.ubb.conference.core.repository;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.AuthorPaper;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.domain.Reviewer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by CristianCosmin on 31.05.2017.
 */
@org.springframework.stereotype.Repository
public class PaperRepositoryImpl implements CustomPaperRepository {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Override
    public void delete(Long paperId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Paper paper = entityManager.find(Paper.class, paperId);
        Set<Author> authors =  paper.getAuthors();


                authors.forEach(i->
                {
                    if(i.getPapers().contains(paper)){
                        i.removePaper(paper);
                    }
                });
        Set<Reviewer> reviewers = paper.getReviewers();
        reviewers.forEach(i->{
            if(i.getPapers().contains(paper)){
                i.removePaper(paper);
            }
        });
        reviewers.clear();
        authors.clear();
        entityManager.remove(paper);


        transaction.commit();
        entityManager.close();
    }

    @Override
    public Reviewer updateReviewerGrade(Long personId, Long paperId, int grade){
                EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Reviewer person = (Reviewer) reviewerRepository.findOne(personId);
        Paper paper =  entityManager.find(Paper.class, paperId);

        person.updatePaper(paper, grade);
        paper.updateReviewer(person, grade);
        entityManager.merge(paper);
        entityManager.merge(person);
        transaction.commit();
        entityManager.close();

        return person;
    }
}

