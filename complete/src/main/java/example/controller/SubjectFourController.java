package example.controller;

import example.domain.SubjectFour;
import example.repository.SubjectFourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Handles requests for the SubjectFour service.
 */

@Controller
@RestController
public class SubjectFourController {

	private static final Logger logger = LoggerFactory.getLogger(SubjectFourController.class);

	@Autowired
	private SubjectFourRepository subjectFourRepository;

	//get subjectfour by id
	@RequestMapping(value = SubjectFourRestURIConstants.GET_SUBJECTFOUR_BY_ID, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getSubjectFourById(@RequestBody SubjectFour subjectFour) {
		if (null == subjectFour.getId() || subjectFour.getId() <= 0){
			logger.info("id is invalid");
			return new ResponseEntity<Object>("id invalid",
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getSubjectFour. id= " + subjectFour.getId());

		SubjectFour getSubjectFour = subjectFourRepository.findById(subjectFour.getId());
		if (null == getSubjectFour){
			logger.info("subject four not found!");
			return new ResponseEntity<Object>("subject four not found!",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get subjectFour Successfully!");
		return new ResponseEntity<Object>(getSubjectFour, HttpStatus.OK);
	}

	//get subjectfour by chapter
	@RequestMapping(value = SubjectFourRestURIConstants.GET_SUBJECTFOUR_BY_CHAPTER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getSubjectFourByChapter(@RequestBody SubjectFour subjectFour) {
		if (null == subjectFour.getAchapter() || subjectFour.getAchapter() <= 0){
			logger.info("chapter id is invalid");
			return new ResponseEntity<Object>("chapter id is invalid!",
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getSubjectFour. chapter= " + subjectFour.getAchapter());

		List<SubjectFour> getSubjectFourList = subjectFourRepository.findByAchapter(subjectFour.getAchapter());
		if (getSubjectFourList.isEmpty()){
			logger.info("subject four not found!");
			return new ResponseEntity<Object>("subject four not found!",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get subjectFour Successfully!");
		return new ResponseEntity<Object>(getSubjectFourList, HttpStatus.OK);
	}

	//get subjectfour by type
	@RequestMapping(value = SubjectFourRestURIConstants.GET_SUBJECTFOUR_BY_TYPE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getSubjectFourByType(@RequestBody SubjectFour subjectFour) {
		if ( null == subjectFour.getItype() || subjectFour.getItype() <= 0){
			logger.info("type id is invalid");
			return new ResponseEntity<Object>("type id is invalid!",
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getSubjectFour. type= " + subjectFour.getItype());

		List<SubjectFour> getSubjectFourList = subjectFourRepository.findByItype(subjectFour.getItype());
		if (getSubjectFourList.isEmpty()){
			logger.info("subject four not found!");
			return new ResponseEntity<Object>("subject four not found!",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get subjectFour Successfully!");
		return new ResponseEntity<Object>(getSubjectFourList, HttpStatus.OK);
	}

	//get all subjectfour
	@RequestMapping(value = SubjectFourRestURIConstants.GET_ALL_SUBJECTFOUR, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllSubjectFour() {
		logger.info("Start getAllSubjectFour.");

		List<SubjectFour> getSubjectFourList = subjectFourRepository.findAll();
		if (getSubjectFourList.isEmpty()){
			logger.info("subject four not found!");
			return new ResponseEntity<Object>("subject four not found!",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get all subjectFour Successfully!");
		return new ResponseEntity<Object>(getSubjectFourList, HttpStatus.OK);
	}

	//get subject four exam, 45 single choice and 5 multiple choice
	@RequestMapping(value = SubjectFourRestURIConstants.GET_EXAM, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getExam() {
		logger.info("Start getExam.");
		Integer type1 = 1;
		Integer type2 = 2;
		Integer type3 = 3; //single choice with mp4
		Integer singleNum = 45;
		Integer multiNum = 5;

		List<SubjectFour> listType1 = null;
		List<SubjectFour> listType2 = null;
		List<SubjectFour> listType3 = null;
		List<SubjectFour> listExam = null;

		listType1 = subjectFourRepository.findByItype(type1);
		listType2 = subjectFourRepository.findByItype(type2);
		listType3 = subjectFourRepository.findByItype(type3);

		listType1.addAll(listType2);
		listExam = SubjectFour.pickNRandom(listType1, singleNum);
		listExam.addAll(SubjectFour.pickNRandom(listType3, multiNum));
		if (listExam.isEmpty()){
			logger.info("exam not found!");
			return new ResponseEntity<Object>("exam not found!",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get exam Successfully!");
		return new ResponseEntity<Object>(listExam, HttpStatus.OK);
	}
}