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
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		logger.info("Start createUser.");

		if (user.getRegisterphone().isEmpty() || null == user.getRegisterphone()){
			logger.info("register phone is empty");
			return new ResponseEntity<Object>("register phone is empty",
					HttpStatus.NOT_FOUND);
		}

		User getUser = userRepository.findByRegisterphone(user.getRegisterphone());
		if (null != getUser){
			logger.info("register phone already exists");
			return new ResponseEntity<Object>("register phone already exists",
					HttpStatus.CONFLICT);
		}

		try {
			user.setVerify(false);
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("create user failed!");
			return new ResponseEntity<Object>("fail to create user.", HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("create user Successfully!");
		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}

	//update user
	@RequestMapping(value = UserRestURIConstants.UPDATE_USER, method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Object> updateUser(@RequestBody User user) {
		logger.info("Start updateUser.");

		if (user.getUserid() <= 0 || null == user.getUserid()){
			logger.info("userid empty");
			return new ResponseEntity<Object>("userid is empty",
					HttpStatus.NOT_FOUND);
		}

		User getUser = userRepository.findByUserid(user.getUserid());
		if (null == getUser){
			logger.info("user not exists");
			return new ResponseEntity<Object>("user not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
			getUser.updateAllowedAttribute(user);

			//first time update to set
			if (!getUser.getVerify())
			{
				getUser.setVerify(true);
			}
			userRepository.save(getUser);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("fail to update user.", HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("update user Successfully!");
		return new ResponseEntity<Object>(getUser, HttpStatus.OK);
	}

	//delete user
	@RequestMapping(value = UserRestURIConstants.DELETE_USER, method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Object> deleteUser(@RequestBody User user) {
		logger.info("Start deleteUser.");

		if (null == user.getUserid() || user.getUserid() <= 0){
			logger.info("userid invalid");
			return new ResponseEntity<Object>("userid invalid",
					HttpStatus.NOT_FOUND);
		}

		User getUser = userRepository.findByUserid(user.getUserid());
		if (null == getUser){
			logger.info("user not exists");
			return new ResponseEntity<Object>("user not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
			userRepository.delete(getUser);
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
		return new ResponseEntity<Object>("user deleted.", HttpStatus.OK);
	}

	//get user by userid
	@RequestMapping(value = UserRestURIConstants.GET_USER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getUser(@RequestBody User user) {
		if (null == user.getUserid() || user.getUserid() <= 0){ //null == user.getUserid() must be ahead of <=0, or will be NullPointerException
			logger.info("userid is empty");
			return new ResponseEntity<Object>("userid invalid",
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getUser. userid= " + user.getUserid());

		User getUser = userRepository.findByUserid(user.getUserid());
		if (null == getUser){
			logger.info("user not found!");
			return new ResponseEntity<Object>("userid invalid",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get user Successfully!");
		return new ResponseEntity<Object>(getUser, HttpStatus.OK);
	}

	//get all users
	@RequestMapping(value = UserRestURIConstants.GET_ALL_USER, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllUsers() {
		logger.info("Start getUsers.");

		List<User> getUserList = userRepository.findAll();
		if (getUserList.isEmpty()){
			logger.info("no user");
			return new ResponseEntity<Object>("no user found",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get all users Successfully!");
		return new ResponseEntity<Object>(getUserList, HttpStatus.OK);
	}
}