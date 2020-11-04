package com.sbs.example.easytextboard.session;

public class Session {
	private int loginedId;

	public Session() {
		loginedId = 0;
	}

	public boolean isLogined() {
		return loginedId != 0;
	}

	public void setLoginedId(int loginedId) {
		this.loginedId = loginedId;
	}

	public int getLoginedId() {
		return loginedId;
	}
}
