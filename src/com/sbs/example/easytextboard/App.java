package com.sbs.example.easytextboard;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class App {

	// 객체 배열 생성
	Article[] articleArray = new Article[3];

	int articlesCount = 0;

	// getArticle 메소드
	public Article getArticle(int id) {
		int index = getIndex(id);
		if (index == -1) {
			return null;
		}
		return articleArray[index];
	}

	// getIndex 메소드 : 번호로 인덱스 찾기
	public int getIndex(int id) {
		for (int i = 0; i < articlesCount; i++) {
			if (articleArray[i].id == id) {

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
	public void remove(int id) {
		int index = getIndex(id);
		if (index == -1) {
			return;
		}
		for (int i = index + 1; i < articleSize(); i++) {

			articleArray[i - 1] = articleArray[i];

		}
		articlesCount--;

	}

	Calendar time = Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// run 메소드
	public void run() {

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

				String format = format1.format(time.getTime());

				if (articleSize() >= articleArray.length) {
					System.out.println("더 이상 게시물을 생성할 수 없습니다.");
					continue;
				}

				System.out.println("== 게시물 등록 ==");
				System.out.printf("제목 : ");
				String title = scanner.nextLine();
				System.out.printf("내용 : ");
				String body = scanner.nextLine();

				Article article = new Article();

				System.out.printf("%d번 게시물이 등록되었습니다.\n", id);

				article.id = id;
				article.title = title;
				article.body = body;
				article.regDate = format;

				articleArray[articleSize()] = article;
				articlesCount++;
				lastArticleId = id;

				// 게시물 리스트
			} else if (command.equals("article list")) {
				System.out.println("== 게시물 리스트 ==");
				if (articleSize() == 0) {
					System.out.println("게시물이 존재하지 않습니다.");
					continue;
				}
				System.out.println("번호 / 제목");
				for (int i = articleSize() - 1; i >= 0; i--) {

					System.out.printf("%d / %s\n", articleArray[i].id, articleArray[i].title);

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

				articleArray[getIndex(num)].title = title;
				articleArray[getIndex(num)].body = body;
				System.out.printf("%d번 게시물이 수정되었습니다.\n", num);

			} // 게시물 검색
			else if (command.startsWith("article search ")) {
				String[] keyWord = command.split(" ");

				for (int i = 0; i < articleSize(); i++) {
					if (articleArray[i].title.contains(keyWord[2]) && i == articleArray.length - 1) {
						System.out.printf("%d / %s\n", articleArray[i].id, articleArray[i].title);
						break;

					}
					if (articleArray[i].title.contains(keyWord[2]) && articleArray[i] != articleArray[i + 1]) {
						System.out.printf("%d / %s\n", articleArray[i].id, articleArray[i].title);
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
