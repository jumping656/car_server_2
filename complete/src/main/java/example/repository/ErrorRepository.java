package example.repository;

import example.domain.ErrorQuestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ErrorRepository extends CrudRepository<ErrorQuestion, Long> {

	List<ErrorQuestion> findAll();

	List<ErrorQuestion> findByErrorid(Integer errorid);

	List<ErrorQuestion> findByUserid(Integer userid);

	List<ErrorQuestion> findBySubjectoneid(Integer subjectoneid);

	List<ErrorQuestion> findBySubjectfourid(Integer subjectfourid);

	List<ErrorQuestion> findByUseridAndSubjectoneid(Integer userid, Integer subjectoneid);

	List<ErrorQuestion> findByUseridAndSubjectfourid(Integer userid, Integer subjectfourid);
}