package com.sbs.example.easytextboard.dto;

import java.util.Map;

public class Board {

	private int id;
	private String name;

	public Board(Map<String, Object> boardMap) {
		this.id = (int) boardMap.get("id");
		this.name = (String) boardMap.get("name");

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

}
