package com.sbs.example.easytextboard.service;

import java.util.ArrayList;

import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.dao.MemberDao;
import com.sbs.example.easytextboard.dto.Member;

public class MemberService {

	MemberDao memberDao;

	// 기본 생성자
	public MemberService() {
		memberDao = Container.memberDao;
	}

	// login 메소드 : MemberService 에서 로그인이 실행되면 member필드에 로그인된 멤버의 정보를 가져옴
	public void login(Member member) {
		memberDao.login(member);
	}

	// getMember 메소드 : member 객체를 리턴
	public Member getMember() {
		return memberDao.getMember();
	}

	// getMemberId 메소드 : memberId 필드를 리턴
	public int getMemberId() {
		return memberDao.getMemberId();
	}

	// memberJoin 메소드 : 멤버 회원 가입
	public void memberJoin(String id, String password, String name) {
		memberDao.memberJoin(id, password, name);

	}

	// isJoinableLoginId 메소드 : 회원가입 시 로그인 중복 검사
	public boolean isJoinableLoginId(String loginId) {
		return memberDao.isJoinableLoginId(loginId);
	}

	// getMemberById 메소드 : 아이디로 멤버 객체 리턴
	public Member getMemberById(String id) {
		return memberDao.getMemberById(id);
	}
}
