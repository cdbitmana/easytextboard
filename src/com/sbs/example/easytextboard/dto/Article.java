package com.sbs.example.easytextboard.dto;

import java.util.Date;

public class Article {
	private int number;
	private String regDate;
	private String title;
	private String body;
	private int writeMemberNum;
	private int boardId;

	public int getBoardId() {
		return boardId;
	}
	
	public String getRegDate() {
		return regDate;
	}

	public int getNumber() {
		return number;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public int getWriteMemberNum() {
		return writeMemberNum;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public void setWriteMemberNum(int writeMemberNum) {
		this.writeMemberNum = writeMemberNum;
	}
}
