package example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface PersonRepository extends CrudRepository<User, Long> {

	Page<User> findAll(Pageable pageable);
}