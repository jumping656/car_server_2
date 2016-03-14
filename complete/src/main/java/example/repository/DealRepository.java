package example.repository;

import example.domain.Deal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DealRepository extends CrudRepository<Deal, Long> {

	List<Deal> findAll();

	List<Deal> findByUserid(Integer userid);

	List<Deal> findByCoachid(Integer coachid);

	Deal findByDealid(Integer dealid);

	List<Deal> findByUseridAndState(Integer userid, Deal.DEAL_STATE deal_state);

	List<Deal> findBytradeno(String out_trade_no);
}