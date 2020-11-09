package com.sbs.example.easytextboard.dao;

import java.util.ArrayList;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;

public class MemberDao {

	private ArrayList<Member> members;
	int memberNum;

	public MemberDao() {
		members = new ArrayList<>();
		memberNum = 1;
		
	}

	

	public boolean isExistId(String id) {
		for (Member member : members) {
			if (member.getId().equals(id)) {

				return true;
			}
		}
		return false;
	}

	public Member getMemberByNum(int number) {
		for (Member member : members) {
			if (member.getNumber() == number) {
				return member;
			}
		}
		return null;
	}

	public Member getMemberById(String id) {
		for (Member member : members) {
			if (member.getId().equals(id)) {
				return member;
			}
		}
		return null;
	}

	public Member getLoginedMember(int loginedId) {
		for (Member member : members) {
			if (member.getNumber() == loginedId) {
				return member;
			}
		}
		return null;

	}

	public int join(String id, String pw, String name) {
		Member member = new Member();
		member.setNumber(memberNum);
		member.setId(id);
		member.setPw(pw);
		member.setName(name);

		members.add(member);
		memberNum++;
		return member.getNumber();
	}

	public void modify(int number, String newName) {
		Member member;
		member = getLoginedMember(number);
		member.setName(newName);

	}

	public void whoami() {
		for (Member member : members) {
			if (member.getNumber() == Container.session.getLoginedId()) {
				System.out.println("== 회원 정보 ==");
				System.out.println("번호 / 아이디 / 이름");
				System.out.printf("%d / %s / %s\n", member.getNumber(), member.getId(), member.getName());
			}
		}

	}

}
