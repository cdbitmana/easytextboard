package com.sbs.example.easytextboard.dto;

import java.util.Map;

public class Tag {
	private int id;
	private String regDate;
	private String updateDate;
	private String body;
	private String relTypeCode;
	private int relId;
	
	public Tag(Map<String,Object> tagMap) {
		this.id = (int)tagMap.get("id");
		this.regDate = (String)tagMap.get("regDate");
		this.regDate = (String)tagMap.get("updateDate");
		this.body = (String)tagMap.get("body");
		this.relTypeCode = (String)tagMap.get("relTypeCode");
		this.relId = (int)tagMap.get("relId");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getRelTypeCode() {
		return relTypeCode;
	}

	public void setRelTypeCode(String relTypeCode) {
		this.relTypeCode = relTypeCode;
	}

	public int getRelId() {
		return relId;
	}

	public void setRelId(int relId) {
		this.relId = relId;
	}
}
