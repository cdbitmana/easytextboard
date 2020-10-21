package com.sbs.example.easytextboard;

import java.util.Scanner;

public class App {
	// 객체 배열 생성
	Article[] articleArray = new Article[100];

	int articlesCount = 0;

	// getArticle 메소드
	public Article getArticle(int id) {
		return articleArray[id - 1];
	}

	// getIndex 메소드 : 제목으로 인덱스 찾기
	public int getIndex(String str) {
		for (int i = 0; i < articlesCount; i++) {
			if (articleArray[i].title.equals(str)) {
				return i;
			}

		}
		return -1;
	}

	// removeByTitle 메소드 : 제목으로 인덱스 찾아서 remove메소드 실행
	public void removeByTitle(String str) {
		int index = getIndex(str);
		if (index != -1) {
			remove(index);
		}
	}

	// remove 메소드
	public void remove(int num) {
		for (int i = num; i <= articlesCount; i++) {
			articleArray[i - 1] = articleArray[i];

		}
		articlesCount--;
	}

	// run 메소드
	public void run() {
		for (int i = 0; i < articleArray.length; i++) {
			articleArray[i] = new Article();
		}
		int id = 0;
		int lastArticleId = 0;
		Scanner scanner = new Scanner(System.in);

		// 명령어 받아내는 반복문 시작
		while (true) {

			System.out.printf("명령어) ");
			String command = scanner.nextLine();

			// 게시물 등록
			if (command.equals("article add")) {
				id = lastArticleId + 1;
				if (articlesCount > articleArray.length) {
					System.out.println("더 이상 게시물을 생성할 수 없습니다.");
					continue;
				}

				System.out.println("==게시물 등록==");
				System.out.printf("제목 : ");
				String title = scanner.nextLine();
				System.out.printf("내용 : ");
				String body = scanner.nextLine();

				System.out.printf("%d번 게시물이 등록되었습니다.\n", id);

				articleArray[lastArticleId].id = id;
				articleArray[lastArticleId].title = title;
				articleArray[lastArticleId].body = body;

				articlesCount++;
				lastArticleId = id;

				// 게시물 리스트
			} else if (command.equals("article list")) {
				System.out.println("==게시물 리스트==");
				if (articlesCount == 0) {
					System.out.println("게시물이 존재하지 않습니다.");
					continue;
				}
				System.out.println("번호 / 제목");
				for (int i = 0; i < articlesCount; i++) {

					System.out.printf("%d / %s\n", articleArray[i].id, articleArray[i].title);

				}
				// 게시물 상세
			} else if (command.startsWith("article detail ")) {
				if (command.equals("article detail ")) {
					System.out.println("존재하지 않는 명령어");
					continue;
				}
				System.out.println("==게시물 상세==");

				String[] number = command.split(" ");

				if (Integer.parseInt(number[2]) > articlesCount || Integer.parseInt(number[2]) <= 0) {
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
			} // 프로그램 종료
			else if (command.equals("system exit")) {
				System.out.println("==프로그램 종료==");
				break;
				// 게시물 삭제
			} else if (command.startsWith("article delete ")) {
				if (command.equals("article delete ")) {
					System.out.println("존재하지 않는 명령어");
					continue;
				}
				System.out.println("==게시물 삭제==");
				String[] str = command.split(" ");
				if (Integer.parseInt(str[2]) > articlesCount || Integer.parseInt(str[2]) <= 0) {
					System.out.println("존재하지 않는 게시물은 삭제할 수 없습니다.");
					continue;
				}

				int num = Integer.parseInt(str[2]);
				System.out.printf("%d번 게시물이 삭제되었습니다.\n", num);

				remove(num);

			}

			// 존재하지 않는 명령어
			else {
				System.out.println("존재하지 않는 명령어");
			}

		}
		scanner.close();
	} // run 메소드 끝
}
