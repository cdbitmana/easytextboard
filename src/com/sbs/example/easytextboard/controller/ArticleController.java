package com.sbs.example.easytextboard.controller;

import java.util.ArrayList;

import java.util.Scanner;

import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.dto.Article;

import service.ArticleService;

public class ArticleController extends Controller {

	private ArticleService articleService;

	// 기본 생성자
	public ArticleController() {
		articleService = Container.articleService;

	}

	// run 메소드
	public void run(Scanner scanner, String command) {

		// 게시물 생성 (article add)
		if (command.equals("article add")) {
			if (!Container.session.isLogined()) {
				System.out.println("먼저 로그인을 해야 합니다.");
				return;
			}
			String title;
			String body;

			System.out.println("==게시물 등록==");
			System.out.printf("제목 : ");
			title = scanner.nextLine();
			System.out.printf("내용 : ");
			body = scanner.nextLine();

			articleService.add(title, body);
			System.out.printf("%d번 글이 등록되었습니다.\n", articleService.getLastArticleId());
		}

		// 게시물 리스트 (article list)
		else if (command.startsWith("article list")) {
			String[] page = command.split(" ");
			int listNum = 0;
			if (page.length < 3) { // 페이지를 입력 안하면 1페이지가 출력
				System.out.println("번호 / 제목 / 작성자");

				articleService.printList(articleService.getArticles(), 1);

				return;

			}
			try {
				listNum = Integer.parseInt(command.split(" ")[2]);
			} catch (NumberFormatException e) {
				System.out.println("페이지는 숫자로 입력해야 합니다.");
				return;
			}

			if (listNum <= 0 || (listNum - 1) * 10 >= articleService.getArticles().size()) {
				System.out.println("페이지가 존재하지 않습니다.");
				return;
			}

			articleService.printList(articleService.getArticles(), listNum);

		}
		// 게시물 상세 (article detail)
		else if (command.startsWith("article detail")) {
			String[] detail = command.split(" ");
			int Num = 0;
			if (detail.length < 3) {
				System.out.println("존재하지 않는 명령어");
				return;
			}
			try {
				Num = Integer.parseInt(command.split(" ")[2]);
			} catch (NumberFormatException e) {
				System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
				return;
			}

			Article article = articleService.getArticle(Num);

			if (article == null) {
				System.out.println("게시물이 존재하지 않습니다.");
				return;
			}

			System.out.println("==게시물 상세==");
			System.out.printf("번호 : %d\n", article.id);
			System.out.printf("제목 : %s\n", article.title);
			System.out.printf("내용 : %s\n", article.body);
			System.out.printf("내용 : %s\n", article.writerName);
			System.out.printf("작성일 : %s\n", article.regDate);
		}
		// 게시물 삭제 (article delete)
		else if (command.startsWith("article delete ")) {
			if (!Container.session.isLogined()) {
				System.out.println("먼저 로그인을 해야 합니다.");
				return;
			}
			String[] delete = command.split(" ");
			int Num = 0;
			if (delete.length < 3) {
				System.out.println("존재하지 않는 명령어");
				return;
			}
			try {
				Num = Integer.parseInt(command.split(" ")[2]);
			} catch (NumberFormatException e) {
				System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
				return;
			}

			Article article = articleService.getArticle(Num);
			if (article == null) {
				System.out.println("게시물이 존재하지 않습니다.");
				return;
			}
			int searchIndex = articleService.getIndexById(Num);
			articleService.getArticles().remove(searchIndex);
			System.out.printf("%d번 게시물이 삭제되었습니다.\n", Num);
		}
		// 게시물 수정 (article modify)
		else if (command.startsWith("article modify ")) {
			if (!Container.session.isLogined()) {
				System.out.println("먼저 로그인을 해야 합니다.");
				return;
			}
			String[] modify = command.split(" ");
			int Num = 0;
			if (modify.length < 3) {
				System.out.println("존재하지 않는 명령어");
				return;
			}
			try {
				Num = Integer.parseInt(command.split(" ")[2]);
			} catch (NumberFormatException e) {
				System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
				return;
			}

			Article article = articleService.getArticle(Num);
			if (article == null) {
				System.out.println("게시물이 존재하지 않습니다.");
				return;
			}

			String newTitle;
			String newBody;
			System.out.println("==게시물 수정==");
			System.out.printf("새 제목 : ");
			newTitle = scanner.nextLine();
			System.out.printf("새 제목 : ");
			newBody = scanner.nextLine();

			articleService.getArticle(Num).title = newTitle;
			articleService.getArticle(Num).body = newBody;

			System.out.println("게시물이 수정되었습니다.");
		}

		// 게시물 서치 (article search)
		else if (command.startsWith("article search")) {
			String[] search = command.split(" ");
			int Num = 0;

			ArrayList<Article> searchList = new ArrayList<Article>(); // 검색 결과만 담을 새 ArrayList 생성
			int index = 0;
			for (Article article : articleService.getArticles()) {
				if (article.title.contains(search[2])) {
					searchList.add(index, article);
					index++;
				}
			}

			if (search.length < 4) { // 페이지를 입력 안하면 검색 결과에서 1페이지가 출력
				System.out.println("번호 / 제목 / 작성자");
				for (int i = searchList.size() - 1; i >= searchList.size() - 10; i--) {
					if (i < 0) {
						break;
					}
					System.out.printf("%d / %s\n", searchList.get(i).id, searchList.get(i).title,
							searchList.get(i).writerName);

				}
				return;
			}
			try {
				Num = Integer.parseInt(command.split(" ")[3]);
			} catch (NumberFormatException e) {
				System.out.println("페이지 번호는 숫자로 입력해야 합니다.");
				return;
			}

			System.out.println("==게시물 검색==");
			if (Num <= 0 || (Num - 1) * 10 >= searchList.size()) {
				System.out.println("페이지가 존재하지 않습니다.");
				return;
			}
			System.out.println("번호 / 제목 / 작성자");
			articleService.printList(searchList, Num);

		} else {
			System.out.println("존재하지 않는 명령어");
			return;
		}

	}

}
