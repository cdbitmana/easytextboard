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

		// article add
		if (command.equals("article add")) {
			doAdd();
		}
		// article list
		else if (command.startsWith("article list")) {
			showList(command);
		}

		// article detail
		else if (command.startsWith("article detail")) {
			showDetail(command);
		}
		// article recommand
		else if (command.startsWith("article recommand")) {
			doRecommand(command);
		}
		// article cancelRecommand
		else if (command.startsWith("article cancelRecommand")) {
			doCancelRecommand(command);
		}
		// article writeReply
		else if (command.startsWith("article writeReply")) {
			doWriteReply(command);
		}
		// article modifyReply
		else if (command.startsWith("article modifyReply")) {
			doModifyReply(command);
		}
		// article deleteReply
		else if (command.startsWith("article deleteReply")) {
			doDeleteReply(command);
		}
		// article delete
		else if (command.startsWith("article delete")) {
			doDelete(command);
		}
		// article modify
		else if (command.startsWith("article modify")) {
			doModify(command);
		}
		// article search
		else if (command.startsWith("article search")) {
			showSearchList(command);
		}
		// article makeBoard
		else if (command.equals("article makeBoard")) {
			doMakeBoard(sc);
		}
		// article selectBoard
		else if (command.startsWith("article selectBoard")) {
			doSelectBoard(command);
		}
		// article currentBoard
		else if (command.equals("article currentBoard")) {
			showCurrentBoard();
		} else {
			System.out.println("존재하지 않는 명령어");
		}

	}

	// doCancelRecommand
	private void doCancelRecommand(String command) {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		String[] commands = command.split(" ");
		int articleId = 0;
		try {
			articleId = Integer.parseInt(commands[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("게시물 번호를 입력해야 합니다.");
			return;
		}
		Article article = articleService.getArticleById(articleId);
		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}
		int memberId = articleService.getMemberIdRecommand(articleId);
		if (memberId != Container.session.getLoginedId()) {
			System.out.println("아직 추천하지 않은 게시물입니다.");
			return;
		}

		articleService.doCancelRecommand(articleId);
		System.out.printf("%d번 게시물의 추천을 취소합니다.\n", articleId);
	}

	// doRecommand
	private void doRecommand(String command) {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		String[] commands = command.split(" ");
		int articleId = 0;
		try {
			articleId = Integer.parseInt(commands[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("게시물 번호를 입력해야 합니다.");
			return;
		}
		Article article = articleService.getArticleById(articleId);
		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}

		int memberId = articleService.getMemberIdRecommand(articleId);
		if (memberId == Container.session.getLoginedId()) {
			System.out.println("이미 추천한 게시물입니다.");
			return;
		}
		articleService.doRecommand(articleId);

		System.out.printf("%d번 게시물을 추천하였습니다.\n", articleId);

	}

	// doDeleteReply
	private void doDeleteReply(String command) {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		String[] commands = command.split(" ");
		int articleId = 0;
		int replyId = 0;
		try {
			articleId = Integer.parseInt(commands[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("게시물 번호를 입력해야 합니다.");
			return;
		}

		try {
			replyId = Integer.parseInt(commands[3]);
		} catch (NumberFormatException e) {
			System.out.println("댓글 번호는 숫자로 입력해야 합니다.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("댓글 번호를 입력해야 합니다.");
			return;
		}
		ArticleReply articleReply = articleService.getArticleReplyById(articleId, replyId);

		if (articleReply == null) {
			System.out.println("해당하는 댓글이 없습니다.");
			return;
		}

		if (articleReply.getMemberId() != Container.session.getLoginedId()) {
			System.out.println("본인이 작성한 댓글만 수정할 수 있습니다.");
			return;

		}

		articleService.doDeleteReply(articleId, replyId);
		System.out.printf("%d번 게시물의 %d번 댓글이 삭제되었습니다.\n", articleId, replyId);
	}

	// doModifyReply
	private void doModifyReply(String command) {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		String[] commands = command.split(" ");
		int articleId = 0;
		int replyId = 0;
		try {
			articleId = Integer.parseInt(commands[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("게시물 번호를 입력해야 합니다.");
			return;
		}

		try {
			replyId = Integer.parseInt(commands[3]);
		} catch (NumberFormatException e) {
			System.out.println("댓글 번호는 숫자로 입력해야 합니다.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("댓글 번호를 입력해야 합니다.");
			return;
		}

		ArticleReply articleReply = articleService.getArticleReplyById(articleId, replyId);

		if (articleReply == null) {
			System.out.println("해당하는 댓글이 없습니다.");
			return;
		}

		if (articleReply.getMemberId() != Container.session.getLoginedId()) {
			System.out.println("본인이 작성한 댓글만 수정할 수 있습니다.");
			return;
		}

		System.out.printf("새 댓글 내용 : ");
		String newBody = sc.nextLine();

		articleService.doModifyReply(articleId, replyId, newBody);

		System.out.printf("%d번 게시물의 %d번 댓글이 수정되었습니다.\n", articleId, replyId);

	}

	// doWriteReply
	private void doWriteReply(String command) {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		String[] commands = command.split(" ");
		int articleId = 0;
		try {
			articleId = Integer.parseInt(commands[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("게시물 번호를 입력해야 합니다.");
			return;
		}

		Article article = articleService.getArticleById(articleId);
		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}
		System.out.printf("내용 : ");
		String reply = sc.nextLine();

		int replyId = articleService.doWriteReply(reply, articleId);

		System.out.printf("%d번 글에 %d번 댓글이 추가되었습니다.\n", articleId, replyId);

	}

	// showCurrentBoard
	private void showCurrentBoard() {

		Board board = articleService.getBoardById(Container.session.getSelectBoardId());

		System.out.printf("현재 %s(%d번) 게시판입니다.\n", board.getBoardName(), board.getBoardId());

	}

	// doSelectBoard
	private void doSelectBoard(String command) {

		String[] commands = command.split(" ");
		int id = 0;
		try {
			id = Integer.parseInt(commands[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시판 번호는 숫자로 입력해야 합니다.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("게시판 번호를 입력해야 합니다.");
			return;
		}

		Board board = articleService.getBoardById(id);

		if (Container.session.getSelectBoardId() == id) {
			System.out.printf("이미 %s(%d번) 게시판입니다.\n", board.getBoardName(), board.getBoardId());
			return;
		}

		Container.session.setSelectBoardId(board.getBoardId());

		System.out.printf("%s(%d번) 게시판이 선택되었습니다.\n", board.getBoardName(), board.getBoardId());

	}

	// showSearchList
	private void showSearchList(String command) {

		String[] commands = command.split(" ");
		int pageNumber = 0;
		ArrayList<Article> searchArticles = new ArrayList<>();

		searchArticles = articleService.getArticlesByKeyword(commands[2]);
		if (searchArticles.size() == 0) {
			System.out.println("검색 결과가 없습니다.");
			return;
		}
		try {
			pageNumber = Integer.parseInt(commands[3]);
		} catch (NumberFormatException e) {
			System.out.println("페이지는 숫자로 입력해 주세요.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {

			pageNumber = 1;
			showAPage(pageNumber, searchArticles);
			return;
		}

		if (searchArticles.size() == 0) {
			System.out.println("검색 결과가 없습니다.");
			return;
		}
		if ((pageNumber - 1) * 10 >= searchArticles.size()) {
			System.out.println("존재하지 않는 페이지입니다.");
			return;
		}

		showAPage(pageNumber, searchArticles);

	}

	// doModify
	private void doModify(String command) {
		String[] commands = command.split(" ");
		int articleId = 0;
		try {
			articleId = Integer.parseInt(commands[2]);
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

		if (Container.session.getLoginedId() != article.getWriteMemberId()) {
			System.out.println("본인이 작성한 글만 수정할 수 있습니다.");
			return;
		}

		System.out.printf("새 제목 : ");
		String title = sc.nextLine();

		System.out.printf("새 내용 : ");
		String body = sc.nextLine();

		articleService.doModify(articleId, title, body);
		System.out.printf("%d번 게시물이 수정되었습니다.\n", articleId);

	}

	// doDelete
	private void doDelete(String command) {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}

		String[] commands = command.split(" ");
		int articleId = 0;
		try {
			articleId = Integer.parseInt(commands[2]);
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

		if (Container.session.getLoginedId() != article.getWriteMemberId()) {
			System.out.println("본인이 작성한 글만 삭제할 수 있습니다.");
			return;
		}

		articleService.doDelete(articleId);
		System.out.printf("%d번 게시물이 삭제되었습니다.\n", articleId);

	}

	// showDetail
	private void showDetail(String command) {

		String[] commands = command.split(" ");
		int articleId = 0;

		try {
			articleId = Integer.parseInt(commands[2]);
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
		articleService.doCountHit(articleId);
		ArrayList<ArticleReply> replys = articleService.getReplysByArticleId(articleId);
		int recommand = articleService.getRecommand(articleId);
		System.out.println("== 게시물 상세 ==");
		System.out.printf("번호 : %d\n", article.getId());
		System.out.printf("작성일 : %s\n", article.getRegDate());
		System.out.printf("수정일 : %s\n", article.getupdateDate());
		System.out.printf("작성자 : %s\n", article.getExtraWriter());
		System.out.printf("제목 : %s\n", article.getTitle());
		System.out.printf("내용 : %s\n", article.getBody());
		System.out.printf("조회수 : %d\n", article.getHit() + 1);
		System.out.printf("추천수 : %d\n", recommand);
		System.out.println("댓글");
		if (replys == null) {
			System.out.printf("\n");
		} else {
			for (int i = 0; i < replys.size(); i++) {
				System.out.printf(" %s - %d. %s \n", replys.get(i).getExtraName(), replys.get(i).getId(),
						replys.get(i).getBody());
			}

		}

	}

	// showList
	private void showList(String cmd) {

		ArrayList<Article> articles = articleService.getArticles();

		String[] commands = cmd.split(" ");
		int pageNumber = 0;
		try {
			pageNumber = Integer.parseInt(commands[2]);
		} catch (NumberFormatException e) {
			System.out.println("페이지는 숫자로 입력해야 합니다.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			pageNumber = 1;
			showAPage(pageNumber, articles);
			return;
		}

		System.out.println("== 게시물 리스트 ==");

		showAPage(pageNumber, articles);

	}

	// showAPage
	private void showAPage(int pageNumber, ArrayList<Article> articles) {
		int itemsInAPage = 10;
		int start = 0;
		start += (pageNumber - 1) * itemsInAPage;
		int end = start + (itemsInAPage - 1);
		if (end >= articles.size()) {
			end = articles.size() - 1;
		}
		System.out.println("번호 / 작성날짜 / 작성자 / 제목 / 내용 / 조회수 / 추천수");
		for (int i = start; i <= end; i++) {
			int id = articles.get(i).getId();
			String regDate = articles.get(i).getRegDate();
			String name = articles.get(i).getExtraWriter();
			String title = articles.get(i).getTitle();
			String body = articles.get(i).getBody();
			int hit = articles.get(i).getHit();
			int recommand = articleService.getRecommand(articles.get(i).getId());
			System.out.printf("%d / %s / %s / %s / %s / %d / %d\n", id, regDate, name, title, body, hit, recommand);
		}
	}

	// doAdd
	private void doAdd() {
		if (!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		System.out.println("== 게시물 등록 ==");
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		int number = articleService.doAdd(title, body, Container.session.getLoginedId(),
				Container.session.getSelectBoardId());

		System.out.printf("%d번 게시물이 등록되었습니다.\n", number);
	}

	// doMakeBoard
	private void doMakeBoard(Scanner sc) {
		Member member = memberService.getMemberById(Container.session.getLoginedId());
		if (member == null) {
			System.out.println("관리자만 게시판을 생성할 수 있습니다.");
			return;
		}
		if (!member.isAdmin()) {
			System.out.println("관리자만 게시판을 생성할 수 있습니다.");
			return;
		}

		System.out.println("== 게시판 생성 ==");
		System.out.printf("게시판 이름 : ");
		String name = sc.nextLine();

		Board board = articleService.getBoardByName(name);
		if (board != null) {
			System.out.println("이미 존재하는 게시판입니다.");
			return;
		}

		int number = articleService.doMakeBoard(name);
		System.out.printf("%s(%d번) 게시판이 생성되었습니다.\n", name, number);
	}

}
