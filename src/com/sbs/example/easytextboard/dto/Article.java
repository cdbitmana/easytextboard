package com.sbs.example.easytextboard.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.sbs.example.easytextboard.container.Container;

public class Article {
	public int id;
	public String title;
	public String body;
	public String regDate;
	public String writerName;

	Calendar time = Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String format = format1.format(time.getTime());

	// 기본 생성자
	public Article(int id, String title, String body, String writerName) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.regDate = format;
		this.writerName = writerName;
	}
}
