package com.sbs.example.easytextboard.controller;

import java.util.ArrayList;
import java.util.Scanner;

import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.dto.Article;
import com.sbs.example.easytextboard.dto.Member;
import com.sbs.example.easytextboard.service.ArticleService;
import com.sbs.example.easytextboard.service.MemberService;

public class ArticleController extends Controller {

	private ArticleService articleService;
	private MemberService memberService;

	public ArticleController() {
		articleService = Container.articleService;
		memberService = Container.memberService;
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
				print(listNum);
				return;
			}
			if ((listNum - 1) * 10 >= articleService.getArticles().size()) {
				System.out.println("존재하지 않는 페이지입니다.");
				return;
			}

			print(listNum);

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
			Member member;
			Article article = articleService.getArticleByNum(articleNum);
			member = memberService.getMemberByNum(article.getWriteMemberNum());
			System.out.println("번호 / 제목 / 내용 / 작성자");
			System.out.printf("%d / %s / %s / %s\n", articleService.getArticleByNum(articleNum).getNumber(),
					articleService.getArticleByNum(articleNum).getTitle(),
					articleService.getArticleByNum(articleNum).getBody(), member.getName());

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
				searchList(listNum, searchArticles);
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
			searchList(listNum, searchArticles);

		} else {
			System.out.println("존재하지 않는 명령어");

		}

	}

	public void print(int listNum) {
		Member member;
		Article article;
		System.out.println("== 게시물 리스트 ==");

		System.out.println("번호 / 제목 / 작성자");
		int itemsInAPage = 10;
		int start = articleService.getArticles().size() - 1;
		start -= (listNum - 1) * itemsInAPage;
		int end = start - (itemsInAPage - 1);
		if (end < 0) {
			end = 0;
		}
		for (int i = start; i >= end; i--) {
			article = articleService.getArticleByIndex(i);
			member = memberService.getMemberByNum(article.getWriteMemberNum());
			System.out.printf(" %d / %s / %s\n", article.getNumber(), article.getTitle(), member.getName());
		}
	}

	public void searchList(int listNum, ArrayList<Article> articles) {
		Member member;
		Article article;
		System.out.println("== 게시물 리스트 ==");

		System.out.println("번호 / 제목 / 작성자");
		int itemsInAPage = 10;
		int start = articles.size() - 1;
		start -= (listNum - 1) * itemsInAPage;
		int end = start - (itemsInAPage - 1);
		if (end < 0) {
			end = 0;
		}

		for (int i = start; i >= end; i--) {
			article = articles.get(i);
			member = memberService.getMemberByNum(article.getWriteMemberNum());
			System.out.printf(" %d / %s / %s\n", articles.get(i).getNumber(), articles.get(i).getTitle(),
					member.getName());
		}
	}

}
