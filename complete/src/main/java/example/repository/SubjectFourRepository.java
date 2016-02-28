package example.repository;

import example.domain.SubjectFour;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface SubjectFourRepository extends CrudRepository<SubjectFour, Long> {

	List<SubjectFour> findAll();

	SubjectFour findById(Integer id);

	List<SubjectFour> findByItype(Integer type);

	List<SubjectFour> findByAchapter(Integer chapter);
}