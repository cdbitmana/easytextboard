package com.sbs.example.easytextboard.dto;

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

	public Article(int id, String regDate, String updateDate, String title, String body, int writerId, int hit) {
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

	public String getupdateDate() {
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

	public int getWriteMemberId() {
		return writerId;
	}

	public int getHit() {
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

	public void setWriterId(int writerId) {
		this.writerId = writerId;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

}
