package com.sbs.example.easytextboard.dto;

import java.util.Map;

public class GuestBook {
	private int id;
	private String regDate;
	private String updateDate;
	private String title;	
	private int memberId;		
	private String extra__writer;
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}



	public String getExtra__writer() {
		return extra__writer;
	}

	public void setExtra__writer(String extra__writer) {
		this.extra__writer = extra__writer;
	}

	public GuestBook(Map<String, Object> guestBookMap) {
		this.id = (int) guestBookMap.get("id");
		this.regDate = (String) guestBookMap.get("regDate");
		this.updateDate = (String) guestBookMap.get("updateDate");
		this.title = (String) guestBookMap.get("title");		
		this.memberId = (int) guestBookMap.get("memberId");		
		
		this.extra__writer = (String) guestBookMap.get("extra__writer");
		
	}
}
