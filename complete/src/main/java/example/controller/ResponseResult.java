package example.controller;

/**
 * Created by ejiping on 2016/2/21.
 * This class is the REST API response Entity, code:operation result, result:return entity
 */
public class ResponseResult {

	static final String SUCCESS           = "200";
	static final String PARAM_ERROR       = "301"; //缺少参数，参数名不一致等
	static final String CONSISTENCY_ERROR = "401"; //一致性错误，例如数据库已经存在记录,数据库中没有记录
	static final String DB_ERROR          = "501"; //数据库操作相关错误
	static final String OPERATION_ERROR   = "601"; //程序内部错误

	private String code;
	private Object result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
