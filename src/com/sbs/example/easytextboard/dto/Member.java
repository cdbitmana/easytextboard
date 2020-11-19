package com.sbs.example.easytextboard.dto;

import java.util.Map;

public class Member {
	private int id;
	private String loginId;
	private String pw;
	private String name;

	public Member(Map<String, Object> boardMap) {
		this.id = (int) boardMap.get("id");
		this.loginId = (String) boardMap.get("loginId");
		this.pw = (String) boardMap.get("pw");
		this.name = (String) boardMap.get("name");
	}

	public boolean isAdmin() {
		return loginId.equals("aaa");
	}

	public int getId() {
		return id;
	}

	public String getloginId() {
		return loginId;
	}

	public String getPw() {
		return pw;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public void setName(String name) {
		this.name = name;
	}

}
