package example.domain;

import javax.persistence.*;

/**
 * Created by ejiping on 2016/2/19.
 */

@Entity
@Table(name = "subject_one")
public class SubjectOne {

	@Id
	private Integer id;

	//the perfix a,b,c ahead the column name for Hibernate generates columns in alphabetical order
	@Column(nullable = true) private Integer achapter;
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = true) private String bquestion;
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = true) private String coption1;
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = true) private String doption2;
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = true) private String eoption3;
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = true) private String foption4;
	@Column(nullable = true) private Integer ganswer;
	@Column(nullable = true) private String himage;
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = true) private String ilink;
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = true, name = "`janalyze`") private String janalyze;   //lead to create table in mysql fail, cause analyze is a reserved word in mysql

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAchapter() {
		return achapter;
	}

	public void setAchapter(Integer achapter) {
		this.achapter = achapter;
	}

	public String getBquestion() {
		return bquestion;
	}

	public void setBquestion(String bquestion) {
		this.bquestion = bquestion;
	}

	public String getCoption1() {
		return coption1;
	}

	public void setCoption1(String coption1) {
		this.coption1 = coption1;
	}

	public String getDoption2() {
		return doption2;
	}

	public void setDoption2(String doption2) {
		this.doption2 = doption2;
	}

	public String getEoption3() {
		return eoption3;
	}

	public void setEoption3(String eoption3) {
		this.eoption3 = eoption3;
	}

	public String getFoption4() {
		return foption4;
	}

	public void setFoption4(String foption4) {
		this.foption4 = foption4;
	}

	public Integer getGanswer() {
		return ganswer;
	}

	public void setGanswer(Integer ganswer) {
		this.ganswer = ganswer;
	}

	public String getHimage() {
		return himage;
	}

	public void setHimage(String himage) {
		this.himage = himage;
	}

	public String getIlink() {
		return ilink;
	}

	public void setIlink(String ilink) {
		this.ilink = ilink;
	}

	public String getJanalyze() {
		return janalyze;
	}

	public void setJanalyze(String janalyze) {
		this.janalyze = janalyze;
	}
}