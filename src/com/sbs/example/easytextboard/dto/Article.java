package com.sbs.example.easytextboard.dto;

import java.util.Date;

public class Article {
	private int id;
	private String regDate;
	private String updateDate;
	private String title;
	private String body;
	private int writerId;
	
	private int hit;

	public Article() {

	}

	public Article(int id, String regDate, String updateDate, String title, String body, int writerId,int hit) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.body = body;
		this.writerId = writerId;
		
		this.hit = hit;
	}

	

	public String getRegDate() {
		return regDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public int getId() {
		return this.id;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public int getWriteMemberNum() {
		return writerId;
	}

	public int getArticleHit() {
		return hit;
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

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBody(String body) {
		this.body = body;
	}

	

	public void setWriteMemberNum(int writeMemberNum) {
		this.writerId = writeMemberNum;
	}

	public void setArticleHit(int hit) {
		this.hit = hit;
	}
}
