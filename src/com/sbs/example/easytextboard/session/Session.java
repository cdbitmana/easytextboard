package com.sbs.example.easytextboard.session;

public class Session {
	private int loginedId;
	private int currentBoardId;
	private String currentBoardCode;

	public Session() {
		loginedId = 0;
		currentBoardId = 2;
		currentBoardCode = "free";
	}

	public boolean isLogined() {
		return loginedId != 0;
	}

	public void setCurrentBoardCode(String CurrentBoardCode) {
		this.currentBoardCode = CurrentBoardCode;
	}

	public void setLoginedId(int loginedId) {
		this.loginedId = loginedId;
	}
	
	public void setCurrentBoardId(int currentBoardId) {
		this.currentBoardId = currentBoardId;
	}

	public int getLoginedId() {
		return loginedId;
	}
	
	public int getCurrentBoardId() {
		return currentBoardId;
	}

	public String getCurrentBoardCode() {
		return currentBoardCode;
	}
}
