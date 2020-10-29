package com.sbs.example.easytextboard.controller;

import java.util.ArrayList;
import java.util.Scanner;
import com.sbs.example.easytextboard.dto.Member;

public class MemberController extends Controller {

	// 멤버 객체 ArrayList
	ArrayList<Member> members;
	int checkId;
	int memberId;
	int memberIndex;
	boolean loginStatus = false;

	// 멤버컨트롤러 생성자
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
			int loginCount = 0;
			String loginId = "";
			String loginPassword = "";
			while (true) {
				if (loginStatus) {
					System.out.println("이미 로그인 상태입니다.");
					return;
				}

				if (loginCount >= 3) {
					System.out.println("잠시 후 다시 이용해 주세요.");
					return;
				}

				else if (!loginStatus) {

					System.out.printf("아이디 : ");
					loginId = scanner.nextLine().trim();
					if (loginId.length() == 0) {
						System.out.println("아이디를 입력해 주세요.");
						loginCount++;
						continue;
					}
					break;
				}
			}
			while (true) {
				if (loginCount >= 3) {
					System.out.println("잠시 후 다시 이용해 주세요.");
					return;
				}
				System.out.printf("비밀번호 : ");
				loginPassword = scanner.nextLine().trim();
				if (loginPassword.length() == 0) {
					System.out.println("비밀번호를 입력해 주세요.");
					loginCount++;
					continue;
				}
				break;
			}
			for (Member member : members) {
				if (member.id.equals(loginId) && member.password.equals(loginPassword)) {
					System.out.printf("%s님 환영합니다.\n", member.name);
					checkId = member.memberId;
					loginStatus = true;
					return;
				}
			}
			if (!loginStatus) {
				System.out.println("일치하는 회원이 없습니다.");
			}

		}
		// 멤버 상세
		else if (command.equals("member whoami")) {
			if (!loginStatus) {
				System.out.println("로그아웃 상태입니다.");

			} else {

				for (Member member : members) {
					if (member.memberId == checkId) {
						System.out.printf("회원번호 : %s\n", member.memberId);
						System.out.printf("아이디 : %s\n", member.id);
						System.out.printf("이름 : %s\n", member.name);

					}
				}
			}

		}
		// 멤버 로그아웃
		else if (command.equals("member logout")) {
			if (!loginStatus) {
				System.out.println("로그아웃 상태입니다.");

			} else if (loginStatus) {
				System.out.println("로그아웃 되었습니다.");
				loginStatus = false;

			}
		}

	}

}
