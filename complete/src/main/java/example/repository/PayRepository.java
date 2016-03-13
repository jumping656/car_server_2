package example.repository;

import example.domain.Deal;
import example.domain.PayOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PayRepository extends CrudRepository<PayOrder, Long> {

	List<PayOrder> findAll();

	List<PayOrder> findByUserid(Integer userid);

	List<PayOrder> findByCoachid(Integer coachid);

	PayOrder findByDealid(Integer dealid);

	List<PayOrder> findByUseridAndState(Integer userid, Deal.DEAL_STATE deal_state);
}