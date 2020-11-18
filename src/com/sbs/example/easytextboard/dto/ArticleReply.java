package com.sbs.example.easytextboard.dto;

import java.util.Map;

public class ArticleReply {

	private int id;
	private String regDate;
	private String body;
	private int articleId;
	private int boardId;
	private int memberId;

	public ArticleReply(Map<String, Object> articleReplyMap) {
		this.id = (int) articleReplyMap.get("id");
		this.regDate = (String) articleReplyMap.get("regDate");
		this.body = (String) articleReplyMap.get("body");
		this.articleId = (int) articleReplyMap.get("articleId");
		this.boardId = (int) articleReplyMap.get("boardId");
		this.memberId = (int) articleReplyMap.get("memberId");
	}

	public int getId() {
		return id;
	}

	public String getRegDate() {
		return regDate;
	}

	public String getBody() {
		return body;
	}

	public int getArticleId() {
		return articleId;
	}

	public int getboardId() {
		return boardId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

}
