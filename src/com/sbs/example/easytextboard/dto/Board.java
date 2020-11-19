package com.sbs.example.easytextboard.dto;

import java.util.Map;

public class Board {

	private int id;
	private String regDate;
	private String updateDate;
	private String name;
	private String code;

	public Board(Map<String, Object> boardMap) {
		this.id = (int) boardMap.get("id");
		this.regDate = (String) boardMap.get("regDate");
		this.updateDate = (String) boardMap.get("updateDate");
		this.name = (String) boardMap.get("name");
		this.code = (String) boardMap.get("code");

	}

	public int getId() {
		return id;
	}

	public String getRegDate() {
		return regDate;
	}

	public String getupdateDate() {
		return updateDate;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
