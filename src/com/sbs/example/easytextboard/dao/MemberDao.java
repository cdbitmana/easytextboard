package com.sbs.example.easytextboard.dao;

import java.util.ArrayList;

import com.sbs.example.easytextboard.dto.Member;

public class MemberDao {

	// 멤버 객체 ArrayList
	ArrayList<Member> members;
	private Member member;
	private int memberId;

	// 기본 생성자
	public MemberDao() {
		members = new ArrayList<Member>();

	}

	// login 메소드 : MemberService 에서 로그인이 실행되면 member필드에 로그인된 멤버의 정보를 가져옴
	public void login(Member member) {
		this.member = member;
	}

	// getMember 메소드 : member 객체를 리턴
	public Member getMember() {
		return member;
	}

	// getMemberId 메소드 : memberId 필드를 리턴
	public int getMemberId() {
		return memberId;
	}

	// memberJoin 메소드 : 멤버 회원 가입
	public void memberJoin(String id, String password, String name) {
		memberId++;
		members.add(new Member(memberId, id, password, name));

	}

	// isJoinableLoginId 메소드 : 회원가입 시 로그인 중복 검사
	public boolean isJoinableLoginId(String loginId) {
		for (Member member : members) {
			if (member.id.equals(loginId)) {
				System.out.println("이미 사용중인 아이디입니다.");
				return false;
			}
		}
		return true;
	}

	// getMemberById 메소드 : 아이디에 맞는 멤버 객체 리턴
	public Member getMemberById(String id) {
		for (Member member : members) {
			if (member.id.equals(id)) {
				return member;
			}
		}
		return null;
	}
}
