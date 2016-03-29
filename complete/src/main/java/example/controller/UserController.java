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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Handles requests for the User service.
 * 该类处理普通学员的controller类
 */

@Controller
@RestController
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	                     Model model;
	@Autowired
	private UserRepository userRepository;

	//first time register only with registerphone
	@RequestMapping(value = UserRestURIConstants.CREATE_USER, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		logger.info("Start createUser.");
		ResponseResult responseResult = new ResponseResult();

		if (null == user.getRegisterphone() || user.getRegisterphone().isEmpty()){
			logger.info("register phone is empty");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			responseResult.setResult(user);
			return new ResponseEntity<Object>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		User getUser = userRepository.findByRegisterphone(user.getRegisterphone());
		if (null != getUser){
			logger.info("register phone already exists");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			responseResult.setResult(user);
			return new ResponseEntity<Object>(responseResult,
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
			responseResult.setCode(ResponseResult.OPERATION_ERROR);
			responseResult.setResult(user);
			return new ResponseEntity<>(responseResult, HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("create user Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(user);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	//update user
	@RequestMapping(value = UserRestURIConstants.UPDATE_USER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> updateUser(@RequestBody User user) {
		logger.info("Start updateUser.");
		ResponseResult responseResult = new ResponseResult();

		if (null == user.getUserid() || user.getUserid() <= 0){
			logger.info("userid empty");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			responseResult.setResult(user);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		User getUser = userRepository.findByUserid(user.getUserid());
		if (null == getUser){
			logger.info("user not exists");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			responseResult.setResult(user);
			return new ResponseEntity<>(responseResult,
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
					responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
					responseResult.setResult(user);
					return new ResponseEntity<>(responseResult, HttpStatus.CONFLICT);
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
			responseResult.setCode(ResponseResult.OPERATION_ERROR);
			responseResult.setResult(user);
			return new ResponseEntity<>(responseResult, HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("update user Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(getUser);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	//delete user
	@RequestMapping(value = UserRestURIConstants.DELETE_USER, method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Object> deleteUser(@RequestBody User user) {
		logger.info("Start deleteUser.");
		ResponseResult responseResult = new ResponseResult();

		if (null == user.getUserid() || user.getUserid() <= 0){
			logger.info("userid invalid");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			responseResult.setResult(user);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		User getUser = userRepository.findByUserid(user.getUserid());
		if (null == getUser){
			logger.info("user not exists");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			responseResult.setResult(user);
			return new ResponseEntity<>(responseResult,
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
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(user);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	//get user by userid
	@RequestMapping(value = UserRestURIConstants.GET_USER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getUser(@RequestBody User user) {
		ResponseResult responseResult = new ResponseResult();

		if (null == user.getUserid() || user.getUserid() <= 0){ //null == user.getUserid() must be ahead of <=0, or will be NullPointerException
			logger.info("userid is empty");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			responseResult.setResult(user);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getUser. userid= " + user.getUserid());

		User getUser = userRepository.findByUserid(user.getUserid());
		if (null == getUser){
			logger.info("user not found!");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			responseResult.setResult(user);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		logger.info("get user Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(getUser);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	//get all users
	@RequestMapping(value = UserRestURIConstants.GET_ALL_USER, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllUsers() {
		logger.info("Start getUsers.");
		ResponseResult responseResult = new ResponseResult();

		List<User> getUserList = userRepository.findAll();
		if (getUserList.isEmpty()){
			logger.info("no user");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		logger.info("get all users Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(getUserList);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	//user login by registerphone or username
	@RequestMapping(value = UserRestURIConstants.USER_LOGIN, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> userLogin(@RequestBody User user) {
		String loginKey = null;
		User getUser = null;
		ResponseResult responseResult = new ResponseResult();

		//check params
		if (null == user.getPassword() || "" == user.getPassword()){
			logger.info("password empty");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			responseResult.setResult(user);
			return new ResponseEntity<>(responseResult,
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
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			responseResult.setResult(user);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		if (null == getUser){
			logger.info("user not exists");
			responseResult.setCode(ResponseResult.DB_ERROR);
			responseResult.setResult(user);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		try {
			String md5String = Md5.getMD5Str(user.getPassword());
			if (!getUser.getPassword().equals(md5String)){
				logger.info("password or username/registerphone invalid!");
				responseResult.setCode(ResponseResult.PARAM_ERROR);
				responseResult.setResult(user);
				return new ResponseEntity<>(responseResult, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseResult.setCode(ResponseResult.OPERATION_ERROR);
			responseResult.setResult(user);
			return new ResponseEntity<>(responseResult, HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("login Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(getUser);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}