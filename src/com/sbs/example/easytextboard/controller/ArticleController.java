package com.sbs.example.easytextboard.controller;

import java.util.ArrayList;
import java.util.Scanner;

import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.dto.Article;
import com.sbs.example.easytextboard.service.ArticleService;

public class ArticleController extends Controller {

	private ArticleService articleService;

	public ArticleController() {
		articleService = Container.articleService;

	}

	public void doCommand(Scanner sc, String command) {

		// article add
		if (command.equals("article add")) {
			if (!Container.session.isLogined()) {
				System.out.println("로그인 후 이용해 주세요.");
				return;
			}

			System.out.println("== 게시물 등록 ==");
			System.out.printf("제목 : ");
			String title = sc.nextLine();
			System.out.printf("내용 : ");
			String body = sc.nextLine();

			int number = articleService.add(title, body,
					Container.memberDao.getLoginedMember(Container.session.getLoginedId()).getName(),
					Container.session.getLoginedId());

			System.out.printf("%d번 게시물이 등록되었습니다.\n", number);
		}

		// article list
		else if (command.startsWith("article list")) {
			if (articleService.getArticles().size() == 0) {
				System.out.println("게시물이 없습니다.");
				return;
			}
			String[] str = command.split(" ");
			int listNum = 0;
			try {
				listNum = Integer.parseInt(str[2]);
			} catch (NumberFormatException e) {
				System.out.println("페이지는 숫자로 입력해 주세요.");
				return;
			} catch (Exception e) {
				listNum = 1;
				articleService.printList(listNum);
				return;
			}
			if ((listNum - 1) * 10 >= articleService.getArticles().size()) {
				System.out.println("존재하지 않는 페이지입니다.");
				return;
			}

			articleService.printList(listNum);

		}

		// article detail
		else if (command.startsWith("article detail")) {
			if (articleService.getArticles().size() == 0) {
				System.out.println("게시물이 없습니다.");
				return;
			}
			String[] str = command.split(" ");
			int articleNum = 0;
			try {
				articleNum = Integer.parseInt(str[2]);
			} catch (NumberFormatException e) {
				System.out.println("게시물 번호는 숫자로 입력해 주세요.");
				return;
			} catch (Exception e) {
				System.out.println("게시물 번호를 입력해 주세요.");
				return;
			}
			if (articleNum > articleService.getArticles().size()) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}

			System.out.println("번호 / 제목 / 내용 / 작성자");
			System.out.printf("%d / %s / %s / %s\n", articleService.getArticleByIndex(articleNum - 1).getNumber(),
					articleService.getArticleByIndex(articleNum - 1).getTitle(),
					articleService.getArticleByIndex(articleNum - 1).getBody(),
					articleService.getArticleByIndex(articleNum - 1).getWriter());

		}

		// article delete
		else if (command.startsWith("article delete")) {
			if (articleService.getArticles().size() == 0) {
				System.out.println("게시물이 없습니다.");
				return;
			}
			String[] str = command.split(" ");
			int articleNum = 0;
			try {
				articleNum = Integer.parseInt(str[2]);
			} catch (NumberFormatException e) {
				System.out.println("게시물 번호는 숫자로 입력해 주세요.");
				return;
			} catch (Exception e) {
				System.out.println("게시물 번호를 입력해 주세요.");
				return;
			}
			if (articleNum > articleService.getArticles().size()) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}

			System.out.printf("%d번 게시물이 삭제되었습니다.\n", articleService.remove(articleNum));

		}

		// article modify
		else if (command.startsWith("article modify")) {
			if (articleService.getArticles().size() == 0) {
				System.out.println("게시물이 없습니다.");
				return;
			}
			String[] str = command.split(" ");
			int articleNum = 0;
			try {
				articleNum = Integer.parseInt(str[2]);
			} catch (NumberFormatException e) {
				System.out.println("게시물 번호는 숫자로 입력해 주세요.");
				return;
			} catch (Exception e) {
				System.out.println("게시물 번호를 입력해 주세요.");
				return;
			}
			if (articleNum > articleService.getArticles().size()) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}

			System.out.printf("새 제목 : ");
			String title = sc.nextLine();
			System.out.printf("새 내용 : ");
			String body = sc.nextLine();

			System.out.printf("%d번 게시물이 수정되었습니다.\n", articleService.modify(articleNum, title, body));

		}

		// article search
		else if (command.startsWith("article search")) {
			if (articleService.getArticles().size() == 0) {
				System.out.println("게시물이 없습니다.");
				return;
			}
			String[] str = command.split(" ");
			int listNum = 0;
			ArrayList<Article> searchArticles = new ArrayList<>();
			searchArticles = articleService.searchArticle(str[2]);
			try {
				listNum = Integer.parseInt(str[3]);
			} catch (NumberFormatException e) {
				System.out.println("페이지는 숫자로 입력해 주세요.");
				return;
			} catch (Exception e) {
				if (searchArticles.size() == 0) {
					System.out.println("검색 결과가 없습니다.");
					return;
				}
				listNum = 1;
				articleService.searchList(listNum, searchArticles);
				return;
			}
			if (listNum > articleService.getArticles().size()) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}

			if (searchArticles.size() == 0) {
				System.out.println("검색 결과가 없습니다.");
				return;
			}
			if ((listNum - 1) * 10 >= searchArticles.size()) {
				System.out.println("존재하지 않는 페이지입니다.");
				return;
			}
			articleService.searchList(listNum, searchArticles);

		} else {
			System.out.println("존재하지 않는 명령어");

		}

	}

}
