package com.sbs.example.easytextboard.dto;

import java.util.Map;

public class Board {

	private int id;
	private String name;
	private int articleId;

	public Board(Map<String, Object> boardMap) {
		this.id = (int) boardMap.get("id");
		this.name = (String) boardMap.get("name");
		this.articleId = (int) boardMap.get("articleId");
	}

	public int getBoardId() {
		return id;
	}

	public String getBoardName() {
		return name;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setBoardId(int id) {
		this.id = id;

	}

	public void setBoardName(String name) {
		this.name = name;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

}
