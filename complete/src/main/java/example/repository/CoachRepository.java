package example.repository;

import example.domain.Coach;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface CoachRepository extends CrudRepository<Coach, Long> {

	List<Coach> findAll();

	Coach findByRegisterphone(String registerphone);

	Coach findByCoachname(String coachname);

	Coach findByCoachid(Integer coachid);

}