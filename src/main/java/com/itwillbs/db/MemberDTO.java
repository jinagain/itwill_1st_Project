package com.itwillbs.db;

import java.sql.Date;

public class MemberDTO {
	private int user_id;
	private String id;
	private String password;
	private String name;
	private String nickname;
	private String phone_number;
	private String address;
	private String user_image;
	private Date regdate;
	private String birth_date; 
	private Boolean blocked;
	private int post_number;
	private String address2;
	private String email;
	private Boolean withdrawal;
	

	public Boolean getWithdrawal() {
		return withdrawal;
	}


	public void setWithdrawal(Boolean withdrawal) {
		this.withdrawal = withdrawal;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getPost_number() {
		return post_number;
	}

	public void setPost_number(int post_number) {
		this.post_number = post_number;
	}


	public String getAddress2() {
		return address2;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	@Override
	public String toString() {
		return "MemberDTO [user_id=" + user_id + ", id=" + id + ", password=" + password + ", name=" + name
				+ ", nickname=" + nickname + ", phone_number=" + phone_number + ", address=" + address + ", user_image="
				+ user_image + ", regdate=" + regdate + ", birth_date=" + birth_date + ", blocked=" + blocked + "]";
	}
	
	
	public String getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public Boolean getBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}





	
    
}