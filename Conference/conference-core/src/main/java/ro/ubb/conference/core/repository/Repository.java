package ro.ubb.conference.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.conference.core.domain.BaseEntity;

import java.io.Serializable;

@NoRepositoryBean
@Transactional
public interface Repository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID> {}
