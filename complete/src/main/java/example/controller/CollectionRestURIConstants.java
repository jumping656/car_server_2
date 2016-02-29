package example.controller;

/**
 * Created by ejiping on 2016/2/21.
 */
public class CollectionRestURIConstants {

	public static final String GET_COLLECTIONS                   = "/car/collection/get/{type}";
	public static final String CREATE_COLLECTION_ONE             = "/car/collection/create/subjectone";
	public static final String CREATE_COLLECTION_FOUR            = "/car/collection/create/subjectfour";
	public static final String UPDATE_COLLECTION                 = "/car/collection/update";
	public static final String DELETE_COLLECTION_BY_COLLECTIONID = "/car/collection/delete";

	public static final String GETTYPE_COLLECTIONS_BY_ONEID             = "oneid";
	public static final String GETTYPE_COLLECTIONS_BY_FOUREID           = "fourid";
	public static final String GETTYPE_COLLECTIONS_BY_USERID            = "userid";
	public static final String GETTYPE_COLLECTIONS_BY_COLLECTIONID      = "collectionid";
	public static final String GETTYPE_COLLECTIONS_BY_USERID_AND_ONEID  = "userid_and_oneid";
	public static final String GETTYPE_COLLECTIONS_BY_USERID_AND_FOURID = "userid_and_fourid";
}
