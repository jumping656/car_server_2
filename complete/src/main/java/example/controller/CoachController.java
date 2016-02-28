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
	public ResponseEntity<Object> createCoach(@RequestBody Coach coach) {
		logger.info("Start createCoach.");

		if (coach.getRegisterphone().isEmpty() || null == coach.getRegisterphone()){
			logger.info("register phone is empty");
			return new ResponseEntity<Object>("register phone is empty",
					HttpStatus.NOT_FOUND);
		}

		Coach getCoach = coachRepository.findByRegisterphone(coach.getRegisterphone());
		if (null != getCoach){
			logger.info("register phone already exists");
			return new ResponseEntity<Object>("register phone already exists",
					HttpStatus.CONFLICT);
		}

		try {
			coach.setVerify(false);
			coachRepository.save(coach);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("create coach failed!");
			return new ResponseEntity<Object>("fail to create coach.", HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("create coach Successfully!");
		return new ResponseEntity<Object>(coach, HttpStatus.OK);
	}

	//update coach
	@RequestMapping(value = CoachRestURIConstants.UPDATE_COACH, method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Object> updateCoach(@RequestBody Coach coach) {
		logger.info("Start updateCoach.");

		if (coach.getCoachid() <= 0  || null == coach.getCoachid()){
			logger.info("coachid is empty");
			return new ResponseEntity<Object>("coachid is empty",
					HttpStatus.NOT_FOUND);
		}

		Coach getCoach = coachRepository.findByCoachid(coach.getCoachid());
		if (null == getCoach){
			logger.info("coach not exists");
			return new ResponseEntity<Object>("coach not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
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
		return new ResponseEntity<Object>(getCoach, HttpStatus.OK);
	}

	//delete coach
	@RequestMapping(value = CoachRestURIConstants.DELETE_COACH, method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Object> deleteUser(@RequestBody Coach coach) {
		logger.info("Start deleteCoach.");

		if (null == coach.getCoachid() || coach.getCoachid() <= 0){
			logger.info("coachid invalid");
			return new ResponseEntity<Object>("coachid invalid",
					HttpStatus.NOT_FOUND);
		}

		Coach getCoach = coachRepository.findByCoachid(coach.getCoachid());
		if (null == getCoach){
			logger.info("coach not exists");
			return new ResponseEntity<Object>("coach not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
			coachRepository.delete(getCoach);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("delete deal failed");
			return new ResponseEntity<Object>("delete deal failed",
					HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("delete coach Successfully!");
		return new ResponseEntity<Object>("coach deleted", HttpStatus.OK);
	}

	//get coach by coachid
	@RequestMapping(value = CoachRestURIConstants.GET_COACH, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getCoach(@RequestBody Coach coach) {
		if (null == coach.getCoachid() || coach.getCoachid() <= 0){
			logger.info("coachid is empty");
			return new ResponseEntity<Object>("coachid invalid",
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getCoach. coachid= " + coach.getCoachid());

		Coach getCoach = coachRepository.findByCoachid(coach.getCoachid());
		if (null == getCoach){
			logger.info("coach not found!");
			return new ResponseEntity<Object>("coachid not found",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get coach Successfully!");
		return new ResponseEntity<Object>(getCoach, HttpStatus.OK);
	}

	//get all coachs
	@RequestMapping(value = CoachRestURIConstants.GET_ALL_COACH, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllCoachs() {
		logger.info("Start getCoachs.");

		List<Coach> getCoachList = coachRepository.findAll();
		if (getCoachList.isEmpty()){
			logger.info("no coach");
			return new ResponseEntity<Object>("no coach found",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get all coachs Successfully!");
		return new ResponseEntity<Object>(getCoachList, HttpStatus.OK);
	}
}