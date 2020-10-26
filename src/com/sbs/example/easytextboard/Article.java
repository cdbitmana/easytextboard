package com.sbs.example.easytextboard;

public class Article {
	int id;
	String title;
	String body;
	public String regDate;

	public Article(int id, String title, String body, String format) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.regDate = format;
	}
}
