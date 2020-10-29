package com.sbs.example.easytextboard.dto;

public class Member {
	public int memberId;
	public String id;
	public String password;
	public String name;

	// 기본 생성자
	public Member(int memberId, String id, String password, String name) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.memberId = memberId;

	}
}
