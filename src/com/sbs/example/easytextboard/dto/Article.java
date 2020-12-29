package com.sbs.example.easytextboard.dto;

import java.util.Map;

public class Article {
	private int id;
	private String regDate;
	private String updateDate;
	private String title;
	private String body;
	private int memberId;
	private int hit;
	private int boardId;
	private int likesCount;
	private int commentsCount;
	private String extra__writer;
	private String extra__boardName;
	private String extra__boardCode;
	
	public Article(Map<String, Object> articleMap) {
		this.id = (int) articleMap.get("id");
		this.regDate = (String) articleMap.get("regDate");
		this.updateDate = (String) articleMap.get("updateDate");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.memberId = (int) articleMap.get("memberId");
		this.hit = (int) articleMap.get("hit");
		this.likesCount = (int) articleMap.get("likesCount");
		this.commentsCount = (int) articleMap.get("commentsCount");
		this.boardId = (int) articleMap.get("boardId");
		this.extra__writer = (String) articleMap.get("extra__writer");
		this.extra__boardName = (String) articleMap.get("extra__boardName");
		this.extra__boardCode = (String) articleMap.get("extra__boardCode");
	}
	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}
	public String getExtra__boardCode() {
		return extra__boardCode;
	}

	public void setExtra__boardCode(String extra__boardCode) {
		this.extra__boardCode = extra__boardCode;
	}

	public String getExtra__writer() {
		return extra__writer;
	}

	public void setExtra__writer(String extra__writer) {
		this.extra__writer = extra__writer;
	}

	public String getExtra__boardName() {
		return extra__boardName;
	}

	public void setExtra__boardName(String extra__boardName) {
		this.extra__boardName = extra__boardName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public String getRegDate() {
		return regDate;
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

	public int getMemberId() {
		return memberId;
	}

	public int getHit() {
		return hit;
	}

	public int getBoardId() {
		return boardId;
	}

	public String getExtraWriter() {
		return extra__writer;
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

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public void setExtraWriter(String extra__writer) {
		this.extra__writer = extra__writer;
	}

}
