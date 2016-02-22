package example.repository;

import example.domain.Coach;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CoachRepository extends CrudRepository<Coach, Long> {

	List<Coach> findAll();

	Coach findByRegisterphone(String registerphone);

	Coach findByCoachname(String coachname);

	Coach findByCoachid(Integer coachid);

}