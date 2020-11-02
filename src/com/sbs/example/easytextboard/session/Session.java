package com.sbs.example.easytextboard.session;

import com.sbs.example.easytextboard.dto.*;

public class Session {

	public int loginedMemberId;
	public String loginId;
	public String loginName;

	public boolean isLogined() {

		return loginedMemberId != 0;

	}
	

}
