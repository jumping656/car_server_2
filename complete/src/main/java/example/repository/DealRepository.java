package example.repository;

import example.domain.Deal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface DealRepository extends CrudRepository<Deal, Long> {

	Page<Deal> findAll(Pageable pageable);
}