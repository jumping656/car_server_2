package example.repository;

import example.domain.SubjectFour;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface SubjectFourRepository extends CrudRepository<SubjectFour, Long> {

	List<SubjectFour> findAll();

	SubjectFour findById(Integer id);

	List<SubjectFour> findByType(Integer type);

	List<SubjectFour> findByChapter(Integer chapter);
}