package example.domain;

import javax.persistence.*;

/**
 * Created by ejiping on 2016/2/19.
 */

@Entity
@Table(name = "coach")
public class Coach {

	@Id
	@GeneratedValue
	private Long coachid;

	@Column(nullable = false) private String coachname;
	@Column(nullable = false) private String nickname;
	@Column(nullable = false) private String idcard;
	@Column(nullable = false) private String registerphone;
	@Column(nullable = false) private String password;
	@Column(nullable = false) private String realname;
	@Column(nullable = false) private String sex;
	@Column(nullable = false) private String area;
	@Column(nullable = false) private String mail;
	@Column(nullable = false) private String qq;
	@Column(nullable = false) private String phone;
	@Column(nullable = false) private String realage;
	@Column(nullable = false) private Integer age;
	@Column(nullable = false) private Integer carage;
	@Column(nullable = false) private String cartype;
	@Column(nullable = false) private String school;
	@Column(nullable = false) private String schooladdress;
	@Column(nullable = false) private String evaluation;   //students evaluation
	@Column(nullable = false) private Integer exam2cost;
	@Column(nullable = false) private Integer exam3cost;
	@Column(nullable = false) private Integer totalcost;
	@Column(nullable = false) private Integer introduction; //personal introduction, optional
	@Column(nullable = false) private String picture;  //picture URL
	@Column(nullable = false) private String idcardpicture;
	@Column(nullable = false) private String coachpicture;
	@Column(nullable = false) private Boolean verify; //verified or not by admin
	@Column(nullable = false) private Long money;   //students evaluation
	@Column(nullable = false) private String bankaccount;
	@Column(nullable = false) private String dummy1;
	@Column(nullable = false) private String dummy2;

	public Long getCoachid() {
		return coachid;
	}

	public void setCoachid(Long coachid) {
		this.coachid = coachid;
	}

	public String getCoachname() {
		return coachname;
	}

	public void setCoachname(String coachname) {
		this.coachname = coachname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getRegisterphone() {
		return registerphone;
	}

	public void setRegisterphone(String registerphone) {
		this.registerphone = registerphone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealage() {
		return realage;
	}

	public void setRealage(String realage) {
		this.realage = realage;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getCarage() {
		return carage;
	}

	public void setCarage(Integer carage) {
		this.carage = carage;
	}

	public String getCartype() {
		return cartype;
	}

	public void setCartype(String cartype) {
		this.cartype = cartype;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSchooladdress() {
		return schooladdress;
	}

	public void setSchooladdress(String schooladdress) {
		this.schooladdress = schooladdress;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public Integer getExam2cost() {
		return exam2cost;
	}

	public void setExam2cost(Integer exam2cost) {
		this.exam2cost = exam2cost;
	}

	public Integer getExam3cost() {
		return exam3cost;
	}

	public void setExam3cost(Integer exam3cost) {
		this.exam3cost = exam3cost;
	}

	public Integer getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(Integer totalcost) {
		this.totalcost = totalcost;
	}

	public Integer getIntroduction() {
		return introduction;
	}

	public void setIntroduction(Integer introduction) {
		this.introduction = introduction;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getIdcardpicture() {
		return idcardpicture;
	}

	public void setIdcardpicture(String idcardpicture) {
		this.idcardpicture = idcardpicture;
	}

	public String getCoachpicture() {
		return coachpicture;
	}

	public void setCoachpicture(String coachpicture) {
		this.coachpicture = coachpicture;
	}

	public Boolean getVerify() {
		return verify;
	}

	public void setVerify(Boolean verify) {
		this.verify = verify;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
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