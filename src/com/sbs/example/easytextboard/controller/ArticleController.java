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
	private Scanner sc;
	private ArticleService articleService;
	private MemberService memberService;
	private Board board;

	public ArticleController() {
		articleService = Container.articleService;
		memberService = Container.memberService;
		sc = Container.scanner;

	}

	public void doCommand(String command) {

		// article add
		if (command.equals("article add")) {
			if (!Container.session.isLogined()) {
				System.out.println("로그인 후 이용해 주세요.");
				return;
			}
			articleAdd();

		}

		// article list
		else if (command.startsWith("article list")) {

			String[] str = command.split(" ");
			int listNum = 0;
			ArrayList<Article> articles = articleService.getArticles();
			try {
				listNum = Integer.parseInt(str[2]);
			} catch (NumberFormatException e) {
				System.out.println("페이지는 숫자로 입력해야 합니다.");
				return;
			} catch (ArrayIndexOutOfBoundsException e) {
				listNum = 1;
				printList(articles, listNum);
				return;
			}

			printList(articles, listNum);

		}

		// article detail
		else if (command.startsWith("article detail")) {

			String[] str = command.split(" ");
			int articleId = 0;

			try {
				articleId = Integer.parseInt(str[2]);
			} catch (NumberFormatException e) {
				System.out.println("게시물 번호는 숫자로 입력해 주세요.");
				return;
			} catch (Exception e) {
				System.out.println("게시물 번호를 입력해 주세요.");
				return;
			}

			articleDetail(command, articleId);

		}

		// article delete
		else if (command.startsWith("article delete")) {

			String[] str = command.split(" ");
			int articleId = 0;
			try {
				articleId = Integer.parseInt(str[2]);
			} catch (NumberFormatException e) {
				System.out.println("게시물 번호는 숫자로 입력해 주세요.");
				return;
			} catch (Exception e) {
				System.out.println("게시물 번호를 입력해 주세요.");
				return;
			}

			Article article = articleService.getArticleByNum(articleId);
			if (article == null) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}

			articleService.removeArticle(articleId);

		}

		// article modify
		else if (command.startsWith("article modify")) {

			String[] str = command.split(" ");
			int articleId = 0;
			try {
				articleId = Integer.parseInt(str[2]);
			} catch (NumberFormatException e) {
				System.out.println("게시물 번호는 숫자로 입력해 주세요.");
				return;
			} catch (Exception e) {
				System.out.println("게시물 번호를 입력해 주세요.");
				return;
			}
			Article article = articleService.getArticleByNum(articleId);

			if (article == null) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}

			articleModify(articleId);
			System.out.printf("%d번 게시물이 수정되었습니다.\n", articleId);
		}

		// article search
		else if (command.startsWith("article search")) {

			String[] str = command.split(" ");
			int listNum = 0;
			ArrayList<Article> searchArticles = new ArrayList<>();
			try {
				searchArticles = articleService.getArticlesByKeyword(str[2]);
			} catch (Exception e) {
				System.out.println("존재하지 않는 명령어");
				return;
			}

			try {
				listNum = Integer.parseInt(str[3]);
			} catch (NumberFormatException e) {
				System.out.println("페이지는 숫자로 입력해 주세요.");
				return;
			} catch (ArrayIndexOutOfBoundsException e) {
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
			Board board = new Board();
			board = articleService.getBoardById(Container.session.getSelectBoardId());
			String name = board.getBoardName();
			int id = board.getBoardId();
			System.out.printf("%s(%d번) 게시판\n", name, id);
		}

		else {
			System.out.println("존재하지 않는 명령어");

		}

	}

	// printList
	private void printList(ArrayList<Article> articles, int listNum) {
		Article article;
		Member member;
		int itemsInAPage = 10;
		int start = 0;
		start += (listNum - 1) * itemsInAPage;
		int end = start + (itemsInAPage - 1);
		if (end >= articles.size()) {
			end = articles.size() - 1;
		}
		System.out.println("== 게시물 리스트 ==");
		System.out.println("번호 / 날짜 / 작성자 / 제목 / 조회수");
		for (int i = start; i <= end; i++) {
			article = articles.get(i);
			member = memberService.getMemberById(article.getWriteMemberNum());
			int id = article.getId();
			String regDate = article.getRegDate();
			String nickName = member.getName();
			String title = article.getTitle();
			int hit = article.getArticleHit();
			System.out.printf(" %d / %s / %s / %s / %d\n", id, regDate, nickName, title, hit);
		}

	}

	// articleModify
	private void articleModify(int articleId) {
		System.out.printf("새 제목 : ");
		String title = sc.nextLine();
		System.out.printf("새 내용 : ");
		String body = sc.nextLine();

		articleService.modifyArticle(title, body, articleId);

	}

	// articleDetail
	private void articleDetail(String command, int articleId) {
		Article article = new Article();
		Member member = new Member();
		article = articleService.getArticleByNum(articleId);
		if (article == null) {
			System.out.println("해당 게시물이 없습니다.");
			return;
		}
		int id = article.getId();
		String title = article.getTitle();
		String body = article.getBody();
		member = memberService.getMemberById(article.getWriteMemberNum());
		String writer = member.getName();
		int hit = article.getArticleHit();

		System.out.println("번호 / 제목 / 내용 / 작성자 / 조회수");
		System.out.printf("%d / %s / %s / %s / %d\n", id, title, body, writer, hit);

	}

	// articleAdd
	private void articleAdd() {
		System.out.println("== 게시물 등록 ==");
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		int number = articleService.addArticle(title, body, Container.session.getLoginedId(),
				Container.session.getSelectBoardId());

		System.out.printf("%d번 게시물이 등록되었습니다.\n", number);
	}

	// selectBoard
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

	// makeBoard
	private void makeBoard(Scanner sc) {
		Board board = new Board();

		System.out.println("== 게시판 생성 ==");
		System.out.printf("게시판 이름 : ");
		String name = sc.nextLine();

		board = articleService.getBoardByName(name);
		if (board != null) {
			System.out.println("이미 존재하는 게시판입니다.");
			return;
		}

		int number = articleService.makeBoard(name);
		System.out.printf("%s(%d번) 게시판이 생성되었습니다.\n", name, number);
	}

	// searchList
	public void searchList(int listNum, ArrayList<Article> articles) {
		Member member;
		Article article;
		System.out.println("== 게시물 리스트 ==");

		System.out.println("번호 / 날짜 / 작성자 / 제목 / 조회수");
		int itemsInAPage = 10;
		int start = 0;
		start += (listNum - 1) * itemsInAPage;
		int end = start + (itemsInAPage - 1);
		if (end >= articles.size()) {
			end = articles.size() - 1;
		}

		for (int i = start; i <= end; i++) {
			article = articles.get(i);
			member = memberService.getMemberById(article.getWriteMemberNum());
			int id = article.getId();
			String regDate = article.getRegDate();
			String nickName = member.getName();
			String title = article.getTitle();
			int hit = article.getArticleHit();
			System.out.printf(" %d / %s / %s / %s / %d\n", id, regDate, nickName, title, hit);
		}
	}

}
