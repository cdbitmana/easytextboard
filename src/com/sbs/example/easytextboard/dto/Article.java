package com.sbs.example.easytextboard.dto;

public class Article {
	private int number;
	private String title;
	private String body;
	private String writer;

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
}
