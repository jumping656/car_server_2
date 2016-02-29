package example.repository;

import example.domain.Collection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CollectionRepository extends CrudRepository<Collection, Long> {

	List<Collection> findAll();

	List<Collection> findByCollectionid(Integer collectionid);

	List<Collection> findByUserid(Integer userid);

	List<Collection> findBySubjectoneid(Integer subjectoneid);

	List<Collection> findBySubjectfourid(Integer subjectfourid);

	List<Collection> findByUseridAndSubjectoneid(Integer userid, Integer subjectoneid);

	List<Collection> findByUseridAndSubjectfourid(Integer userid, Integer subjectfourid);
}