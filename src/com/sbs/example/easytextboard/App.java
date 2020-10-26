package com.sbs.example.easytextboard;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class App {

	// App 클래스의 기본 생성자
	public App() {
		init();
	}

	// 회원 객체 배열 생성
	Member[] members;
	int membersCount;
	boolean loginstatus = false;
	int id;
	// 게시물 객체 배열 생성
	ArrayList<Article> list = new ArrayList<Article>();
	int lastArticleId = 0;

	Calendar time = Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String format = format1.format(time.getTime());

	// add 메소드
	private void add(String title, String body) {
		id += 1;
		list.add(new Article(id, title, body, format));

	}

	// memberAdd 메소드
	private void memberAdd(String id, String password, String name) {

		if (membersCount >= members.length) {
			Member[] newMembers = new Member[members.length * 2];
			for (int i = 0; i < members.length; i++) {
				newMembers[i] = members[i];
			}
			members = newMembers;
		}

		Member member = new Member();
		member.id = id;
		member.password = password;
		member.name = name;

		members[membersCount] = member;
		membersCount++;

	}

	// init 메소드
	private void init() {

		members = new Member[1];
		membersCount = 0;
	}

	// run 메소드
	public void run() {

		Scanner scanner = new Scanner(System.in);

		// 명령어 받아내는 반복문 시작
		while (true) {

			System.out.printf("명령어) ");
			String command = scanner.nextLine();

			// 게시물 등록
			if (command.equals("article add")) {

				System.out.println("== 게시물 등록 ==");
				System.out.printf("제목 : ");
				String title = scanner.nextLine();
				System.out.printf("내용 : ");
				String body = scanner.nextLine();

				add(title, body);

				System.out.printf("%d번 게시물이 등록되었습니다.\n", list.get(lastArticleId).id);
				lastArticleId += 1;

				// 게시물 리스트
			} else if (command.startsWith("article list ")) {
				if (command.equals("article list")) {
					System.out.println("존재하지 않는 명령어");
					continue;
				}
				String[] str = command.split(" ");
				int listNum = Integer.parseInt(str[2]);

				System.out.println("== 게시물 리스트 ==");
				if (list.size() == 0) {
					System.out.println("게시물이 존재하지 않습니다.");
					continue;
				}

				int itemsInAPage = 10;
				int start = list.size() - 1;
				start -= (listNum - 1) * itemsInAPage;
				int end = start - (itemsInAPage - 1);
				if (end < 0) {
					end = 0;
				}
				if (list.size() < 10 * (listNum - 1) || listNum <= 0) {
					System.out.println("존재하지 않는 페이지입니다.");
					continue;
				}

				System.out.println("번호 / 제목");
				for (int i = start; i >= end; i--) {

					System.out.printf("%d / %s\n", list.get(i).id, list.get(i).title);

				}
				// 게시물 상세
			} else if (command.startsWith("article detail ")) {
				if (command.equals("article detail ")) {
					System.out.println("존재하지 않는 명령어");
					continue;
				}
				System.out.println("== 게시물 상세 ==");

				String[] number = command.split(" ");
				int detailNum = Integer.parseInt(number[2]);

				if (detailNum <= 0) {
					System.out.println("게시물이 존재하지 않습니다");
					continue;
				}

				if (detailNum > list.size()) {
					System.out.println("게시물이 존재하지 않습니다.");
					continue;
				}

				Article detailArticle = list.get(Integer.parseInt(number[2]) - 1);

				System.out.printf("번호 : %d\n", detailArticle.id);
				System.out.printf("제목 : %s\n", detailArticle.title);
				System.out.printf("내용 : %s\n", detailArticle.body);
				System.out.printf("날짜 : %s\n", detailArticle.regDate);
			} // 프로그램 종료
			else if (command.equals("system exit")) {
				System.out.println("== 프로그램 종료 ==");
				break;
				// 게시물 삭제
			} else if (command.startsWith("article delete ")) {
				if (command.equals("article delete ")) {
					System.out.println("존재하지 않는 명령어");
					continue;
				}
				System.out.println("==게시물 삭제==");
				String[] str = command.split(" ");

				int deleteNum = Integer.parseInt(str[2]);

				if (deleteNum <= 0 || deleteNum > list.size()) {
					System.out.println("존재하지 않는 게시물은 삭제할 수 없습니다.");
					continue;
				}

				System.out.printf("%d번 게시물이 삭제되었습니다.\n", deleteNum);

				list.remove(deleteNum - 1);

			} // 게시물 수정
			else if (command.startsWith("article modify ")) {

				if (command.equals("article modify")) {
					System.out.println("존재하지 않는 명령어");
					continue;
				}

				String[] str = command.split(" ");

				int modifyNum = Integer.parseInt(str[2]);

				if (modifyNum <= 0 || modifyNum > list.size()) {
					System.out.println("존재하지 않는 게시물입니다.");
					continue;
				}

				String title;
				String body;
				System.out.printf("%d번 게시물 수정\n", list.get(modifyNum - 1).id);
				System.out.printf("번호 : %d\n", list.get(modifyNum - 1).id);
				System.out.printf("새 제목 : ");
				title = scanner.nextLine();
				System.out.printf("새 내용 : ");
				body = scanner.nextLine();

				list.get(modifyNum - 1).title = title;
				list.get(modifyNum - 1).body = body;

				System.out.printf("%d번 게시물이 수정되었습니다.\n", modifyNum);

			} // 게시물 검색 (article search 키워드 페이지)
			else if (command.startsWith("article search ")) {
				if (command.equals("article search")) {
					System.out.println("존재하지 않는 명령어");
				}

				String[] keyWord = command.split(" ");

				ArrayList<Article> searchList = new ArrayList<Article>(); // 검색된 게시물만 담을 새 리스트 생성

				if (searchList.size() == 0) {
					System.out.println("검색 결과가 없습니다.");
					continue;
				}

				int searchListIndex = 0;
				for (Article article : list) { // 키워드로 검색된 게시물만 searchList 배열에 대입
					if (article.title.contains(keyWord[2])) {
						searchList.add(new Article());
						searchListIndex++;
					}
				}

				if (keyWord.length < 4) { // 페이지 없이 키워드로만 검색

					for (int i = articleSize() - 1; i >= articleSize() - 10; i--) {
						if (articles[i].title.contains(keyWord[2])) {
							System.out.printf("%d / %s\n", articles[i].id, articles[i].title);
						}
					}
					continue;
				}

				int searchNum = Integer.parseInt(keyWord[3]);

				if (searchList.length < 10 * (searchNum - 1) || searchNum <= 0) {
					System.out.println("존재하지 않는 페이지입니다.");
					continue;
				}
				int itemsInAPage = 10;
				int start = searchList.length - 1;
				start -= (searchNum - 1) * itemsInAPage;
				int end = start - (itemsInAPage - 1);
				if (end < 0) {
					end = 0;
				}
				System.out.println("==검색 결과 리스트==");
				System.out.println("번호 / 제목");

				for (int i = start; i >= end; i--) {

					System.out.printf("%d / %s\n", searchList[i].id, searchList[i].title);

				}

			} // member join 명령어
			else if (command.equals("member join")) {

				String memberId;
				String password;
				String name;

				System.out.printf("로그인 아이디 : ");
				memberId = scanner.nextLine();
				System.out.printf("로그인 비밀번호 : ");
				password = scanner.nextLine();
				System.out.printf("이름 : ");
				name = scanner.nextLine();

				memberAdd(memberId, password, name);

				System.out.println("가입이 완료되었습니다.");

			} // member login 명령어
			else if (command.equals("member login")) {
				if (loginstatus) {
					System.out.println("이미 로그인 되었습니다.");
					continue;
				}
				String memberId;
				String password;

				System.out.printf("로그인 아이디 : ");
				memberId = scanner.nextLine();
				System.out.printf("로그인 비밀번호 : ");
				password = scanner.nextLine();
				if (membersCount == 0) {
					continue;
				}

				int count = 0;
				for (int i = 0; i < members.length; i++) {
					if (members[i].id.equals(memberId) && members[i].password.equals(password)) {
						System.out.printf("%s님 환영합니다.\n", members[i].name);
						loginstatus = true;
						count++;
						continue;
					}
				}
				if (count == 0) {
					System.out.println("일치하는 회원이 없습니다.");
				}
			}
			// member logout 명령어
			else if (command.equals("member logout")) {
				if (loginstatus) {
					System.out.println("로그아웃 되었습니다.");
					loginstatus = false;
					continue;
				} else if (!loginstatus) {
					System.out.println("로그인 되지 않았습니다.");
				}
			}

			// 존재하지 않는 명령어
			else {
				System.out.println("존재하지 않는 명령어");
			}

		}
		scanner.close();
	} // run 메소드 끝
}
