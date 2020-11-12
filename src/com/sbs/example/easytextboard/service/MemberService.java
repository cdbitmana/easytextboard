package com.sbs.example.easytextboard.service;

import java.util.Scanner;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dao.*;
import com.sbs.example.easytextboard.dto.*;

public class MemberService {

	private MemberDao memberDao;

	public MemberService() {
		memberDao = Container.memberDao;
	}

	public Member getMemberById(int number) {
		return memberDao.getMemberById(number);
	}

	

	public void modify(int number, String newName) {
		memberDao.modify(number, newName);

	}

	public Member getLoginedMember() {
		return memberDao.getLoginedMember();
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public int memberJoin(String loginId, String pw, String name) {
		return memberDao.memberJoin(loginId,pw,name);
	}

}
