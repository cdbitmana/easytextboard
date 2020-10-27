package com.sbs.example.easytextboard;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class App {

	// 기본 생성자
	public App() {
		inIt();
	}

	// 날짜 입력 클래스

	// 게시물 객체 ArrayList
	ArrayList<Article> articles = new ArrayList<Article>();
	int lastArticleId = 0;

	// 멤버 객체 ArrayList
	ArrayList<Member> members = new ArrayList<Member>();
	int checkId;
	int memberId;
	int memberIndex;
	boolean loginStatus = false;

	// add 메소드
	private void add(String title, String body) {
		lastArticleId += 1;
		articles.add(new Article(lastArticleId, title, body));
	}

	// printList 메소드
	private void printList(ArrayList<Article> list, int listNum) {
		int itemsInAPage = 10;
		int start = list.size() - 1;
		start -= itemsInAPage * (listNum - 1);
		int end = start - (itemsInAPage - 1);
		if (end < 0) {
			end = 0;
		}

		for (int i = start; i >= end; i--) {
			System.out.printf("%d / %s\n", list.get(i).id, list.get(i).title);

		}
	}

	int[] result;

	private int[] asd() {
		result = new int[] { 1 };
		return result;
	}

	// getIndexById 메소드
	private int getIndexById(int id) {
		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).id == id) {
				return i;
			}
		}
		return -1;
	}

	// getArticle 메소드
	private Article getArticle(int id) {
		int index = getIndexById(id);
		if (index == -1) {
			return null;
		}
		return articles.get(index);
	}

	// memberJoin 메소드
	private void memberJoin(String id, String password, String name) {
		memberId++;
		members.add(new Member(memberId, id, password, name));
		memberIndex++;

	}

	// inIt 메소드
	private void inIt() {
		for (int i = 0; i < 32; i++) {
			add("title" + (lastArticleId + 1), "body" + (lastArticleId + 1));
		}
	}

	// run 메소드
	public void run() {

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.printf("명령어) ");
			String command = scanner.nextLine();

			// 게시물 등록
			if (command.equals("article add")) {

				String title;
				String body;

				System.out.println("==게시물 등록==");
				System.out.printf("제목 : ");
				title = scanner.nextLine();
				System.out.printf("내용 : ");
				body = scanner.nextLine();

				add(title, body);
				System.out.printf("%d번 글이 등록되었습니다.\n", lastArticleId);
			}

			// 게시물 리스트
			else if (command.startsWith("article list")) {
				String[] page = command.split(" ");
				int listNum = 0;
				if (page.length < 3) { // 페이지를 입력 안하면 1페이지가 출력
					System.out.println("번호 / 제목");
					for (int i = articles.size() - 1; i >= articles.size() - 10; i--) {
						if (i < 0) {
							break;
						}
						System.out.printf("%d / %s\n", articles.get(i).id, articles.get(i).title);

					}
					continue;
				}
				try {
					listNum = Integer.parseInt(command.split(" ")[2]);
				} catch (NumberFormatException e) {
					System.out.println("페이지는 숫자로 입력해야 합니다.");
					continue;
				}

				if (listNum <= 0 || (listNum - 1) * 10 >= articles.size()) {
					System.out.println("페이지가 존재하지 않습니다.");
					continue;
				}

				printList(articles, listNum);

			}
			// 게시물 상세
			else if (command.startsWith("article detail ")) {
				String[] detail = command.split(" ");
				int Num = 0;
				if (detail.length < 3) {
					System.out.println("존재하지 않는 명령어");
					continue;
				}
				try {
					Num = Integer.parseInt(command.split(" ")[2]);
				} catch (NumberFormatException e) {
					System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
					continue;
				}

				Article article = getArticle(Num);

				if (article == null) {
					System.out.println("게시물이 존재하지 않습니다.");
					continue;
				}

				System.out.println("==게시물 상세==");
				System.out.printf("번호 : %d\n", article.id);
				System.out.printf("제목 : %s\n", article.title);
				System.out.printf("내용 : %s\n", article.body);
				System.out.printf("작성일 : %s\n", article.regDate);

			}
			// 게시물 삭제
			else if (command.startsWith("article delete ")) {
				String[] delete = command.split(" ");
				int Num = 0;
				if (delete.length < 3) {
					System.out.println("존재하지 않는 명령어");
					continue;
				}
				try {
					Num = Integer.parseInt(command.split(" ")[2]);
				} catch (NumberFormatException e) {
					System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
					continue;
				}

				Article article = getArticle(Num);
				if (article == null) {
					System.out.println("게시물이 존재하지 않습니다.");
					continue;
				}
				int searchIndex = getIndexById(Num);
				articles.remove(searchIndex);
				System.out.printf("%d번 게시물이 삭제되었습니다.\n", Num);
			}

			// 게시물 수정
			else if (command.startsWith("article modify ")) {
				String[] modify = command.split(" ");
				int Num = 0;
				if (modify.length < 3) {
					System.out.println("존재하지 않는 명령어");
					continue;
				}
				try {
					Num = Integer.parseInt(command.split(" ")[2]);
				} catch (NumberFormatException e) {
					System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
					continue;
				}

				Article article = getArticle(Num);
				if (article == null) {
					System.out.println("게시물이 존재하지 않습니다.");
					continue;
				}

				String newTitle;
				String newBody;
				System.out.println("==게시물 수정==");
				System.out.printf("새 제목 : ");
				newTitle = scanner.nextLine();
				System.out.printf("새 제목 : ");
				newBody = scanner.nextLine();

				int searchIndex = getIndexById(Num);

				articles.set(searchIndex, new Article(Num, newTitle, newBody));

				System.out.println("게시물이 수정되었습니다.");
			}

			// 게시물 서치
			else if (command.startsWith("article search")) {
				String[] search = command.split(" ");
				int Num = 0;

				ArrayList<Article> searchList = new ArrayList<Article>(); // 검색 결과만 담을 새 ArrayList 생성
				int index = 0;
				for (Article article : articles) {
					if (article.title.contains(search[2])) {
						searchList.add(index, article);
						index++;
					}
				}

				if (search.length < 4) { // 페이지를 입력 안하면 검색 결과에서 1페이지가 출력
					System.out.println("번호 / 제목");
					for (int i = searchList.size() - 1; i >= searchList.size() - 10; i--) {
						if (i < 0) {
							break;
						}
						System.out.printf("%d / %s\n", searchList.get(i).id, searchList.get(i).title);

					}
					continue;
				}
				try {
					Num = Integer.parseInt(command.split(" ")[3]);
				} catch (NumberFormatException e) {
					System.out.println("페이지 번호는 숫자로 입력해야 합니다.");
					continue;
				}

				System.out.println("==게시물 검색==");
				if (Num <= 0 || (Num - 1) * 10 >= searchList.size()) {
					System.out.println("페이지가 존재하지 않습니다.");
					continue;
				}
				System.out.println("번호 / 제목");
				printList(searchList, Num);

			}

			// 멤버 조인
			else if (command.equals("member join")) {
				String id;
				String password;
				String name;

				System.out.printf("아이디 : ");
				id = scanner.nextLine();
				System.out.printf("비밀번호 : ");
				password = scanner.nextLine();
				System.out.printf("이름 : ");
				name = scanner.nextLine();
				memberJoin(id, password, name);
				System.out.printf("%s님 회원 가입이 완료되었습니다.\n", name);
			}
			// 멤버 로그인
			else if (command.equals("member login")) {
				if (loginStatus) {
					System.out.println("이미 로그인 상태입니다.");
					continue;
				}
				String loginId;
				String loginPassword;
				System.out.printf("아이디 : ");
				loginId = scanner.nextLine();
				System.out.printf("비밀번호 : ");
				loginPassword = scanner.nextLine();

				for (Member member : members) {
					if (member.id.equals(loginId) && member.password.equals(loginPassword)) {
						System.out.printf("%s님 환영합니다.\n", member.name);
						checkId = member.memberId;
						loginStatus = true;
					}
				}

				if (!loginStatus) {
					System.out.println("일치하는 회원이 없습니다.");
					continue;
				}
			}
			// 멤버 상세
			else if (command.equals("member whoami")) {
				if (!loginStatus) {
					System.out.println("로그아웃 상태입니다.");
					continue;
				}

				for (Member member : members) {
					if (member.memberId == checkId) {
						System.out.printf("아이디 : %s\n", member.id);
						System.out.printf("이름 : %s\n", member.name);

					}
				}

			}
			// 멤버 로그아웃
			else if (command.equals("member logout")) {
				if (!loginStatus) {
					System.out.println("로그아웃 상태입니다.");
					continue;
				}

				else if (loginStatus) {
					System.out.println("로그아웃 되었습니다.");
					loginStatus = false;
					continue;
				}
			}

			// 프로그램 종료
			else if (command.equals("system exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}

			// 존재하지 않는 명령어
			else {
				System.out.println("존재하지 않는 명령어");
				continue;
			}

		}
		scanner.close();
	}
}