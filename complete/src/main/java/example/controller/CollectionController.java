package example.controller;

import example.domain.Collection;
import example.repository.CollectionRepository;
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
public class CollectionController {

	private static final Logger logger = LoggerFactory.getLogger(CollectionController.class);

	@Autowired
	private CollectionRepository collectionRepository;

	//create subject_one collection
	@RequestMapping(value = CollectionRestURIConstants.CREATE_COLLECTION_ONE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> createCollectionForOne(@RequestBody Collection collection) {
		logger.info("Start create collection record for subject_one.");

		if (null == collection.getSubjectoneid() || collection.getSubjectoneid() <= 0 ||
				null == collection.getUserid() || collection.getUserid() <= 0) {
			logger.info("invalid collection record params");
			return new ResponseEntity<Object>("invalid collection record params",
					HttpStatus.NOT_FOUND);
		}

		List<Collection> collectionList = collectionRepository.findByUserid(collection.getUserid());
		for (int i = 0; i < collectionList.size(); i++) {
			Collection collectionIterator = collectionList.get(i);
			if (collectionIterator.getSubjectoneid() == collection.getSubjectoneid()) {
				logger.info("collection record exists.");
				return new ResponseEntity<Object>("collection record exists.",
						HttpStatus.CONFLICT);
			}
		}

		try {
			collection.setSubjectfourid(0);
			collectionRepository.save(collection);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("create collection record failed.");
			return new ResponseEntity<Object>("create collection record failed.", HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("create collection record Successfully!");
		return new ResponseEntity<Object>(collection, HttpStatus.OK);
	}

	//create subject_four error question
	@RequestMapping(value = CollectionRestURIConstants.CREATE_COLLECTION_FOUR, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> createCollectionForFour(@RequestBody Collection collection) {
		logger.info("Start creat collection for subject_four.");

		if (null == collection.getSubjectfourid() || collection.getSubjectfourid() <= 0 ||
				null == collection.getUserid() || collection.getUserid() <= 0) {
			logger.info("invalid collection record params");
			return new ResponseEntity<Object>("invalid collection record params",
					HttpStatus.NOT_FOUND);
		}

		List<Collection> collectionList = collectionRepository.findByUserid(collection.getUserid());
		for (int i = 0; i < collectionList.size(); i++) {
			Collection collectionIterator = collectionList.get(i);
			if (collectionIterator.getSubjectfourid() == collection.getSubjectfourid()) {
				logger.info("collection record exists.");
				return new ResponseEntity<Object>("collection record exists.",
						HttpStatus.CONFLICT);
			}
		}

		try {
			collection.setSubjectoneid(0);
			collectionRepository.save(collection);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("create collection record failed.");
			return new ResponseEntity<Object>("create collection record failed.", HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("create collection record Successfully!");
		return new ResponseEntity<Object>(collection, HttpStatus.OK);
	}

	//dispatch different func to handler, return status
	@RequestMapping(value = CollectionRestURIConstants.GET_COLLECTIONS, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getError(@PathVariable(value = "type") String type,
										   @RequestBody Collection collection) {
		logger.info("Start get collection records Dispath.");
		logger.info("type: " + type);

		if (null == collection) {
			logger.info("collection empty");
			return new ResponseEntity<Object>("collection empty",
					HttpStatus.NOT_FOUND);
		}

		if (type.equals(CollectionRestURIConstants.GETTYPE_COLLECTIONS_BY_ONEID)) {
			List<Collection> getCollectionList = getRecordByOneIdHandler(collection);
			if (null != getCollectionList && !getCollectionList.isEmpty()) {
				logger.info("get records successfully, return object!");
				return new ResponseEntity<Object>(getCollectionList,
						HttpStatus.OK);
			}
			return new ResponseEntity<Object>("get records fail, please check log!",
					HttpStatus.NOT_FOUND);
		} else if (type.equals(CollectionRestURIConstants.GETTYPE_COLLECTIONS_BY_FOUREID)) {
			List<Collection> getCollectionList = getRecordByFourIdHandler(collection);
			if (null != getCollectionList && !getCollectionList.isEmpty()) {
				logger.info("get records successfully, return object!");
				return new ResponseEntity<Object>(getCollectionList,
						HttpStatus.OK);
			}
			return new ResponseEntity<Object>("get records fail, please check log!!",
					HttpStatus.NOT_FOUND);
		} else if (type.equals(CollectionRestURIConstants.GETTYPE_COLLECTIONS_BY_USERID)) {
			List<Collection> getCollectionList = getRecordByUserIdHandler(collection);
			if (null != getCollectionList && !getCollectionList.isEmpty()) {
				logger.info("get records successfully, return object!");
				return new ResponseEntity<Object>(getCollectionList,
						HttpStatus.OK);
			}
			return new ResponseEntity<Object>("get records fail, please check log!!",
					HttpStatus.NOT_FOUND);
		}
		else if (type.equals(CollectionRestURIConstants.GETTYPE_COLLECTIONS_BY_COLLECTIONID)) {
			List<Collection> getCollectionList = getRecordByErrorIdHandler(collection);
			if (null != getCollectionList && !getCollectionList.isEmpty()) {
				logger.info("get records successfully, return object!");
				return new ResponseEntity<Object>(getCollectionList,
						HttpStatus.OK);
			}
			return new ResponseEntity<Object>("get records fail, please check log!!",
					HttpStatus.NOT_FOUND);
		}
		else if (type.equals(CollectionRestURIConstants.GETTYPE_COLLECTIONS_BY_USERID_AND_ONEID)) {
			List<Collection> getCollectionList = getRecordByUserIdAndOneIdHandler(collection);
			if (null != getCollectionList && !getCollectionList.isEmpty()) {
				logger.info("get records successfully, return object!");
				return new ResponseEntity<Object>(getCollectionList,
						HttpStatus.OK);
			}
			return new ResponseEntity<Object>("get records fail, please check log!!",
					HttpStatus.NOT_FOUND);
		}
		else if (type.equals(CollectionRestURIConstants.GETTYPE_COLLECTIONS_BY_USERID_AND_FOURID)) {
			List<Collection> getCollectionList = getRecordByUserIdAndFourIdHandler(collection);
			if (null != getCollectionList && !getCollectionList.isEmpty()) {
				logger.info("get records successfully, return object!");
				return new ResponseEntity<Object>(getCollectionList,
						HttpStatus.OK);
			}
			return new ResponseEntity<Object>("get records fail, please check log!!",
					HttpStatus.NOT_FOUND);
		}
		else{
			return new ResponseEntity<Object>("file type invalid",
					HttpStatus.NOT_FOUND);
		}
	}

	//delete record
	@RequestMapping(value = CollectionRestURIConstants.DELETE_COLLECTION_BY_COLLECTIONID, method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Object> deleteCollection(@RequestBody Collection collection) {
		logger.info("Start delete collection record.");

		if (null == collection.getCollectionid() || collection.getCollectionid() <= 0) {
			logger.info("collectionid invalid");
			return new ResponseEntity<Object>("collectionid invalid",
					HttpStatus.NOT_FOUND);
		}

		List<Collection> getCollectionList = collectionRepository.findByCollectionid(collection.getCollectionid());
		if (getCollectionList.isEmpty()) {
			logger.info("record not exists");
			return new ResponseEntity<Object>("record not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
			//only one record
			collectionRepository.delete(getCollectionList.get(0));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("delete record failed");
			return new ResponseEntity<Object>("delete record failed",
					HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("delete record Successfully!");
		return new ResponseEntity<Object>("record deleted", HttpStatus.OK);
	}

	//get record by oneid
	public List<Collection> getRecordByOneIdHandler(Collection collection) {
		if (null == collection.getSubjectoneid() || collection.getSubjectoneid() <= 0){
			logger.info("oneid is empty");
			return null;
		}

		List<Collection> getcollectionList = collectionRepository.findBySubjectoneid(collection.getSubjectoneid());
		if (getcollectionList.isEmpty()){
			logger.info("record not found!");
			return null;
		}

		logger.info("get records Successfully!");
		return getcollectionList;
	}

	//get record by fourid
	public List<Collection> getRecordByFourIdHandler(Collection collection) {
		if (null == collection.getSubjectfourid() || collection.getSubjectfourid() <= 0){
			logger.info("fourid is empty");
			return null;
		}

		List<Collection> getcollectionList = collectionRepository.findBySubjectfourid(collection.getSubjectfourid());
		if (getcollectionList.isEmpty()){
			logger.info("record not found!");
			return null;
		}

		logger.info("get records Successfully!");
		return getcollectionList;
	}

	//get record by userid
	public List<Collection> getRecordByUserIdHandler(Collection collection) {
		if (null == collection.getUserid() || collection.getUserid() <= 0){
			logger.info("userid is empty");
			return null;
		}

		List<Collection> getcollectionList = collectionRepository.findByUserid(collection.getUserid());
		if (getcollectionList.isEmpty()){
			logger.info("record not found!");
			return null;
		}

		logger.info("get records Successfully!");
		return getcollectionList;
	}

	//get record by errorid
	public List<Collection> getRecordByErrorIdHandler(Collection collection) {
		if (null == collection.getCollectionid() || collection.getCollectionid() <= 0){
			logger.info("errorid is empty");
			return null;
		}

		List<Collection> getcollectionList = collectionRepository.findByCollectionid(collection.getCollectionid());
		if (getcollectionList.isEmpty()){
			logger.info("record not found!");
			return null;
		}

		logger.info("get records Successfully!");
		return getcollectionList;
	}

	//get record by userid and oneid
	public List<Collection> getRecordByUserIdAndOneIdHandler(Collection collection) {
		if (null == collection.getUserid() || collection.getUserid() <= 0 ||
			null == collection.getSubjectoneid() || collection.getSubjectoneid() <= 0 ){
			logger.info("params invalid");
			return null;
		}

		List<Collection> getcollectionList = collectionRepository.findByUseridAndSubjectoneid(collection.getUserid(), collection.getSubjectoneid());
		if (getcollectionList.isEmpty()){
			logger.info("record not found!");
			return null;
		}

		logger.info("get records Successfully!");
		return getcollectionList;
	}

	//get record by userid and oneid
	public List<Collection> getRecordByUserIdAndFourIdHandler(Collection collection) {
		if (null == collection.getUserid() || collection.getUserid() <= 0 ||
				null == collection.getSubjectfourid() || collection.getSubjectfourid() <= 0 ){
			logger.info("params invalid");
			return null;
		}

		List<Collection> getcollectionList = collectionRepository.findByUseridAndSubjectfourid(collection.getUserid(), collection.getSubjectfourid());
		if (getcollectionList.isEmpty()){
			logger.info("record not found!");
			return null;
		}

		logger.info("get records Successfully!");
		return getcollectionList;
	}
}