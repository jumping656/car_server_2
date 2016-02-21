package example.controller;

import example.domain.User;
import example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Handles requests for the User service.
 */

@Controller
@RestController
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	//first time register only with registerphone
	@RequestMapping(value = UserRestURIConstants.CREATE_USER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createUser(@RequestBody User user) {
		logger.info("Start createUser.");

		if (user.getRegisterphone().isEmpty() || null == user.getRegisterphone()){
			logger.info("register phone is empty");
			return new ResponseEntity<String>("register phone is empty",
					HttpStatus.NOT_FOUND);
		}

		User getUser = userRepository.findByGegisterphone(user.getRegisterphone());
		if (null != getUser){
			logger.info("register phone already exists");
			return new ResponseEntity<String>("register phone already exists",
					HttpStatus.CONFLICT);
		}

		try {
			user.setVerify(false);
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("save user failed." + e.toString());
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("create user Successfully!");
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	//update user
	@RequestMapping(value = UserRestURIConstants.UPDATE_USER, method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateUser(@RequestBody User user) {
		logger.info("Start updateUser.");

		User getUser = userRepository.findByUsername(user.getUsername());
		if (null == getUser){
			logger.info("user not exists");
			return new ResponseEntity<String>("user not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
			getUser.updateAllowedAttribute(user);

			//first time update to set
			if (!getUser.getVerify())
			{
				getUser.setVerify(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("update user failed." + e.toString());
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("update user Successfully!");
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	//delete user
	@RequestMapping(value = UserRestURIConstants.DELETE_USER, method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteUser(@RequestBody User user) {
		logger.info("Start deleteUser.");

		if (null == user.getUserid() || user.getUserid() <= 0){
			logger.info("userid invalid");
			return new ResponseEntity<String>("userid invalid",
					HttpStatus.NOT_FOUND);
		}

		User getUser = userRepository.findByUserid(user.getUserid());
		if (null == getUser){
			logger.info("user not exists");
			return new ResponseEntity<String>("user not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
			userRepository.save(getUser);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("delete user failed." + e.toString());
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("delete user Successfully!");
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	//get user by registerphone
	@RequestMapping(value = UserRestURIConstants.GET_USER, method = RequestMethod.GET)
	@ResponseBody
	public User getUser(@RequestBody User user) {
		if (user.getRegisterphone().isEmpty() || null == user.getRegisterphone()){
			logger.info("register phone is empty");
			return null;
		}
		logger.info("Start getUser. Registerphone= " + user.getRegisterphone());

		User getUser = userRepository.findByGegisterphone(user.getRegisterphone());
		if (null == getUser){
			return null;
		}

		logger.info("get user Successfully!");
		return getUser;
	}

	//get all users
	@RequestMapping(value = UserRestURIConstants.GET_ALL_USER, method = RequestMethod.GET)
	@ResponseBody
	public List<User> getAllUsers(@RequestBody User user) {
		logger.info("Start getUsers.");

		List<User> getUser = userRepository.findAll();
		if (null == getUser){
			return null;
		}

		logger.info("get all users Successfully!");
		return getUser;
	}
}