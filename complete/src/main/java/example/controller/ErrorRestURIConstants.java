package example.controller;

/**
 * Created by ejiping on 2016/2/21.
 */
public class ErrorRestURIConstants {

	public static final String GET_ERRORS              = "/car/error/get/{type}";
	public static final String CREATE_ERROR_ONE        = "/car/error/create/subjectone";
	public static final String CREATE_ERROR_FOUR       = "/car/error/create/subjectfour";
	public static final String UPDATE_ERROR            = "/car/error/update";
	public static final String DELETE_ERROR_BY_ERRORID = "/car/error/delete";

	public static final String GETTYPE_ERRORS_BY_ONEID             = "oneid";
	public static final String GETTYPE_ERRORS_BY_FOUREID           = "fourid";
	public static final String GETTYPE_ERRORS_BY_USERID            = "userid";
	public static final String GETTYPE_ERRORS_BY_ERRORID           = "errorid";
	public static final String GETTYPE_ERRORS_BY_USERID_AND_ONEID  = "userid_and_oneid";
	public static final String GETTYPE_ERRORS_BY_USERID_AND_FOURID = "userid_and_fourid";
}
