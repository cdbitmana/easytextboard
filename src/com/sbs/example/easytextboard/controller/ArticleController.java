package com.sbs.example.easytextboard.controller;

import java.util.ArrayList;
import java.util.Scanner;

import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.dto.Article;
import com.sbs.example.easytextboard.dto.ArticleReply;
import com.sbs.example.easytextboard.dto.Board;
import com.sbs.example.easytextboard.dto.Member;
import com.sbs.example.easytextboard.service.*;

public class ArticleController extends Controller {
	private Scanner sc;
	private ArticleService articleService;
	private MemberService memberService;

	public ArticleController() {
		articleService = Container.articleService;
		memberService = Container.memberService;
		sc = Container.scanner;

	}

	public void doCommand(String command) {

		if (command.equals("article write")) {
			doWrite();
		} else if (command.startsWith("article list")) {
			showList(command);
		} else if (command.startsWith("article modify")) {
			doModify(command);
		} else if (command.startsWith("article detail")) {
			showDetail(command);
		} else if (command.startsWith("article delete")) {
			doDelete(command);
		} else if (command.startsWith("article search")) {
			showSearchList(command);
		} else if (command.startsWith("article writeReply")) {
			doWriteReply(command);
		} else if (command.startsWith("article deleteReply")) {
			doDeleteReply(command);
		} else if (command.startsWith("article recommend")) {
			doRecommend(command);
		} else if (command.startsWith("article cancelRecommend")) {
			doCancelRecommend(command);
		} else if (command.equals("article makeBoard")) {
			doMakeBoard();
		} else if (command.equals("article selectBoard")) {
			doSelectBoard();
		} else if (command.equals("article currentBoard")) {
			showCurrentBoard();
		} else {
			System.out.println("존재하지 않는 명령어");
		}
	}

	// showCurrentBoard
	private void showCurrentBoard() {
		Board board = articleService.getBoardByCode(Container.session.getCurrentBoardCode());

		System.out.printf("현재 게시판 : %s\n", board.getName());

	}

	// doSelectBoard
	private void doSelectBoard() {

		ArrayList<Board> boards = articleService.getBoardsForPrint();

		System.out.println("== 게시판 목록 ==");
		for (Board board : boards) {
			System.out.printf("%d.%s (%s)", board.getId(), board.getName(), board.getCode());
			if (board.getId() == Container.session.getCurrentBoardId()) {
				System.out.printf("  <---\n");
			} else {
				System.out.println("");
			}
		}

		System.out.printf("선택 : ");
		String inputedBoardCode = sc.nextLine();
		Board board = articleService.getBoardByCode(inputedBoardCode);
		if (board == null) {
			System.out.println("존재하지 않는 게시판입니다.");
			return;
		}
		if (inputedBoardCode.equals(Container.session.getCurrentBoardCode())) {
			System.out.printf("이미 %s 게시판입니다.\n", board.getName());
			return;
		}

		Container.session.setCurrentBoardCode(board.getCode());
		Container.session.setCurrentBoardId(board.getId());

		System.out.printf("%s(%d번) 게시판이 선택되었습니다.\n", board.getName(), board.getId());

	}

	// doMakeBoard
	private void doMakeBoard() {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}
		Member member = memberService.getLoginedMember();
		if (!member.isAdmin()) {
			System.out.println("접근 권한이 없습니다.");
			return;
		}

		System.out.println("== 게시판 생성 ==");
		System.out.printf("이름 : ");
		String name = sc.nextLine();

		System.out.printf("코드 : ");
		String code = sc.nextLine();

		int boardId = articleService.doMakeBoard(name, code);

		System.out.printf("%s(%d번) 게시판을 생성했습니다.\n", name, boardId);

	}

	// doCancelRecommend
	private void doCancelRecommend(String command) {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}
		String[] cmds = command.split(" ");
		int articleId = 0;
		try {
			articleId = Integer.parseInt(cmds[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호는 숫자로 입력해 주세요.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("게시물 번호를 입력해 주세요.");
			return;
		}
		Article article = articleService.getArticleById(articleId);
		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}
		if (!articleService.isRecommended(articleId)) {
			System.out.println("아직 추천하지 않은 게시물입니다.");
			return;
		}

		articleService.doCancelRecommend(articleId);
		System.out.printf("%d번 게시물의 추천을 취소했습니다.\n", articleId);

	}

	// doRecommend
	private void doRecommend(String command) {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}
		String[] cmds = command.split(" ");
		int articleId = 0;
		try {
			articleId = Integer.parseInt(cmds[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호는 숫자로 입력해 주세요.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("게시물 번호를 입력해 주세요.");
			return;
		}
		Article article = articleService.getArticleById(articleId);
		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}
		if (articleService.isRecommended(articleId)) {
			System.out.println("이미 추천한 게시물입니다.");
			return;
		}

		articleService.doRecommend(articleId);
		System.out.printf("%d번 게시물을 추천했습니다.\n", articleId);
	}

	// doDeleteReply
	private void doDeleteReply(String command) {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		String[] cmds = command.split(" ");
		int articleId = 0;
		int replyId = 0;
		try {
			articleId = Integer.parseInt(cmds[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호는 숫자로 입력해 주세요.");
			return;
		}
		try {
			replyId = Integer.parseInt(cmds[3]);
		} catch (NumberFormatException e) {
			System.out.println("댓글 번호는 숫자로 입력해 주세요.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("댓글 번호를 입력해 주세요.");
			return;
		}
		Article article = articleService.getArticleById(articleId);
		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}
		if (article.getMemberId() != Container.session.getLoginedId()) {
			System.out.println("본인이 작성한 댓글만 삭제할 수 있습니다.");
			return;
		}
		articleService.doDeleteReply(articleId, replyId);
		System.out.printf("%d번 게시물의 %d번 댓글을 삭제했습니다.\n", articleId, replyId);
	}

	// doWriteReply
	private void doWriteReply(String command) {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		String[] cmds = command.split(" ");
		int articleId = 0;
		try {
			articleId = Integer.parseInt(cmds[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호는 숫자로 입력해 주세요.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("게시물 번호를 입력해 주세요.");
			return;
		}
		Article article = articleService.getArticleById(articleId);
		System.out.println("== 게시물 댓글 작성 ==");
		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}
		System.out.printf("내용 : ");
		String body = sc.nextLine();
		int replyId = articleService.doWriteReply(articleId, body);

		System.out.printf("%d번 게시물에 %d번 댓글을 작성했습니다.\n", articleId, replyId);

	}

	// showSearchList
	private void showSearchList(String command) {
		String[] cmds = command.split(" ");
		int pageNumber = 0;
		try {
			pageNumber = Integer.parseInt(cmds[3]);
		} catch (NumberFormatException e) {
			System.out.println("페이지 번호는 숫자로 입력해 주세요.");
			return;

		} catch (ArrayIndexOutOfBoundsException e) {
			pageNumber = 1;
		}
		String keyword = cmds[2];
		ArrayList<Article> articles = articleService.getArticlesForPrintByKeyword(keyword);

		int itemsInAPage = 10;
		int start = 0;
		start += +(pageNumber - 1) * itemsInAPage;
		int end = start + itemsInAPage;
		if (end > articles.size()) {
			end = articles.size();
		}
		Board board = articleService.getBoardByCode(Container.session.getCurrentBoardCode());
		System.out.printf("== %s 게시물 리스트 ==\n", board.getName());
		System.out.println("번호 / 작성일 / 제목 / 내용 / 작성자 / 조회수 / 추천수");

		for (int i = start; i < end; i++) {
			int articleId = articles.get(i).getId();
			String regDate = articles.get(i).getRegDate();
			String title = articles.get(i).getTitle();
			String body = articles.get(i).getBody();
			String name = articles.get(i).getExtraWriter();
			int hit = articles.get(i).getHit();
			int Recommend = articleService.getArticleRecommend(articles.get(i).getId());
			System.out.printf("%d / %s / %s / %s / %s / %d / %d\n", articleId, regDate, title, body, name, hit,
					Recommend);
		}

	}

	// doDelete
	private void doDelete(String command) {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		String[] cmds = command.split(" ");
		int articleId = 0;
		try {
			articleId = Integer.parseInt(cmds[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호는 숫자로 입력해 주세요.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("게시물 번호를 입력해 주세요.");
			return;
		}
		Article article = articleService.getArticleById(articleId);

		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}
		if (article.getMemberId() != Container.session.getLoginedId()) {
			System.out.println("본인이 작성한 글만 삭제할 수 있습니다.");
			return;
		}
		articleService.doDelete(articleId);
		System.out.printf("%d번 게시물을 삭제했습니다.\n", articleId);

	}

	// showDetail
	private void showDetail(String command) {
		String[] cmds = command.split(" ");
		int articleId = 0;
		try {
			articleId = Integer.parseInt(cmds[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호는 숫자로 입력해 주세요.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("게시물 번호를 입력해 주세요.");
			return;
		}
		Article article = articleService.getArticleForPrintById(articleId);

		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}

		articleService.doIncreaseHit(articleId);

		int Recommend = articleService.getArticleRecommend(articleId);
		System.out.println("== 게시물 상세 ==");
		System.out.printf("번호 : %d\n", articleId);
		System.out.printf("작성일 : %s\n", article.getRegDate());
		System.out.printf("작성자 : %s\n", article.getExtraWriter());
		System.out.printf("제목 : %s\n", article.getTitle());
		System.out.printf("내용 : %s\n", article.getBody());
		System.out.printf("조회수 : %d , 추천수 : %d\n", article.getHit() + 1, Recommend);
		System.out.println("댓글");
		ArrayList<ArticleReply> replys = articleService.getReplysForPrintByArticleId(articleId);
		for (ArticleReply reply : replys) {
			System.out.printf("%d. %s - %s\n", reply.getId(), reply.getExtraName(), reply.getBody());
		}

	}

	// doModify
	private void doModify(String command) {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		String[] cmds = command.split(" ");
		int articleId = 0;
		try {
			articleId = Integer.parseInt(cmds[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호는 숫자로 입력해 주세요.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("게시물 번호를 입력해 주세요.");
			return;
		}
		Article article = articleService.getArticleById(articleId);

		System.out.println("== 게시물 수정 ==");
		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}
		if (article.getMemberId() != Container.session.getLoginedId()) {
			System.out.println("본인이 작성한 글만 수정할 수 있습니다.");
			return;
		}
		System.out.printf("새 제목 : ");
		String title = sc.nextLine();

		System.out.printf("새 내용 : ");
		String body = sc.nextLine();

		articleService.doModify(title, body, articleId);

		System.out.printf("%d번 게시물이 수정되었습니다.\n", articleId);

	}

	// showList
	private void showList(String command) {
		String[] cmds = command.split(" ");
		int pageNumber = 0;
		try {
			pageNumber = Integer.parseInt(cmds[2]);
		} catch (NumberFormatException e) {
			System.out.println("페이지 번호를 입력해 주세요.");
			return;

		} catch (ArrayIndexOutOfBoundsException e) {
			pageNumber = 1;
		}

		ArrayList<Article> articles = articleService.getArticlesForPrint();

		int itemsInAPage = 10;
		int start = 0;
		start += +(pageNumber - 1) * itemsInAPage;
		int end = start + itemsInAPage;
		if (end > articles.size()) {
			end = articles.size();
		}
		Board board = articleService.getBoardByCode(Container.session.getCurrentBoardCode());
		System.out.printf("== %s 게시물 리스트 ==\n", board.getName());
		System.out.println("번호 / 작성일 / 제목 / 내용 / 작성자 / 조회수 / 추천수");

		for (int i = start; i < end; i++) {
			int articleId = articles.get(i).getId();
			String regDate = articles.get(i).getRegDate();
			String title = articles.get(i).getTitle();
			String body = articles.get(i).getBody();
			String name = articles.get(i).getExtraWriter();
			int hit = articles.get(i).getHit();
			int Recommend = articleService.getArticleRecommend(articles.get(i).getId());
			System.out.printf("%d / %s / %s / %s / %s / %d / %d\n", articleId, regDate, title, body, name, hit,
					Recommend);
		}

	}

	// doWrite
	private void doWrite() {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}
		System.out.println("== 게시물 등록 ==");
		System.out.printf("제목 : ");
		String title = sc.nextLine();

		System.out.printf("내용 : ");
		String body = sc.nextLine();

		int memberId = Container.session.getLoginedId();
		int boardId = Container.session.getCurrentBoardId();

		int articleId = articleService.doWrite(title, body, memberId, boardId);

		System.out.printf("%d번 게시물이 등록되었습니다.\n", articleId);

	}
}