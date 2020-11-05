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
			int joinIdCount = 0;
			int joinPwCount = 0;
			int joinNameCount = 0;
			String id = "";
			String pw = "";
			String name = "";
			while (true) {

				if (joinIdCount >= 3) {
					System.out.println("잠시 후 다시 이용해 주세요.");
					return;
				}
				System.out.printf("아이디 : ");
				id = sc.nextLine().trim();
				if (id.length() == 0) {
					System.out.println("아이디를 입력해 주세요.");
					joinIdCount++;
					continue;
				}
				if (memberService.isExistId(id)) {
					System.out.println("이미 존재하는 아이디 입니다.");
					joinIdCount++;
					continue;
				}
				break;
			}

			while (true) {
				if (joinPwCount >= 3) {
					System.out.println("잠시 후 다시 이용해 주세요.");
					return;
				}
				System.out.printf("비밀번호 : ");
				pw = sc.nextLine();
				if (pw.length() == 0) {
					System.out.println("비밀번호를 입력해 주세요.");
					joinPwCount++;
					continue;
				}
				break;
			}
			while (true) {
				if (joinNameCount >= 3) {
					System.out.println("잠시 후 다시 이용해 주세요.");
					return;
				}
				System.out.printf("이름 : ");
				name = sc.nextLine();
				if (pw.length() == 0) {
					System.out.println("이름을 입력해 주세요.");
					joinNameCount++;
					continue;
				}
				break;
			}

			System.out.printf("%d번 회원으로 가입되었습니다.\n", memberService.join(id, pw, name));

		}

		// member login
		else if (command.equals("member login")) {
			int loginIdCount = 0;
			int loginPwCount = 0;
			Member member;
			while (true) {
				if (Container.session.isLogined()) {
					System.out.println("이미 로그인 상태입니다.");
					return;
				}

				if (loginIdCount >= 3) {
					System.out.println("잠시 후 다시 이용해 주세요.");
					return;
				}
				System.out.printf("아이디 : ");
				String id = sc.nextLine();
				if (id.length() == 0) {
					System.out.println("아이디를 입력해 주세요.");
					loginIdCount++;
					continue;
				}
				if (!memberService.isExistId(id)) {
					System.out.println("일치하는 아이디가 없습니다.");
					loginIdCount++;
					continue;
				}
				member = memberService.getMemberById(id);
				break;
			}

			while (true) {
				if (loginPwCount >= 3) {
					System.out.println("잠시 후 다시 이용해 주세요.");
					return;
				}
				System.out.printf("비밀번호 : ");
				String pw = sc.nextLine();
				if (pw.length() == 0) {
					System.out.println("비밀번호를 입력해 주세요");
					loginPwCount++;
					continue;
				}
				if (!member.getPw().equals(pw)) {
					System.out.println("비밀번호가 맞지 않습니다.");
					loginPwCount++;
					continue;
				}

				break;

			}
			Container.session.setLoginedId(member.getNumber());
			System.out.printf("%d번 회원 %s님 로그인 되었습니다.\n", member.getNumber(), member.getName());

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
			memberService.whoami();
		} else {
			System.out.println("존재하지 않는 명령어");
		}

	}

}
