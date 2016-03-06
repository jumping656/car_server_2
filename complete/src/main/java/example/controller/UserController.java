package example.controller;

import example.domain.Md5;
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
	@RequestMapping(value = UserRestURIConstants.CREATE_USER, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public ResponseEntity<Object> createUser(@ModelAttribute User user) {
		logger.info("Start createUser.");

		if (null == user.getRegisterphone() || user.getRegisterphone().isEmpty()){
			logger.info("register phone is empty");
			return new ResponseEntity<Object>(user,
					HttpStatus.NOT_FOUND);
		}

		User getUser = userRepository.findByRegisterphone(user.getRegisterphone());
		if (null != getUser){
			logger.info("register phone already exists");
			return new ResponseEntity<Object>("register phone already exists",
					HttpStatus.CONFLICT);
		}

		try {
			User userTmp = new User();
			userTmp.setRegisterphone(user.getRegisterphone());
			userTmp.setVerify(false);
			userRepository.save(userTmp);
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
	@RequestMapping(value = UserRestURIConstants.UPDATE_USER, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public ResponseEntity<Object> updateUser(@ModelAttribute User user) {
		logger.info("Start updateUser.");

		if (null == user.getUserid() || user.getUserid() <= 0){
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
			//check username
//			if (null == getUser.getUsername() || getUser.getUsername().equals("")){
//				getUser.setUsername(user.getUsername());
//			}
			//check updated username unique
//			else{
				User userTmp = userRepository.findByUsername(user.getUsername());
				if (null != userTmp){
					logger.info("username already exits");
					return new ResponseEntity<Object>("username already exits", HttpStatus.CONFLICT);
				}
				getUser.setUsername(user.getUsername());
//			}
			getUser.updateAllowedAttribute(user);

			if (null != user.getPassword() && "" != user.getPassword()){
				String md5String = Md5.getMD5Str(user.getPassword());
				getUser.setPassword(md5String);
			}

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
	@RequestMapping(value = UserRestURIConstants.DELETE_USER, method = RequestMethod.DELETE, headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public ResponseEntity<Object> deleteUser(@ModelAttribute User user) {
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
	@RequestMapping(value = UserRestURIConstants.GET_USER, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public ResponseEntity<Object> getUser(@ModelAttribute User user) {
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

	//user login by registerphone or username
	@RequestMapping(value = UserRestURIConstants.USER_LOGIN, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public ResponseEntity<Object> userLogin(@ModelAttribute User user) {
		String loginKey = null;
		User getUser = null;
		//check params
		if (null == user.getPassword() || "" == user.getPassword()){
			logger.info("password empty");
			return new ResponseEntity<Object>("password empty",
					HttpStatus.NOT_FOUND);
		}

		if (null != user.getRegisterphone() && "" != user.getRegisterphone()){
			logger.info("login by registerphone");
			loginKey = user.getRegisterphone();
			getUser = userRepository.findByRegisterphone(loginKey);
		}
		else if (null != user.getUsername() && "" != user.getUsername()){
			logger.info("login by username");
			loginKey =  user.getUsername();
			getUser = userRepository.findByUsername(loginKey);
		}
		else{
			logger.info("registerphone or username need to login");
			return new ResponseEntity<Object>("registerphone or username need to login",
					HttpStatus.NOT_FOUND);
		}

		if (null == getUser){
			logger.info("user not exists");
			return new ResponseEntity<Object>("user not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
			String md5String = Md5.getMD5Str(user.getPassword());
			if (!getUser.getPassword().equals(md5String)){
				logger.info("password or username/registerphone invalid!");
				return new ResponseEntity<Object>("password or username/registerphone invalid!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("login error", HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("login Successfully!");
		return new ResponseEntity<Object>("login success", HttpStatus.OK);
	}
}