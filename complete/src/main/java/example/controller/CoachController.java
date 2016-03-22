package example.controller;

import example.domain.Coach;
import example.domain.Md5;
import example.repository.CoachRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
public class CoachController {

	private static final Logger logger = LoggerFactory.getLogger(CoachController.class);

	@Autowired
	private CoachRepository coachRepository;

	//first time register only with registerphone
	@RequestMapping(value = CoachRestURIConstants.CREATE_COACH, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> createCoach(@RequestBody Coach coach) {
		logger.info("Start createCoach.");
		ResponseResult responseResult = new ResponseResult();

		if (null == coach.getRegisterphone() || coach.getRegisterphone().isEmpty()){
			logger.info("register phone is empty");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			responseResult.setResult(coach);
			return new ResponseEntity<Object>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		Coach getCoach = coachRepository.findByRegisterphone(coach.getRegisterphone());
		if (null != getCoach){
			logger.info("register phone already exists");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			responseResult.setResult(coach);
			return new ResponseEntity<Object>(responseResult,
					HttpStatus.CONFLICT);
		}

		try {
			Coach coachTmp = new Coach();
			coachTmp.setRegisterphone(coach.getRegisterphone());
			coachTmp.setVerify(false);
			coachRepository.save(coachTmp);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("create coach failed!");
			responseResult.setCode(ResponseResult.OPERATION_ERROR);
			responseResult.setResult(coach);
			return new ResponseEntity<Object>(responseResult, HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("create coach Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(coach);
		return new ResponseEntity<Object>(responseResult, HttpStatus.OK);
	}

	//update coach
	@RequestMapping(value = CoachRestURIConstants.UPDATE_COACH, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> updateCoach(@RequestBody Coach coach) {
		logger.info("Start updateCoach.");
		ResponseResult responseResult = new ResponseResult();

		if (coach.getCoachid() <= 0  || null == coach.getCoachid()){
			logger.info("coachid is empty");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			responseResult.setResult(coach);
			return new ResponseEntity<Object>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		Coach getCoach = coachRepository.findByCoachid(coach.getCoachid());
		if (null == getCoach){
			logger.info("coach not exists");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			responseResult.setResult(coach);
			return new ResponseEntity<Object>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		try {
			if (null != coach.getPassword() && "" != coach.getPassword()){
				String md5String = Md5.getMD5Str(coach.getPassword());
				getCoach.setPassword(md5String);
			}

			getCoach.updateAllowedAttribute(coach);

			//first time update to set
			if (!getCoach.getVerify())
			{
				getCoach.setVerify(true);
			}
			coachRepository.save(getCoach);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("update coach failed." + e.toString());
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("update coach Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(getCoach);
		return new ResponseEntity<Object>(responseResult, HttpStatus.OK);
	}

	//delete coach
	@RequestMapping(value = CoachRestURIConstants.DELETE_COACH, method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Object> deleteUser(@RequestBody Coach coach) {
		logger.info("Start deleteCoach.");
		ResponseResult responseResult = new ResponseResult();

		if (null == coach.getCoachid() || coach.getCoachid() <= 0){
			logger.info("coachid invalid");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			responseResult.setResult(coach);
			return new ResponseEntity<Object>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		Coach getCoach = coachRepository.findByCoachid(coach.getCoachid());
		if (null == getCoach){
			logger.info("coach not exists");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			responseResult.setResult(coach);
			return new ResponseEntity<Object>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		try {
			coachRepository.delete(getCoach);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("delete deal failed");
			responseResult.setCode(ResponseResult.OPERATION_ERROR);
			responseResult.setResult(coach);
			return new ResponseEntity<Object>(responseResult,
					HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("delete coach Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(coach);
		return new ResponseEntity<Object>(responseResult, HttpStatus.OK);
	}

	//get coach by coachid
	@RequestMapping(value = CoachRestURIConstants.GET_COACH, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getCoach(@RequestBody Coach coach) {
		ResponseResult responseResult = new ResponseResult();

		if (null == coach.getCoachid() || coach.getCoachid() <= 0){
			logger.info("coachid is empty");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			responseResult.setResult(coach);
			return new ResponseEntity<Object>(responseResult,
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getCoach. coachid= " + coach.getCoachid());

		Coach getCoach = coachRepository.findByCoachid(coach.getCoachid());
		if (null == getCoach){
			logger.info("coach not found!");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			responseResult.setResult(coach);
			return new ResponseEntity<Object>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		logger.info("get coach Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(getCoach);
		return new ResponseEntity<Object>(responseResult, HttpStatus.OK);
	}

	//get all coachs
	@RequestMapping(value = CoachRestURIConstants.GET_ALL_COACH, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllCoachs() {
		logger.info("Start getCoachs.");
		ResponseResult responseResult = new ResponseResult();

		List<Coach> getCoachList = coachRepository.findAll();
		if (getCoachList.isEmpty()){
			logger.info("no coach");
			responseResult.setCode(ResponseResult.SUCCESS);
			return new ResponseEntity<Object>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		logger.info("get all coachs Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(getCoachList);
		return new ResponseEntity<Object>(responseResult, HttpStatus.OK);
	}
}