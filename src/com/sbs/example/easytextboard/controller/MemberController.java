package com.sbs.example.easytextboard.controller;

import java.util.Scanner;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;
import com.sbs.example.easytextboard.service.*;

public class MemberController extends Controller {

	private MemberService memberService;

	public MemberController() {
		memberService = Container.memberService;
	}

	public void doCommand(Scanner sc, String command) {

		// member join
		if (command.equals("member join")) {

			int number = memberService.join(sc);
			if (number == -1) {
				System.out.println("이미 존재하는 아이디입니다.");
				return;
			}
			System.out.printf("%d번 회원으로 가입되었습니다.\n", number);

		}

		// member login
		else if (command.equals("member login")) {
			Member member;
			if (Container.session.isLogined()) {
				System.out.println("이미 로그인 상태입니다.");
				return;
			}

			member = memberService.login(sc);
			if (member == null) {
				return;
			}
			Container.session.setLoginedId(member.getNumber());
			System.out.printf("%d번 회원으로 로그인 되었습니다.\n", member.getNumber());
		}

		// member logout
		else if (command.equals("member logout")) {
			if (Container.session.isLogined()) {
				System.out.println("로그아웃 되었습니다.");
				Container.session.setLoginedId(0);
			} else if (!Container.session.isLogined()) {
				System.out.println("이미 로그아웃 상태입니다.");
			}

		}

		// member modify
		else if (command.equals("member modify")) {
			if (!Container.session.isLogined()) {
				System.out.println("로그인 후 이용해 주세요.");
				return;
			}
			String newName = "";
			System.out.println("회원이름 수정");
			System.out.printf("새 이름 : ");
			newName = sc.nextLine();
			memberService.modify(Container.session.getLoginedId(), newName);
			System.out.println("회원 정보가 수정되었습니다.");
		}

		// member whoami
		else if (command.equals("member whoami")) {
			if (!Container.session.isLogined()) {
				System.out.println("로그인 후 이용해 주세요.");
				return;
			}
			Member member = new Member();
			member = memberService.whoami();

			int id = member.getNumber();
			String loginId = member.getId();
			String name = member.getName();

			System.out.printf("회원번호 : %d\n", id);
			System.out.printf("아이디 : %s\n", loginId);
			System.out.printf("이름 : %s\n", name);
		} else {
			System.out.println("존재하지 않는 명령어");
		}

	}

}
