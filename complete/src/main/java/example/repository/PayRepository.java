package example.repository;

import example.domain.Pay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface PayRepository extends CrudRepository<Pay, Long> {

	List<Pay> findAll();

	List<Pay> findBytradeno(String out_trade_no);

	List<Pay> findByPayorderid(Integer payorderid);
}