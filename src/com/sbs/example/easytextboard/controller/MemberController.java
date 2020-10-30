package com.sbs.example.easytextboard.controller;

import java.util.ArrayList;
import java.util.Scanner;

import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.dto.Member;

public class MemberController extends Controller {

	// 멤버 객체 ArrayList
	ArrayList<Member> members;
	Member member;
	int memberId;
	boolean loginStatus = false;

	// 기본 생성자
	public MemberController() {
		members = new ArrayList<Member>();

	}

	// memberJoin 메소드
	public void memberJoin(String id, String password, String name) {
		memberId++;
		members.add(new Member(memberId, id, password, name));

	}

	// isJoinableLoginId 메소드
	public boolean isJoinableLoginId(String loginId) {
		for (Member member : members) {
			if (member.id.equals(loginId)) {
				System.out.println("이미 사용중인 아이디입니다.");
				return false;
			}
		}
		return true;
	}

	// getMemberById 메소드
	public Member getMemberById(String id) {
		for (Member member : members) {
			if (member.id.equals(id)) {
				return member;
			}
		}
		return null;
	}

	// run 메소드
	public void run(Scanner scanner, String command) {

		// 멤버 가입
		if (command.equals("member join")) {
			int joinIdCount = 0;
			String id;
			String password;
			String name;
			while (true) {
				if (joinIdCount >= 3) {
					System.out.println("잠시 후 다시 이용해 주세요.");
					return;
				}
				System.out.printf("아이디 : ");
				id = scanner.nextLine().trim();
				if (id.length() == 0) {
					System.out.println("아이디를 입력해 주세요.");
					joinIdCount++;
					continue;
				}
				if (!isJoinableLoginId(id)) {
					joinIdCount++;
					continue;
				}
				break;
			}
			while (true) {
				if (joinIdCount >= 3) {
					System.out.println("잠시 후 다시 이용해 주세요.");
					return;
				}
				System.out.printf("비밀번호 : ");
				password = scanner.nextLine().trim();
				if (password.length() == 0) {
					System.out.println("비밀번호를 입력해 주세요.");
					joinIdCount++;
					continue;
				}
				break;
			}
			while (true) {
				if (joinIdCount >= 3) {
					System.out.println("잠시 후 다시 이용해 주세요.");
					return;
				}
				System.out.printf("이름 : ");
				name = scanner.nextLine().trim();
				if (name.length() == 0) {
					System.out.println("이름을 입력해 주세요.");
					joinIdCount++;
					continue;
				}
				break;
			}

			memberJoin(id, password, name);
			System.out.printf("%d번 회원이 생성되었습니다.\n", memberId);
		}

		// 멤버 로그인
		else if (command.equals("member login")) {
			int loginIdCount = 0;
			int loginPwCount = 0;
			String loginId = "";
			String loginPassword = "";

			boolean idCheck = false;
			boolean pwCheck = false;
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
				loginId = scanner.nextLine().trim();
				if (loginId.length() == 0) {
					System.out.println("아이디를 입력해 주세요.");
					loginIdCount++;
					continue;
				}

				member = getMemberById(loginId);

				if (member == null) {
					System.out.println("존재하지 않는 아이디입니다.");
					loginIdCount++;
					continue;
				}
				idCheck = true;
				break;

			}
			while (true) {
				if (loginPwCount >= 3) {
					System.out.println("잠시 후 다시 이용해 주세요.");
					return;
				}
				System.out.printf("비밀번호 : ");
				loginPassword = scanner.nextLine().trim();
				if (loginPassword.length() == 0) {
					System.out.println("비밀번호를 입력해 주세요.");
					loginPwCount++;
					continue;
				}
				if (!member.password.equals(loginPassword)) {
					System.out.println("비밀번호가 일치하지 않습니다.");
					loginPwCount++;
					continue;
				}
				pwCheck = true;
				break;
			}
			if (idCheck && pwCheck) {
				System.out.printf("%d번 회원이 로그인 되었습니다.\n", member.memberId);
				Container.session.loginedMemberId = member.memberId;
				return;
			}

		}
		// 멤버 상세
		else if (command.equals("member whoami")) {

			if (!Container.session.isLogined()) {
				System.out.println("로그아웃 상태입니다.");

			} else {

				System.out.printf("회원번호 : %s\n", member.memberId);
				System.out.printf("아이디 : %s\n", member.id);
				System.out.printf("이름 : %s\n", member.name);

			}

		}
		// 멤버 로그아웃
		else if (command.equals("member logout")) {
			if (!Container.session.isLogined()) {
				System.out.println("로그아웃 상태입니다.");

			} else if (Container.session.isLogined()) {
				System.out.println("로그아웃 되었습니다.");
				Container.session.loginedMemberId = 0;

			}
		} else {
			System.out.println("존재하지 않는 명령어");
			return;
		}

	}

}
