package example.repository;

import example.domain.Deal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DealRepository extends CrudRepository<Deal, Long> {

	List<Deal> findAll();

	Deal findByUserid(Integer userid);

	Deal findByCoachid(Integer coachid);

	Deal findByDealid(Integer dealid);
}