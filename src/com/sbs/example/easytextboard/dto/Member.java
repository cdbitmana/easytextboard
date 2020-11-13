package com.sbs.example.easytextboard.dto;

public class Member {
	private int id;
	private String loginId;
	private String pw;
	private String name;

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

	public void setId(String loginId) {
		this.loginId = loginId;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public void setName(String name) {
		this.name = name;
	}

}
