package example.controller;

import example.domain.Coach;
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
	public ResponseEntity<String> createCoach(@RequestBody Coach coach) {
		logger.info("Start createCoach.");

		if (coach.getRegisterphone().isEmpty() || null == coach.getRegisterphone()){
			logger.info("register phone is empty");
			return new ResponseEntity<String>("register phone is empty",
					HttpStatus.NOT_FOUND);
		}

		Coach getCoach = coachRepository.findByRegisterphone(coach.getRegisterphone());
		if (null != getCoach){
			logger.info("register phone already exists");
			return new ResponseEntity<String>("register phone already exists",
					HttpStatus.CONFLICT);
		}

		try {
			coach.setVerify(false);
			coachRepository.save(coach);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("save coach failed." + e.toString());
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("create coach Successfully!");
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	//update coach
	@RequestMapping(value = CoachRestURIConstants.UPDATE_COACH, method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateCoach(@RequestBody Coach coach) {
		logger.info("Start updateCoach.");

		if (coach.getRegisterphone().isEmpty() || null == coach.getRegisterphone()){
			logger.info("register phone is empty");
			return new ResponseEntity<String>("register phone is empty",
					HttpStatus.NOT_FOUND);
		}

		Coach getCoach = coachRepository.findByRegisterphone(coach.getRegisterphone());
		if (null == getCoach){
			logger.info("coach not exists");
			return new ResponseEntity<String>("coach not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
			getCoach.updateAllowedAttribute(coach);

			//first time update to set
			if (!getCoach.getVerify())
			{
				getCoach.setVerify(true);
			}
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
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	//delete coach
	@RequestMapping(value = CoachRestURIConstants.DELETE_COACH, method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteUser(@RequestBody Coach coach) {
		logger.info("Start deleteCoach.");

		if (null == coach.getCoachid() || coach.getCoachid() <= 0){
			logger.info("coachid invalid");
			return new ResponseEntity<String>("coachid invalid",
					HttpStatus.NOT_FOUND);
		}

		Coach getCoach = coachRepository.findByCoachid(coach.getCoachid());
		if (null == getCoach){
			logger.info("coach not exists");
			return new ResponseEntity<String>("coach not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
			coachRepository.delete(getCoach);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("delete coach failed." + e.toString());
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("delete coach Successfully!");
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	//get coach by registerphone
	@RequestMapping(value = CoachRestURIConstants.GET_COACH, method = RequestMethod.GET)
	@ResponseBody
	public Coach getCoach(@RequestBody Coach coach) {
		if (coach.getRegisterphone().isEmpty() || null == coach.getRegisterphone()){
			logger.info("register phone is empty");
			return null;
		}
		logger.info("Start getCoach. Registerphone= " + coach.getRegisterphone());

		Coach getCoach = coachRepository.findByRegisterphone(coach.getRegisterphone());
		if (null == getCoach){
			return null;
		}

		logger.info("get coach Successfully!");
		return getCoach;
	}

	//get all coachs
	@RequestMapping(value = CoachRestURIConstants.GET_ALL_COACH, method = RequestMethod.GET)
	@ResponseBody
	public List<Coach> getAllCoachs(@RequestBody Coach coach) {
		logger.info("Start getCoachs.");

		List<Coach> getCoachList = coachRepository.findAll();
		if (null == getCoachList){
			return null;
		}

		logger.info("get all coachs Successfully!");
		return getCoachList;
	}
}