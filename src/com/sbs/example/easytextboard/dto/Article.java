package com.sbs.example.easytextboard.dto;

public class Article {
	private int number;
	private String title;
	private String body;
	private String writer;
	private int writeMemberNum;

	public int getNumber() {
		return number;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public String getWriter() {
		return writer;
	}

	public int getWriteMemberNum() {
		return writeMemberNum;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public void setWriteMemberNum(int writeMemberNum) {
		this.writeMemberNum = writeMemberNum;
	}
}
