package com.sbs.example.easytextboard.dto;

import java.util.Map;

public class Member {
	private int id;
	private String regDate;
	private String updateDate;
	private String loginId;
	private String loginPw;
	private String name;

	public Member(Map<String, Object> memberMap) {
		this.id = (int) memberMap.get("id");
		this.regDate = (String) memberMap.get("regDate");
		this.updateDate = (String) memberMap.get("updateDate");
		this.loginId = (String) memberMap.get("loginId");
		this.loginPw = (String) memberMap.get("loginPw");
		this.name = (String) memberMap.get("name");
	}

	public boolean isAdmin() {
		return loginId.equals("test1");
	}

	public int getId() {
		return id;
	}

	public String getRegDate() {
		return regDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getLoginPw() {
		return loginPw;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setLoginPw(String pw) {
		this.loginPw = pw;
	}

	public void setName(String name) {
		this.name = name;
	}

}
