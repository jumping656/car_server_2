package example.repository;

import example.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findAll();

	User findByRegisterphone(String registerphone);

	User findByUsername(String username);

	User findByUserid(Integer userid);
}