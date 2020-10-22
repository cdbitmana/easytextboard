package com.sbs.example.easytextboard;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class App {
	public App() {
		init();
	}

	// 객체 배열 생성
	Article[] articles;
	int articlesCount;
	int lastArticleId;

	Calendar time = Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String format = format1.format(time.getTime());

	// getArticle 메소드 : 번호로 게시물 찾기
	private Article getArticle(int id) {
		int index = getIndex(id);
		if (index == -1) {
			return null;
		}
		return articles[index];
	}

	// getIndex 메소드 : 번호로 인덱스 찾기
	private int getIndex(int id) {
		for (int i = 0; i < articlesCount; i++) {
			if (articles[i].id == id) {

				return i;
			}

		}
		return -1;
	}

	// articleSize 메소드
	int articleSize() {
		return articlesCount;
	}

	// remove 메소드
	private void remove(int id) {
		int index = getIndex(id);
		if (index == -1) {
			return;
		}
		for (int i = index + 1; i < articleSize(); i++) {

			articles[i - 1] = articles[i];

		}
		articlesCount--;

	}

	// isArticlesFull 메소드
	private boolean isArticlesFull() {
		return articleSize() >= articles.length;
	}

	// add 메소드
	private int add(String title, String body) {
		if (isArticlesFull()) {
			Article[] newArticles = new Article[articles.length * 2];
			for (int i = 0; i < articles.length; i++) {
				newArticles[i] = articles[i];
			}
			articles = newArticles;
		}

		Article article = new Article();
		article.id = lastArticleId + 1;
		article.title = title;
		article.body = body;
		article.regDate = format;

		articles[articleSize()] = article;
		articlesCount++;
		lastArticleId = article.id;
		return article.id;
	}

	// init 메소드
	private void init() {
		articles = new Article[32];
		articlesCount = 0;
		lastArticleId = 0;
		for (int i = 1; i <= 32; i++) {
			add("title" + (lastArticleId + 1), "body" + (lastArticleId + 1));

		}
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

				int id = add(title, body);

				System.out.printf("%d번 게시물이 등록되었습니다.\n", id);

				// 게시물 리스트
			} else if (command.startsWith("article list ")) {
				if (command.equals("article list")) {
					System.out.println("존재하지 않는 명령어");
					continue;
				}
				String[] str = command.split(" ");
				int listNum = Integer.parseInt(str[2]);

				System.out.println("== 게시물 리스트 ==");
				if (articleSize() == 0) {
					System.out.println("게시물이 존재하지 않습니다.");
					continue;
				}
				if (articleSize() < 10 * (listNum - 1) || listNum <= 0) {
					System.out.println("존재하지 않는 페이지입니다.");
					continue;
				}

				System.out.println("번호 / 제목");
				for (int i = articleSize() - 10 * (listNum - 1); i > (articleSize() - 10 * (listNum - 1)) - 10; i--) {
					if (i == 0) {
						break;
					}

					System.out.printf("%d / %s\n", articles[i - 1].id, articles[i - 1].title);

				}
				// 게시물 상세
			} else if (command.startsWith("article detail ")) {
				if (command.equals("article detail ")) {
					System.out.println("존재하지 않는 명령어");
					continue;
				}
				System.out.println("== 게시물 상세 ==");

				String[] number = command.split(" ");

				if (Integer.parseInt(number[2]) <= 0) {
					System.out.println("게시물이 존재하지 않습니다");
					continue;
				}

				Article detailArticle = getArticle(Integer.parseInt(number[2]));

				if (detailArticle == null) {
					System.out.println("게시물이 존재하지 않습니다.");
					continue;
				}

				System.out.printf("번호 : %d\n", detailArticle.id);
				System.out.printf("제목 : %s\n", detailArticle.title);
				System.out.printf("내용 : %s\n", detailArticle.body);
				System.out.printf("내용 : %s\n", detailArticle.regDate);
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
				Article deleteArticle = getArticle(Integer.parseInt(str[2]));
				if (Integer.parseInt(str[2]) <= 0 || deleteArticle == null) {
					System.out.println("존재하지 않는 게시물은 삭제할 수 없습니다.");
					continue;
				}

				int num = Integer.parseInt(str[2]);
				System.out.printf("%d번 게시물이 삭제되었습니다.\n", num);

				remove(num);

			} // 게시물 수정
			else if (command.startsWith("article modify ")) {

				if (command.equals("article modify")) {
					System.out.println("존재하지 않는 명령어");
					continue;
				}

				String[] str = command.split(" ");

				int num = Integer.parseInt(str[2]);
				Article article = getArticle(Integer.parseInt(str[2]));

				if (num <= 0 || article == null) {
					System.out.println("존재하지 않는 게시물입니다.");
					continue;
				}

				String title;
				String body;
				System.out.printf("%d번 게시물 수정\n", article.id);
				System.out.printf("번호 : %d\n", article.id);
				System.out.printf("새 제목 : ");
				title = scanner.nextLine();
				System.out.printf("새 내용 : ");
				body = scanner.nextLine();

				articles[getIndex(num)].title = title;
				articles[getIndex(num)].body = body;
				System.out.printf("%d번 게시물이 수정되었습니다.\n", num);

			} // 게시물 검색
			else if (command.startsWith("article search ")) {
				String[] keyWord = command.split(" ");

				for (int i = 0; i < articleSize(); i++) {

					if (articles[i].title.contains(keyWord[2])) {
						System.out.printf("%d / %s\n", articles[i].id, articles[i].title);
					}
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
