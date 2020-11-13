package com.sbs.example.easytextboard.controller;

import java.util.Scanner;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;
import com.sbs.example.easytextboard.service.*;

public class MemberController extends Controller {

	private MemberService memberService;
	private Scanner sc;

	public MemberController() {
		sc = Container.scanner;
		memberService = Container.memberService;
	}

	public void doCommand(String command) {

		// member join
		if (command.equals("member join")) {
			doJoin();
		}
		// member login
		else if (command.equals("member login")) {
			doLogin();
		}
		// member logout
		else if (command.equals("member logout")) {
			doLogout();
		}
		// member modify
		else if (command.equals("member modify")) {
			doModify();
		}
		// member whoami
		else if (command.equals("member whoami")) {
			doWhoami();
		} else {
			System.out.println("존재하지 않는 명령어");
		}

	}

	// doWhoami
	private void doWhoami() {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		Member member = new Member();
		member = memberService.getLoginedMember();

		int id = member.getNumber();
		String loginId = member.getId();
		String name = member.getName();

		System.out.printf("회원번호 : %d\n", id);
		System.out.printf("아이디 : %s\n", loginId);
		System.out.printf("이름 : %s\n", name);

	}

	// doModify
	private void doModify() {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		String newName = "";
		System.out.println("회원이름 수정");
		System.out.printf("새 이름 : ");
		newName = sc.nextLine();
		memberService.doModify(Container.session.getLoginedId(), newName);
		System.out.println("회원 정보가 수정되었습니다.");

	}

	// doLogout
	private void doLogout() {
		if (Container.session.isLogined()) {
			System.out.println("로그아웃 되었습니다.");
			Container.session.setLoginedId(0);
		} else if (!Container.session.isLogined()) {
			System.out.println("이미 로그아웃 상태입니다.");
		}

	}

	// doJoin
	private void doJoin() {
		Member member = new Member();
		System.out.printf("아이디 : ");
		String loginId = sc.nextLine().trim();

		member = memberService.getMemberByLoginId(loginId);

		if (member != null) {
			System.out.println("이미 존재하는 아이디입니다.");
			return;
		}

		System.out.printf("비밀번호 : ");
		String pw = sc.nextLine().trim();

		System.out.printf("이름 : ");
		String name = sc.nextLine().trim();

		int id = memberService.doJoin(loginId, pw, name);

		System.out.printf("%d번 회원으로 가입되었습니다.\n", id);

	}

	// doLogin
	private void doLogin() {
		if (Container.session.isLogined()) {
			System.out.println("이미 로그인 상태입니다.");
			return;
		}
		Member member;

		System.out.printf("아이디 : ");
		String loginId = sc.nextLine().trim();

		member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			System.out.println("일치하는 아이디가 없습니다.");
			return;
		}

		System.out.printf("비밀번호 : ");
		String pw = sc.nextLine().trim();

		if (!member.getPw().equals(pw)) {
			System.out.println("비밀번호가 맞지 않습니다.");
			return;
		}

		System.out.printf("%d번 회원으로 로그인 되었습니다.\n", member.getNumber());
		Container.session.setLoginedId(member.getNumber());

	}

}
