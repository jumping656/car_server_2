package example.controller;

import example.domain.SubjectOne;
import example.repository.SubjectOneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Object> getSubjectOneById(@RequestBody SubjectOne subjectOne) {
		if (null == subjectOne.getId() || subjectOne.getId() <= 0){
			logger.info("id is invalid");
			return new ResponseEntity<Object>("id invalid",
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getSubjectOne. id= " + subjectOne.getId());

		SubjectOne getSubjectOne = subjectOneRepository.findById(subjectOne.getId());
		if (null == getSubjectOne){
			logger.info("subject one not found!");
			return new ResponseEntity<Object>("subject one not found!",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get subjectOne Successfully!");
		return new ResponseEntity<Object>(getSubjectOne, HttpStatus.OK);
	}

	//get subjectone by chapter
	@RequestMapping(value = SubjectOneRestURIConstants.GET_SUBJECTONE_BY_CHAPTER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getSubjectOneByChapter(@RequestBody SubjectOne subjectOne) {
		if (null == subjectOne.getAchapter() || subjectOne.getAchapter() <= 0){
			logger.info("chapter id is invalid");
			return new ResponseEntity<Object>("chapter id is invalid!",
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getSubjectFour. chapter= " + subjectOne.getAchapter());

		List<SubjectOne> getSubjectOneList = subjectOneRepository.findByAchapter(subjectOne.getAchapter());
		if (getSubjectOneList.isEmpty()){
			logger.info("subject one not found!");
			return new ResponseEntity<Object>("subject one not found!",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get subjectOne Successfully!");
		return new ResponseEntity<Object>(getSubjectOneList, HttpStatus.OK);
	}

	//get all subjectone
	@RequestMapping(value = SubjectOneRestURIConstants.GET_ALL_SUBJECTONE, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllSubjectOne() {
		logger.info("Start getAllSubjectOne.");

		List<SubjectOne> getSubjectOneList = subjectOneRepository.findAll();
		if (getSubjectOneList.isEmpty()){
			logger.info("subject one not found!");
			return new ResponseEntity<Object>("subject one not found!",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get all subjectOne Successfully!");
		return new ResponseEntity<Object>(getSubjectOneList, HttpStatus.OK);
	}
}