package com.sbs.example.easytextboard.session;

public class Session {
	private int loginedId;
	private int selectBoardId;

	public Session() {
		loginedId = 0;
		selectBoardId = 1;
	}

	public boolean isLogined() {
		return loginedId != 0;
	}

	public void setSelectBoardId(int selectBoardId) {
		this.selectBoardId = selectBoardId;
	}

	public void setLoginedId(int loginedId) {
		this.loginedId = loginedId;
	}

	public int getLoginedId() {
		return loginedId;
	}

	public int getSelectBoardId() {
		return selectBoardId;
	}
}
