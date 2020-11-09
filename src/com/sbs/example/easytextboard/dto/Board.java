package com.sbs.example.easytextboard.dto;

public class Board {

	private int id;
	private String name;
	private int lastArticleId;

	public Board() {

		lastArticleId = 0;

	}

	public int getLastArticleId() {
		return lastArticleId;
	}

	public int getBoardId() {
		return id;
	}

	public String getBoardName() {
		return name;
	}

	public void setBoardId(int id) {
		this.id = id;

	}

	public void setBoardName(String name) {
		this.name = name;
	}

	public void setLastArticleId(int lastArticleId) {
		this.lastArticleId = lastArticleId;
	}

}
