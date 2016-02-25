package example.domain;

import javax.persistence.*;

/**
 * Created by ejiping on 2016/2/19.
 */

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue
	private Integer userid;

	@Column(nullable = false) private String username;
	@Column(nullable = true) private String nickname;
	@Column(nullable = true) private String idcard;
	@Column(nullable = false) private String registerphone;
	@Column(nullable = false) private String password;
	@Column(nullable = true) private String realname;
	@Column(nullable = true) private String sex;
	@Column(nullable = true) private String area;
	@Column(nullable = true) private String mail;
	@Column(nullable = true) private String qq;
	@Column(nullable = true) private String phone;
	@Column(nullable = true) private String company;
	@Column(nullable = true) private Integer age;
	@Column(nullable = false) private Boolean verify;
	@Column(nullable = true) private String picture;
	@Column(nullable = true) private Integer money;
	@Column(nullable = true) private String bankaccount;
	@Column(nullable = true) private Integer sharedtime;
	@Column(nullable = true) private String lastposition;
	@Column(nullable = true) private String dummy1;
	@Column(nullable = true) private String dummy2;


	public void updateAllowedAttribute(User user){
		if (null != user.getAge()){ this.setAge(user.getAge());	}
		if (null != user.getArea()){ this.setArea(user.getArea()); }
		if (null != user.getBankaccount()){ this.setBankaccount(user.getBankaccount()); }
		if (null != user.getCompany()){ this.setCompany(user.getCompany()); }
		if (null != user.getIdcard()){ this.setIdcard(user.getIdcard()); }
		if (null != user.getLastposition()){ this.setLastposition(user.getLastposition()); }
		if (null != user.getMail()){ this.setMail(user.getMail()); }
		if (null != user.getMoney()){ this.setMoney(user.getMoney()); }
		if (null != user.getNickname()){ this.setNickname(user.getNickname()); }
		if (null != user.getPassword()){ this.setPassword(user.getPassword()); }
		if (null != user.getPhone()){ this.setPhone(user.getPhone()); }
		//if (null != user.getPicture()){ this.setPicture(user.getPicture()); }
		if (null != user.getQq()){ this.setQq(user.getQq()); }
		if (null != user.getRealname()){ this.setRealname(user.getRealname()); }
		if (null != user.getSex()){ this.setSex(user.getSex()); }
		if (null != user.getSharedtime()){ this.setSharedtime(user.getSharedtime()); }
		if (null != user.getUsername()){ this.setUsername(user.getUsername()); }
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Boolean getVerify() {
		return verify;
	}

	public void setVerify(Boolean verify) {
		this.verify = verify;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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

	public String getLastposition() {
		return lastposition;
	}

	public void setLastposition(String lastposition) {
		this.lastposition = lastposition;
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