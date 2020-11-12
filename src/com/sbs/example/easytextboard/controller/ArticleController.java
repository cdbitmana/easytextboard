package com.sbs.example.easytextboard.controller;

import java.util.ArrayList;
import java.util.Scanner;

import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.dto.Article;
import com.sbs.example.easytextboard.dto.Board;
import com.sbs.example.easytextboard.dto.Member;
import com.sbs.example.easytextboard.service.ArticleService;
import com.sbs.example.easytextboard.service.MemberService;

public class ArticleController extends Controller {

	private ArticleService articleService;
	private MemberService memberService;
	private Board board;

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

			int number = articleService.add(title, body, Container.session.getLoginedId(),
					Container.session.getSelectBoardId());

			System.out.printf("%d번 게시물이 등록되었습니다.\n", number);
		}

		// article list
		else if (command.startsWith("article list")) {

			System.out.println("== 게시물 리스트 ==");
			System.out.println("번호 / 날짜 / 작성자 / 제목 / 조회수");
			print();

		}

		// article detail
		else if (command.startsWith("article detail")) {
			Article article = new Article();

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

			Member member = new Member();
			article = articleService.getArticleByNum(articleNum);
			if (article == null) {
				System.out.println("해당 게시물이 없습니다.");
				return;
			}
			int id = article.getNumber();
			String title = article.getTitle();
			String body = article.getBody();
			member = memberService.getMemberByNum(article.getWriteMemberNum());
			String writer = member.getName();
			int hit = article.getArticleHit();

			System.out.println("번호 / 제목 / 내용 / 작성자 / 조회수");
			System.out.printf("%d / %s / %s / %s / %d\n", id, title, body, writer, hit);
		}

		// article delete
		else if (command.startsWith("article delete")) {

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

			Article article = articleService.getArticleByNum(articleNum);
			if (article == null) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}
			articleService.remove(articleNum);

		}

		// article modify
		else if (command.startsWith("article modify")) {

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
			Article article = articleService.getArticleByNum(articleNum);

			if (article == null) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}

			System.out.printf("새 제목 : ");
			String title = sc.nextLine();
			System.out.printf("새 내용 : ");
			String body = sc.nextLine();

			articleService.modify(title, body, articleNum);

		}

		// article search
		else if (command.startsWith("article search")) {

			String[] str = command.split(" ");
			int listNum = 0;
			ArrayList<Article> searchArticles = new ArrayList<>();
			try {
				searchArticles = articleService.searchArticles(str[2]);
			} catch (Exception e) {
				System.out.println("존재하지 않는 명령어");
				return;
			}

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

			if (searchArticles.size() == 0) {
				System.out.println("검색 결과가 없습니다.");
				return;
			}
			if ((listNum - 1) * 10 >= searchArticles.size()) {
				System.out.println("존재하지 않는 페이지입니다.");
				return;
			}
			searchList(listNum, searchArticles);

		}
		// article makeBoard
		else if (command.equals("article makeBoard")) {
			makeBoard(sc);
		}

		// article selectBoard
		else if (command.startsWith("article selectBoard")) {
			String[] str = command.split(" ");
			int boardNum = 0;

			selectBoard(str, boardNum);

		}

		// article currentBoard
		else if (command.equals("article currentBoard")) {
			for (Board board : articleService.getBoards()) {
				if (board.getBoardId() == Container.session.getSelectBoardId()) {
					System.out.printf("현재 %s 게시판입니다.\n", board.getBoardName());
					break;
				}
			}
		}

		else {
			System.out.println("존재하지 않는 명령어");

		}

	}

	// selectBoard 메소드
	private void selectBoard(String[] str, int boardNum) {
		try {
			boardNum = Integer.parseInt(str[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시판 번호는 숫자로 입력해 주세요.");
			return;
		} catch (Exception e) {
			System.out.println("게시판 번호를 입력해 주세요.");
			return;
		}
		if (!articleService.isExistBoard(boardNum)) {
			System.out.println("게시판이 존재하지 않습니다.");
			return;
		}

		Board board = new Board();

		board = articleService.getBoardById(boardNum);

		if (board.getBoardId() == Container.session.getSelectBoardId()) {
			System.out.println("이미 선택된 게시판입니다.");
			return;
		}

		Container.session.setSelectBoardId(board.getBoardId());

		int id = board.getBoardId();
		String name = board.getBoardName();
		System.out.printf("%s(%d번) 게시판이 선택되었습니다.\n", name, id);

	}

	// makeBoard 메소드
	private void makeBoard(Scanner sc) {
		Board board = new Board();

		System.out.println("== 게시판 생성 ==");
		System.out.printf("게시판 이름 : ");
		String name = sc.nextLine();
		int boardId = 0;
		board = articleService.getBoardByName(name);
		if (board != null) {
			boardId = board.getBoardId();
		}
		if (articleService.isExistBoard(boardId)) {
			System.out.println("이미 존재하는 게시판입니다.");
			return;
		}

		int number = articleService.makeBoard(name);
		System.out.printf("%s(%d번) 게시판이 생성되었습니다.\n", name, number);
	}

	// print 메소드
	public void print() {
		articleService.printList();

	}

	// searchList 메소드
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
