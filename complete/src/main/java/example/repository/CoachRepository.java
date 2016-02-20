package example.repository;

import example.domain.Coach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface CoachRepository extends CrudRepository<Coach, Long> {

	Page<Coach> findAll(Pageable pageable);
}