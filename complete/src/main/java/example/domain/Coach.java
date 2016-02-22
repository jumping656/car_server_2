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
	private Integer coachid;

	@Column(nullable = true) private String coachname;
	@Column(nullable = true) private String nickname;
	@Column(nullable = false) private String idcard;
	@Column(nullable = false) private String registerphone;
	@Column(nullable = false) private String password;
	@Column(nullable = true) private String realname;
	@Column(nullable = true) private String sex;
	@Column(nullable = true) private String area;
	@Column(nullable = true) private String mail;
	@Column(nullable = true) private String qq;
	@Column(nullable = true) private String phone;
	@Column(nullable = true) private String realage;
	@Column(nullable = true) private Integer age;
	@Column(nullable = false) private Integer carage;
	@Column(nullable = false) private String cartype;
	@Column(nullable = false) private String school;
	@Column(nullable = false) private String schooladdress;
	@Column(nullable = false) private String evaluation;   //students evaluation
	@Column(nullable = false) private Integer exam2cost;
	@Column(nullable = false) private Integer exam3cost;
	@Column(nullable = false) private Integer totalcost;
	@Column(nullable = true) private String introduction; //personal introduction, optional
	@Column(nullable = true) private String picture;  //picture URL
	@Column(nullable = false) private String idcardpicture;
	@Column(nullable = false) private String coachpicture;
	@Column(nullable = false) private Boolean verify; //verified or not by admin
	@Column(nullable = true) private Integer money;   //students evaluation
	@Column(nullable = true) private String bankaccount;
	@Column(nullable = true) private Integer sharedtime;
	@Column(nullable = true) private String dummy1;
	@Column(nullable = true) private String dummy2;

	public void updateAllowedAttribute(Coach coach){
		if (null != coach.getAge()){ this.setAge(coach.getAge());	}
		if (null != coach.getArea()){ this.setArea(coach.getArea()); }
		if (null != coach.getBankaccount()){ this.setBankaccount(coach.getBankaccount()); }
		if (null != coach.getCarage()){ this.setCarage(coach.getCarage()); }
		if (null != coach.getIdcard()){ this.setIdcard(coach.getIdcard()); }
		if (null != coach.getCartype()){ this.setCartype(coach.getCartype()); }
		if (null != coach.getMail()){ this.setMail(coach.getMail()); }
		if (null != coach.getMoney()){ this.setMoney(coach.getMoney()); }
		if (null != coach.getNickname()){ this.setNickname(coach.getNickname()); }
		if (null != coach.getPassword()){ this.setPassword(coach.getPassword()); }
		if (null != coach.getPhone()){ this.setPhone(coach.getPhone()); }
		if (null != coach.getPicture()){ this.setPicture(coach.getPicture()); }
		if (null != coach.getQq()){ this.setQq(coach.getQq()); }
		if (null != coach.getRealname()){ this.setRealname(coach.getRealname()); }
		if (null != coach.getSex()){ this.setSex(coach.getSex()); }
		if (null != coach.getSharedtime()){ this.setSharedtime(coach.getSharedtime()); }
		if (null != coach.getCoachname()){ this.setCoachname(coach.getCoachname()); }
		if (null != coach.getSchool()){ this.setSchool(coach.getSchool()); }
		if (null != coach.getSchooladdress()){ this.setSchooladdress(coach.getSchooladdress()); }
		if (null != coach.getEvaluation()){ this.setEvaluation(coach.getEvaluation()); }
		if (null != coach.getExam2cost()){ this.setExam2cost(coach.getExam2cost()); }
		if (null != coach.getExam3cost()){ this.setExam3cost(coach.getExam3cost()); }
		if (null != coach.getTotalcost()){ this.setTotalcost(coach.getTotalcost()); }
		if (null != coach.getIntroduction()){ this.setIntroduction(coach.getIntroduction()); }
		if (null != coach.getIdcardpicture()){ this.setIdcardpicture(coach.getIdcardpicture()); }
		if (null != coach.getCoachpicture()){ this.setCoachpicture(coach.getCoachpicture()); }
	}

	public Integer getCoachid() {
		return coachid;
	}

	public void setCoachid(Integer coachid) {
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

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
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

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public Integer getSharedtime() {
		return sharedtime;
	}

	public void setSharedtime(Integer sharedtime) {
		this.sharedtime = sharedtime;
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