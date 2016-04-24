package example.repository;

import example.domain.SubjectOne;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface SubjectOneRepository extends CrudRepository<SubjectOne, Long> {

	List<SubjectOne> findAll();

	SubjectOne findById(Integer id);

	//List<SubjectFour> findByType(Integer type);

	List<SubjectOne> findByAchapter(Integer chapter);
}