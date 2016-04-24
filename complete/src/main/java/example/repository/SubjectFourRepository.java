package example.repository;

import example.domain.SubjectFour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface SubjectFourRepository extends CrudRepository<SubjectFour, Long> {

	List<SubjectFour> findAll();

	SubjectFour findById(Integer id);

	List<SubjectFour> findByItype(Integer type);

	List<SubjectFour> findByAchapter(Integer chapter);
}