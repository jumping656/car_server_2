package example.domain;

import javax.persistence.*;

/**
 * Created by ejiping on 2016/2/19.
 */

@Entity
@Table(name = "error")
public class ErrorQuestion {

	@Id
	@GeneratedValue
	private Integer errorid;

	@Column(nullable = false) private Integer userid;
	@Column(nullable = false) private Integer subjectoneid;
	@Column(nullable = false) private Integer subjectfourid;
	@Column(nullable = true) private String dummy1;
	@Column(nullable = true) private String dummy2;

	public Integer getErrorid() {
		return errorid;
	}

	public void setErrorid(Integer errorid) {
		this.errorid = errorid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getSubjectoneid() {
		return subjectoneid;
	}

	public void setSubjectoneid(Integer subjectoneid) {
		this.subjectoneid = subjectoneid;
	}

	public Integer getSubjectfourid() {
		return subjectfourid;
	}

	public void setSubjectfourid(Integer subjectfourid) {
		this.subjectfourid = subjectfourid;
	}

	public String getDummy1() {
		return dummy1;
	}

	public void setDummy1(String dummy1) {
		this.dummy1 = dummy1;
	}

	public String getDummy2() {
		return dummy2;
	}

	public void setDummy2(String dummy2) {
		this.dummy2 = dummy2;
	}
}