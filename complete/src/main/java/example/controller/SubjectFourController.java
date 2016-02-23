package example.controller;

import example.domain.SubjectFour;
import example.repository.SubjectFourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	@RequestMapping(value = SubjectFourRestURIConstants.GET_SUBJECTFOUR_BY_ID, method = RequestMethod.GET)
	@ResponseBody
	public SubjectFour getSubjectFourById(@RequestBody SubjectFour subjectFour) {
		if (subjectFour.getId() <= 0 || null == subjectFour.getId()){
			logger.info("id is invalid");
			return null;
		}
		logger.info("Start getSubjectFour. id= " + subjectFour.getId());

		SubjectFour getSubjectFour = subjectFourRepository.findById(subjectFour.getId());
		if (null == getSubjectFour){
			return null;
		}

		logger.info("get subjectFour Successfully!");
		return getSubjectFour;
	}

	//get subjectfour by chapter
	@RequestMapping(value = SubjectFourRestURIConstants.GET_SUBJECTFOUR_BY_CHAPTER, method = RequestMethod.GET)
	@ResponseBody
	public List<SubjectFour> getSubjectFourByChapter(@RequestBody SubjectFour subjectFour) {
		if (subjectFour.getChapter() <= 0 || null == subjectFour.getChapter()){
			logger.info("chapter id is invalid");
			return null;
		}
		logger.info("Start getSubjectFour. chapter= " + subjectFour.getChapter());

		List<SubjectFour> getSubjectFourList = subjectFourRepository.findByChapter(subjectFour.getChapter());
		if (null == getSubjectFourList){
			return null;
		}

		logger.info("get subjectFour Successfully!");
		return getSubjectFourList;
	}

	//get subjectfour by type
	@RequestMapping(value = SubjectFourRestURIConstants.GET_SUBJECTFOUR_BY_TYPE, method = RequestMethod.GET)
	@ResponseBody
	public List<SubjectFour> getSubjectFourByType(@RequestBody SubjectFour subjectFour) {
		if (subjectFour.getType() <= 0 || null == subjectFour.getType()){
			logger.info("type id is invalid");
			return null;
		}
		logger.info("Start getSubjectFour. type= " + subjectFour.getType());

		List<SubjectFour> getSubjectFourList = subjectFourRepository.findByType(subjectFour.getType());
		if (null == getSubjectFourList){
			return null;
		}

		logger.info("get subjectFour Successfully!");
		return getSubjectFourList;
	}

	//get all subjectfour
	@RequestMapping(value = SubjectFourRestURIConstants.GET_ALL_SUBJECTFOUR, method = RequestMethod.GET)
	@ResponseBody
	public List<SubjectFour> getAllSubjectFour(@RequestBody SubjectFour subjectFour) {
		logger.info("Start getAllSubjectFour.");

		List<SubjectFour> getSubjectFourList = subjectFourRepository.findAll();
		if (null == getSubjectFourList){
			return null;
		}

		logger.info("get all subjectFour Successfully!");
		return getSubjectFourList;
	}
}