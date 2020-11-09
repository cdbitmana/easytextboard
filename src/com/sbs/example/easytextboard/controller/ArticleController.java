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
		for (int i = 0; i < 5; i++) {
			add("title" + (i + 1), "body" + (i + 1), 1, 1, i);

		}
		for (int i = 5; i < 10; i++) {
			add("title" + (i + 1), "body" + (i + 1), 2, 1, i);

		}
	}

	public void doCommand(Scanner sc, String command) {

		// article add
		if (command.equals("article add")) {
			if (!Container.session.isLogined()) {
				System.out.println("로그인 후 이용해 주세요.");
				return;
			}
			for (Board board : articleService.getBoards()) {
				if (board.getBoardId() == Container.session.getSelectBoardId()) {
					this.board = board;
					break;
				}
			}

			System.out.println("== 게시물 등록 ==");
			System.out.printf("제목 : ");
			String title = sc.nextLine();
			System.out.printf("내용 : ");
			String body = sc.nextLine();

			int number = add(title, body, Container.session.getLoginedId(), Container.session.getSelectBoardId(),
					board.getLastArticleId());

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
			ArrayList<Article> articles = new ArrayList<>();
			for (Article article : articleService.getArticles()) {
				if (article.getBoardId() == Container.session.getSelectBoardId()) {
					articles.add(article);
				}
			}

			if (articles.size() == 0) {
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
			if (articleNum > articles.size()) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}

			for (Article article : articles) {
				if (article.getNumber() == articleNum) {
					Member member = memberService.getMemberByNum(article.getWriteMemberNum());
					System.out.println("번호 / 제목 / 내용 / 작성자");
					System.out.printf("%d / %s / %s / %s\n", article.getNumber(), article.getTitle(), article.getBody(),
							member.getName());
				}
			}

		}

		// article delete
		else if (command.startsWith("article delete")) {
			ArrayList<Article> articles = new ArrayList<>();
			for (Article article : articleService.getArticles()) {
				if (article.getBoardId() == Container.session.getSelectBoardId()) {
					articles.add(article);
				}
			}
			if (articles.size() == 0) {
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
			if (articleNum > articles.size()) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}

			for (Article article : articles) {
				if (article.getNumber() == articleNum) {
					System.out.printf("%d번 게시물이 삭제되었습니다.\n", articleService.remove(article));
				}
			}

		}

		// article modify
		else if (command.startsWith("article modify")) {
			ArrayList<Article> articles = new ArrayList<>();
			for (Article article : articleService.getArticles()) {
				if (article.getBoardId() == Container.session.getSelectBoardId()) {
					articles.add(article);
				}
			}
			if (articles.size() == 0) {
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
			if (articleNum > articles.size()) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}

			System.out.printf("새 제목 : ");
			String title = sc.nextLine();
			System.out.printf("새 내용 : ");
			String body = sc.nextLine();

			for (Article article : articles) {
				if (article.getNumber() == articleNum) {
					article.setTitle(title);
					article.setBody(body);
					System.out.printf("%d번 게시물이 수정되었습니다.\n", article.getNumber());
				}
			}

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
			try {
				searchArticles = articleService.searchArticle(str[2]);
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
		ArrayList<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			if (board.getBoardId() == boardNum) {
				Container.session.setSelectBoardId(boardNum);
				System.out.printf("%s(%d번) 게시판이 선택되었습니다.\n", board.getBoardName(), board.getBoardId());

				return;
			}
		}

	}

	// makeBoard 메소드
	private void makeBoard(Scanner sc) {

		ArrayList<Board> boards = articleService.getBoards();
		System.out.println("== 게시판 생성 ==");
		System.out.printf("게시판 이름 : ");
		String name = sc.nextLine();
		for (Board board : boards) {
			if (board.getBoardName().equals(name)) {
				System.out.println("이미 존재하는 게시판입니다.");
				return;
			}
		}

		int number = articleService.makeBoard(name);
		System.out.printf("%s(%d번) 게시판이 생성되었습니다.\n", name, number);
	}

	// print 메소드
	public void print(int listNum) {
		Member member;

		Board board;
		board = articleService.getBoardById(Container.session.getSelectBoardId());
		System.out.printf("== %s 리스트 ==\n", board.getBoardName());
		if (board.getLastArticleId() == 0) {
			System.out.println("게시물이 없습니다.");
			return;
		}
		System.out.println("번호 / 제목 / 작성자");

		ArrayList<Article> articles = new ArrayList<>();

		for (Article article : articleService.getArticles()) {
			if (article.getBoardId() == Container.session.getSelectBoardId()) {
				articles.add(article);
			}
		}

		int itemsInAPage = 10;
		int start = articles.size() - 1;
		start -= (listNum - 1) * itemsInAPage;
		int end = start - (itemsInAPage - 1);
		if (end < 0) {
			end = 0;
		}
		for (int i = start; i >= end; i--) {
			member = Container.memberService.getMemberByNum(articles.get(i).getWriteMemberNum());

			System.out.printf("%d / %s / %s / %s\n", articles.get(i).getNumber(), articles.get(i).getTitle(),
					member.getName(), board.getBoardName());
		}
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

	// add 메소드
	public int add(String title, String body, int writerNumber, int boardId, int lastArticleId) {
		Board board1 = null;
		ArrayList<Article> articles = articleService.getArticles();
		for (Board board : articleService.getBoards()) {
			if (board.getBoardId() == Container.session.getSelectBoardId()) {
				board1 = board;
			}
		}
		Article article = new Article();
		article.setNumber(lastArticleId + 1);
		article.setTitle(title);
		article.setBody(body);
		article.setWriteMemberNum(writerNumber);
		article.setBoardId(boardId);
		board1.setLastArticleId(article.getNumber());
		articles.add(article);

		return article.getNumber();
	}

}
