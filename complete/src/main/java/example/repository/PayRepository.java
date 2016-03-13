package example.repository;

import example.domain.Pay;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PayRepository extends CrudRepository<Pay, Long> {

	List<Pay> findAll();

	List<Pay> findBytradeno(String out_trade_no);

	List<Pay> findByPayorderid(Integer payorderid);
}