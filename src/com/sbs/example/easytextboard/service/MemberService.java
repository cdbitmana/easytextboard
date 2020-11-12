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

	public Member getMemberByNum(int number) {
		return memberDao.getMemberByNum(number);
	}

	public int join(Scanner sc) {
		return memberDao.join(sc);

	}

	public void modify(int number, String newName) {
		memberDao.modify(number, newName);

	}

	public Member login(Scanner sc) {
		return memberDao.login(sc);

	}

	public Member whoami() {
		return memberDao.whoami();
	}

}
