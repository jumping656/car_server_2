package example.repository;

import example.domain.SubjectOne;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface SubjectOneRepository extends CrudRepository<SubjectOne, Long> {

	List<SubjectOne> findAll();

	SubjectOne findById(Integer id);

	//List<SubjectFour> findByType(Integer type);

	List<SubjectOne> findByChapter(Integer chapter);
}