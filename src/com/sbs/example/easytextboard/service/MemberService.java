package com.sbs.example.easytextboard.service;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dao.*;
import com.sbs.example.easytextboard.dto.*;

public class MemberService {

	private MemberDao memberDao;

	public MemberService() {
		memberDao = Container.memberDao;
	}

	public boolean isExistId(String id) {
		return memberDao.isExistId(id);
	}

	public boolean isExistPw(String pw) {
		return memberDao.isExistPw(pw);
	}

	public Member getMemberById(String id) {
		return memberDao.getMemberById(id);
	}

	public int join(String id, String pw, String name) {
		return memberDao.join(id, pw, name);

	}

	public void modify(int number, String newName) {
		memberDao.modify(number, newName);

	}

	public void whoami() {
		memberDao.whoami();

	}

}
