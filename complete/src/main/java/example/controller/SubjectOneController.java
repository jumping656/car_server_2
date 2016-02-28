package example.controller;

import example.domain.SubjectFour;
import example.domain.SubjectOne;
import example.repository.SubjectOneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Handles requests for the SubjectOne service.
 */

@Controller
@RestController
public class SubjectOneController {

	private static final Logger logger = LoggerFactory.getLogger(SubjectOneController.class);

	@Autowired
	private SubjectOneRepository subjectOneRepository;

	//get subjectone by id
	@RequestMapping(value = SubjectOneRestURIConstants.GET_SUBJECTONE_BY_ID, method = RequestMethod.POST)
	@ResponseBody
	public SubjectOne getSubjectOneById(@RequestBody SubjectOne subjectOne) {
		if (subjectOne.getId() <= 0 || null == subjectOne.getId()){
			logger.info("id is invalid");
			return null;
		}
		logger.info("Start getSubjectOne. id= " + subjectOne.getId());

		SubjectOne getSubjectOne = subjectOneRepository.findById(subjectOne.getId());
		if (null == getSubjectOne){
			return null;
		}

		logger.info("get subjectOne Successfully!");
		return getSubjectOne;
	}

	//get subjectone by chapter
	@RequestMapping(value = SubjectOneRestURIConstants.GET_SUBJECTONE_BY_CHAPTER, method = RequestMethod.GET)
	@ResponseBody
	public List<SubjectOne> getSubjectOneByChapter(@RequestBody SubjectOne subjectOne) {
		if (subjectOne.getAchapter() <= 0 || null == subjectOne.getAchapter()){
			logger.info("chapter id is invalid");
			return null;
		}
		logger.info("Start getSubjectFour. chapter= " + subjectOne.getAchapter());

		List<SubjectOne> getSubjectOneList = subjectOneRepository.findByAchapter(subjectOne.getAchapter());
		if (null == getSubjectOneList){
			return null;
		}

		logger.info("get subjectOne Successfully!");
		return getSubjectOneList;
	}

	//get all subjectone
	@RequestMapping(value = SubjectOneRestURIConstants.GET_ALL_SUBJECTONE, method = RequestMethod.GET)
	@ResponseBody
	public List<SubjectOne> getAllSubjectOne(@RequestBody SubjectFour subjectFour) {
		logger.info("Start getAllSubjectOne.");

		List<SubjectOne> getSubjectOneList = subjectOneRepository.findAll();
		if (null == getSubjectOneList){
			return null;
		}

		logger.info("get all subjectOne Successfully!");
		return getSubjectOneList;
	}
}