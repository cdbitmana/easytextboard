package com.sbs.example.easytextboard.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.Article;
import com.sbs.example.easytextboard.dto.ArticleReply;
import com.sbs.example.easytextboard.dto.Board;
import com.sbs.example.easytextboard.dto.Member;
import com.sbs.example.easytextboard.util.Util;

public class BuildService {

	ArticleService articleService;
	MemberService memberService;

	public BuildService() {
		articleService = Container.articleService;
		memberService = Container.memberService;
	}

	public void makeHtml() {
		String cssSource = "site_template/part/app.css";
		String jsSource = "site_template/resource/common.js";
		File siteDir = new File("site");
		if (siteDir.exists() == false) {
			siteDir.mkdir();
		}
		/*
		File statDir = new File("site/stat/");

		if (statDir.exists() == false) {
			statDir.mkdirs();
		}
		File articleDir = new File("site/article/");

		if (articleDir.exists() == false) {
			articleDir.mkdirs();
		}
		
		 * File homeDir = new File("site/home/");
		 * 
		 * if (homeDir.exists() == false) { homeDir.mkdirs(); } Util.copy(cssSource,
		 * "site/home/app.css");
		 
		Util.copy(cssSource, "site/article/app.css");
		Util.copy(cssSource, "site/stat/app.css");
		

		
		Util.copy(jsSource, "site/article/common.js");
		Util.copy(jsSource, "site/stat/common.js");
		*/
		Util.copy(cssSource, "site/app.css");
		Util.copy(jsSource, "site/common.js");
		String head = Util.getFileContents("site_template/part/head.html");
		String foot = Util.getFileContents("site_template/part/foot.html");

		createMainPage(head, foot);

		createBoardPage(head, foot);

		createArticleDetail(head, foot);

		createStatDetail(head, foot);

	}

	// 통계페이지 생성 함수
	private void createStatDetail(String head, String foot) {
		ArrayList<Board> boards = articleService.getBoardsForPrint();
		String fileName = "statindex.html";

		List<Member> members = memberService.getMembers();
		List<Article> articles = articleService.getArticles();

		StringBuilder statHtmlBuilder = new StringBuilder();
		String statHtml = Util.getFileContents("site_template/stat/index.html");
		statHtmlBuilder.append(getHeadHtml(head));

		statHtml = statHtml.replace("${member_count}", String.valueOf(members.size()));
		statHtml = statHtml.replace("${all_article_count}", String.valueOf(articles.size()));

		StringBuilder dif_article_count = new StringBuilder();
		dif_article_count.append("<ul>");
		for (Board board : boards) {
			List<Article> articlesByBoard = articleService.getArticlesByBoardCode(board.getCode());
			dif_article_count.append("<li>" + board.getName() + " : " + articlesByBoard.size() + "</li>");
		}
		dif_article_count.append("</ul>");

		statHtml = statHtml.replace("${dif_article_count}", dif_article_count.toString());

		int hitSum = 0;
		for (Article article : articles) {
			hitSum += article.getHit();
		}

		statHtml = statHtml.replace("${all_article_hit_count}", String.valueOf(hitSum));

		StringBuilder dif_article_hit_count = new StringBuilder();

		dif_article_hit_count.append("<ul>");
		for (Board board : boards) {
			List<Article> articlesByBoard = articleService.getArticlesByBoardCode(board.getCode());

			int hitSumByBoard = 0;
			for (Article article : articlesByBoard) {
				hitSumByBoard += article.getHit();
			}
			dif_article_hit_count.append("<li>" + board.getName() + " : " + hitSumByBoard + "</li>");

		}
		dif_article_hit_count.append("</ul>");

		statHtml = statHtml.replace("${dif_article_hit_count}", dif_article_hit_count.toString());

		statHtmlBuilder.append(statHtml);
		statHtmlBuilder.append(foot);
		Util.writeFileContents("site/" + fileName, statHtmlBuilder.toString());

	}

	// 게시물 상세보기 생성 함수
	private void createArticleDetail(String head, String foot) {

		ArrayList<Board> boards = articleService.getBoardsForPrint();

		for (Board board : boards) {

			List<Article> articles = articleService.getArticlesByBoardCode(board.getCode());

			for (int i = 0; i < articles.size(); i++) {
				String articleDetailHtml = Util.getFileContents("site_template/article/article/detail.html");
				String writer = memberService.getMemberById(articles.get(i).getMemberId()).getName();
				String fileName = board.getCode() + "-" + articles.get(i).getId() + ".html";
				StringBuilder articleDetailHtmlBuilder = new StringBuilder();

				articleDetailHtmlBuilder.append(getHeadHtml(head));

				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-id}",
						String.valueOf(articles.get(i).getId()));
				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-title}",
						articles.get(i).getTitle());
				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-writer}", writer);
				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-regDate}",
						articles.get(i).getRegDate());
				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-hit}",
						String.valueOf(articles.get(i).getHit()));
				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-recommend}",
						String.valueOf(articleService.getArticleRecommend(articles.get(i).getId())));
				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-body}",
						articles.get(i).getBody());

				

				StringBuilder articleDetail__ArticleMoveButton = new StringBuilder();
				articleDetail__ArticleMoveButton.append("");
				if (i != articles.size() - 1) {
					articleDetail__ArticleMoveButton.append("<div class=\"con next-article\"><a href=\""
							+ board.getCode() + "-" + articles.get(i + 1).getId() + ".html\">다음글</a></div>");
				}

				if (i != 0) {
					articleDetail__ArticleMoveButton.append("<div class=\"con next-article\"><a href=\""
							+ board.getCode() + "-" + articles.get(i - 1).getId() + ".html\">이전글</a></div>");
				}

				articleDetailHtml = articleDetailHtml.replace("${articledetail_articlemovebutton}",
						articleDetail__ArticleMoveButton.toString());

				int itemsInAPage = 10;
				int total_pages = articles.size() / itemsInAPage;
				if (articles.size() % 10 != 0) {
					total_pages++;
				}

				if (total_pages == 0 && articles.size() > 0) {
					total_pages = 1;
				}

				int currentPage = ((int) (articles.size() - i) / 10) + 1;
				if ((articles.size() - i) % 10 == 0) {
					currentPage--;
				}
				int start = articles.size() - ((currentPage - 1) * itemsInAPage) - 1;
				int end = (start - itemsInAPage + 1);
				if (end < 0) {
					end = 0;
				}

				int buttonsInAPage = 10;
				int pageButton = (int) Math.ceil((double) currentPage / buttonsInAPage);
				int startPageButton = (pageButton - 1) * buttonsInAPage + 1;
				int endPageButton = startPageButton + buttonsInAPage - 1;
				if (endPageButton > total_pages) {
					endPageButton = total_pages;
				}

				StringBuilder articleDetail__articlelist_boardName = new StringBuilder();

				switch (board.getCode()) {
				case "notice":
					articleDetail__articlelist_boardName
							.append("<i class=\"fas fa-flag\"></i><span>" + board.getName() + " 게시판</span>");
					break;
				case "free":
					articleDetail__articlelist_boardName
							.append("<i class=\"fas fa-clipboard\"></i><span>" + board.getName() + " 게시판</span>");
					break;
				default:
					articleDetail__articlelist_boardName
							.append("<i class=\"far fa-clipboard\"></i><span>" + board.getName() + " 게시판</span>");
					break;
				}

				articleDetailHtml = articleDetailHtml.replace("${articledetail__articlelist-boardname}",
						articleDetail__articlelist_boardName.toString());

				StringBuilder articleDetail__articleList = new StringBuilder();
				for (int j = start; j >= end; j--) {
					articleDetail__articleList.append("<tr>");
					articleDetail__articleList.append("<td colspan=\"6\" class =\"line-separate\"></td>");
					articleDetail__articleList.append("</tr>");
					articleDetail__articleList.append("<tr>");
					if (articles.get(i).getId() == articles.get(j).getId()) {
						articleDetail__articleList.append("<td class=\"cell-id\"> &gt; </td>");
					} else {
						articleDetail__articleList.append("<td class=\"cell-id\">" + articles.get(j).getId() + "</td>");
					}

					articleDetail__articleList.append("<td class=\"cell-title\"><a href=\"" + board.getCode() + "-"
							+ articles.get(j).getId() + ".html\">" + articles.get(j).getTitle() + "</td>");
					articleDetail__articleList
							.append("<td class=\"cell-writer\">" + articles.get(j).getExtraWriter() + "</td>");
					articleDetail__articleList
							.append("<td class=\"cell-regDate\">" + articles.get(j).getRegDate() + "</td>");
					articleDetail__articleList.append("<td class=\"cell-hit\">" + articles.get(j).getHit() + "</td>");
					articleDetail__articleList.append("<td class=\"cell-recommend\">"
							+ articleService.getArticleRecommend(articles.get(j).getId()) + "</td>");
					articleDetail__articleList.append("</tr>");
				}

				articleDetailHtml = articleDetailHtml.replace("${articledetail__articlelist}",
						articleDetail__articleList.toString());

				StringBuilder articledetail__articlelist_firstpagelink = new StringBuilder();
				articledetail__articlelist_firstpagelink.append("");
				if (total_pages >= 2) {
					articledetail__articlelist_firstpagelink.append("<div class=\"flex flex-basis-50px\">");
					articledetail__articlelist_firstpagelink
							.append("<a href=\"" + board.getCode() + "-list-1.html\"> &lt;&lt; </a>");
					articledetail__articlelist_firstpagelink.append("</div>");
				}
				articleDetailHtml = articleDetailHtml.replace("${articledetail__articlelist-firstpagelink}",
						articledetail__articlelist_firstpagelink.toString());

				StringBuilder articledetail__articlelist_prevpagelink = new StringBuilder();
				articledetail__articlelist_prevpagelink.append("");
				if (currentPage != 1) {
					articledetail__articlelist_prevpagelink.append("<div class=\"flex flex-basis-50px\">");
					articledetail__articlelist_prevpagelink.append("<a href=\"" + board.getCode() + "-list-"
							+ ((pageButton - 1) * buttonsInAPage - 9) + ".html\"> 이전 </a>");
					articledetail__articlelist_prevpagelink.append("</div>");
				}
				articleDetailHtml = articleDetailHtml.replace("${articledetail__articlelist-prevpagelink}",
						articledetail__articlelist_prevpagelink.toString());

				StringBuilder articledetail__articlelist_pages = new StringBuilder();
				articledetail__articlelist_pages.append("");
				for (int k = startPageButton; k <= endPageButton; k++) {
					String page = board.getCode() + "-list-" + k + ".html";
					if (k == currentPage) {
						articledetail__articlelist_pages.append("<li class=\"flex flex-jc-c flex-basis-50px\">");
						articledetail__articlelist_pages.append("<span>" + k + "</span>");
						articledetail__articlelist_pages.append("</li>");
					} else {
						articledetail__articlelist_pages.append("<li class=\"flex flex-jc-c flex-basis-50px\">");
						articledetail__articlelist_pages.append("<a href=\"" + page + "\"> " + k + "</a>");
						articledetail__articlelist_pages.append("</li>");
					}
				}

				articleDetailHtml = articleDetailHtml.replace("${articledetail__articlelist-pages}",
						articledetail__articlelist_pages.toString());

				StringBuilder articledetail__articlelist_nextpagelink = new StringBuilder();
				articledetail__articlelist_nextpagelink.append("");
				if (currentPage < total_pages) {
					articledetail__articlelist_nextpagelink.append("<div class=\"flex flex-basis-50px\">");
					articledetail__articlelist_nextpagelink.append("<a href=\"" + board.getCode() + "-list-"
							+ (pageButton * buttonsInAPage + 1) + ".html\"> 다음 </a>");
					articledetail__articlelist_nextpagelink.append("</div>");
				}

				articleDetailHtml = articleDetailHtml.replace("${articledetail__articlelist-nextpagelink}",
						articledetail__articlelist_nextpagelink.toString());

				StringBuilder articledetail__articlelist_lastpagelink = new StringBuilder();
				if (total_pages >= 2) {
					articledetail__articlelist_lastpagelink.append("<div class=\"flex flex-basis-50px\">");
					articledetail__articlelist_lastpagelink
							.append("<a href=\"" + board.getCode() + "-list-" + total_pages + ".html\"> &gt&gt; </a>");
					articledetail__articlelist_lastpagelink.append("</div>");
				}

				articleDetailHtml = articleDetailHtml.replace("${articledetail__articlelist-lastpagelink}",
						articledetail__articlelist_lastpagelink.toString());
				articleDetailHtmlBuilder.append(articleDetailHtml);
				articleDetailHtmlBuilder.append(foot);

				Util.writeFileContents("site/" + fileName, articleDetailHtmlBuilder.toString());

			}
		}
	}

	// 게시판 페이지 생성 함수
	private void createBoardPage(String head, String foot) {

		ArrayList<Board> boards = articleService.getBoardsForPrint();
		for (Board board : boards) {

			List<Article> articles = articleService.getArticlesByBoardCode(board.getCode());

			int itemsInAPage = 10;
			int pages = articles.size() / itemsInAPage;
			if (articles.size() % 10 != 0) {
				pages++;
			}

			if (pages == 0 && articles.size() > 0) {
				pages = 1;
			}

			if (articles.size() == 0) {
				String boardPageHtml = Util.getFileContents("site_template/article/list/list.html");

				StringBuilder boardPageHtmlBuilder = new StringBuilder();
				String fileName = "";
				fileName = board.getCode() + "-list-1.html";
				boardPageHtmlBuilder.append(getHeadHtml(head));

				StringBuilder board_list__boardname = new StringBuilder();

				switch (board.getCode()) {
				case "notice":
					board_list__boardname
							.append("<i class=\"fas fa-flag\"></i><span>" + board.getName() + " 게시판</span>");
					break;
				case "free":
					board_list__boardname
							.append("<i class=\"fas fa-clipboard\"></i><span>" + board.getName() + " 게시판</span>");
					break;
				default:
					board_list__boardname
							.append("<i class=\"far fa-clipboard\"></i><span>" + board.getName() + " 게시판</span>");
					break;
				}

				boardPageHtml = boardPageHtml.replace("${board-list__boardname}", board_list__boardname.toString());

				boardPageHtml = boardPageHtml.replace("${board-list}",
						"<tr class=\"article-not-exists\"><td colspan=\"6\" class=\"font-bold\">작성된 게시물이 없습니다.</td></tr>");

				boardPageHtml = boardPageHtml.replace("${board-list__firstpagebutton}", "");
				boardPageHtml = boardPageHtml.replace("${board-list__prevpagebutton}", "");
				boardPageHtml = boardPageHtml.replace("${board-list__pagebuttons}", "");
				boardPageHtml = boardPageHtml.replace("${board-list__nextpagebutton}", "");
				boardPageHtml = boardPageHtml.replace("${board-list__lastpagebutton}", "");

				boardPageHtmlBuilder.append(boardPageHtml);

				boardPageHtmlBuilder.append(foot);
				Util.writeFileContents("site/" + fileName, boardPageHtmlBuilder.toString());
			} else if (articles.size() != 0) {

				for (int i = 1; i <= pages; i++) {
					String boardPageHtml = Util.getFileContents("site_template/article/list/list.html");

					StringBuilder boardPageHtmlBuilder = new StringBuilder();
					int buttonsInAPage = 10;
					int pageButton = (int) Math.ceil((double) i / buttonsInAPage);
					int startPageButton = (pageButton - 1) * buttonsInAPage + 1;
					int endPageButton = startPageButton + buttonsInAPage - 1;
					if (endPageButton > pages) {
						endPageButton = pages;
					}
					int endPage = (int) Math.ceil((double) pages / buttonsInAPage);

					String fileName = "";
					fileName = board.getCode() + "-list-" + i + ".html";

					boardPageHtmlBuilder.append(getHeadHtml(head));

					StringBuilder board_list__boardname = new StringBuilder();

					switch (board.getCode()) {
					case "notice":
						board_list__boardname
								.append("<i class=\"fas fa-flag\"></i><span>" + board.getName() + " 게시판</span>");
						break;
					case "free":
						board_list__boardname
								.append("<i class=\"fas fa-clipboard\"></i><span>" + board.getName() + " 게시판</span>");
						break;
					default:
						board_list__boardname
								.append("<i class=\"far fa-clipboard\"></i><span>" + board.getName() + " 게시판</span>");
						break;
					}

					boardPageHtml = boardPageHtml.replace("${board-list__boardname}", board_list__boardname.toString());

					int start = articles.size() - ((i - 1) * itemsInAPage) - 1;
					int end = (start - itemsInAPage + 1);
					if (end < 0) {
						end = 0;
					}

					StringBuilder board_list = new StringBuilder();
					for (int j = start; j >= end; j--) {
						board_list.append("<tr>");
						board_list.append("<td colspan=\"6\" class =\"line-separate\"></td>");
						board_list.append("</tr>");
						board_list.append("<tr>");
						board_list.append("<td class=\"cell-id\">" + articles.get(j).getId() + "</td>");
						board_list.append("<td class=\"cell-title\"><a href=\"" + board.getCode() + "-"
								+ articles.get(j).getId() + ".html\">" + articles.get(j).getTitle() + "</td>");
						board_list.append("<td class=\"cell-writer\">" + articles.get(j).getExtraWriter() + "</td>");
						board_list.append("<td class=\"cell-regDate\">" + articles.get(j).getRegDate() + "</td>");
						board_list.append("<td class=\"cell-hit\">" + articles.get(j).getHit() + "</td>");
						board_list.append("<td class=\"cell-recommend\">"
								+ articleService.getArticleRecommend(articles.get(j).getId()) + "</td>");
						board_list.append("</tr>");
					}

					boardPageHtml = boardPageHtml.replace("${board-list}", board_list.toString());

					StringBuilder board_list__firstpagebutton = new StringBuilder();
					board_list__firstpagebutton.append("");
					if (pages >= 2) {
						board_list__firstpagebutton.append("<div class=\"flex flex-basis-50px\">");
						board_list__firstpagebutton.append("<a href=\"" + board.getCode() + "-list-1.html\"> << </a>");
						board_list__firstpagebutton.append("</div>");
					}
					boardPageHtml = boardPageHtml.replace("${board-list__firstpagebutton}",
							board_list__firstpagebutton.toString());

					StringBuilder board_list__prevpagebutton = new StringBuilder();
					board_list__prevpagebutton.append("");
					if (pageButton != 1) {
						board_list__prevpagebutton.append("<div class=\"flex flex-basis-50px\">");
						board_list__prevpagebutton.append("<a href=\"" + board.getCode() + "-list-"
								+ ((pageButton - 1) * buttonsInAPage - 9) + ".html\"> 이전 </a>");
						board_list__prevpagebutton.append("</div>");
					}
					boardPageHtml = boardPageHtml.replace("${board-list__prevpagebutton}",
							board_list__prevpagebutton.toString());

					StringBuilder board_list__pagebuttons = new StringBuilder();

					for (int k = startPageButton; k <= endPageButton; k++) {
						String page = board.getCode() + "-list-" + k + ".html";
						if (k == i) {
							board_list__pagebuttons.append("<li class=\"flex flex-jc-c flex-basis-50px\">");
							board_list__pagebuttons.append("<span>" + k + "</span>");
							board_list__pagebuttons.append("</li>");
						} else {
							board_list__pagebuttons.append("<li class=\"flex flex-jc-c flex-basis-50px\">");
							board_list__pagebuttons.append("<a href=\"" + page + "\"> " + k + "</a>");
							board_list__pagebuttons.append("</li>");
						}
					}
					boardPageHtml = boardPageHtml.replace("${board-list__pagebuttons}",
							board_list__pagebuttons.toString());

					StringBuilder board_list__nextpagebutton = new StringBuilder();
					board_list__nextpagebutton.append("");
					if (pageButton < endPage) {
						board_list__nextpagebutton.append("<div class=\"flex flex-basis-50px\">");
						board_list__nextpagebutton.append("<a href=\"" + board.getCode() + "-list-"
								+ (pageButton * buttonsInAPage + 1) + ".html\"> 다음 </a>");
						board_list__nextpagebutton.append("</div>");
					}
					boardPageHtml = boardPageHtml.replace("${board-list__nextpagebutton}",
							board_list__nextpagebutton.toString());

					StringBuilder board_list__lastpagebutton = new StringBuilder();
					if (pages >= 2) {
						board_list__lastpagebutton.append("<div class=\"flex flex-basis-50px\">");
						board_list__lastpagebutton
								.append("<a href=\"" + board.getCode() + "-list-" + pages + ".html\"> >> </a>");
						board_list__lastpagebutton.append("</div>");
					}
					boardPageHtml = boardPageHtml.replace("${board-list__lastpagebutton}",
							board_list__lastpagebutton.toString());

					boardPageHtmlBuilder.append(boardPageHtml);

					boardPageHtmlBuilder.append(foot);

					Util.writeFileContents("site/" + fileName, boardPageHtmlBuilder.toString());
				}
			}
		}

	}

	// 메인화면 생성 함수
	private void createMainPage(String head, String foot) {

		StringBuilder mainPageHtml = new StringBuilder();
		String homeHtml = Util.getFileContents("site_template/home/index.html");

		mainPageHtml.append(getHeadHtml(head));
		mainPageHtml.append(homeHtml);
		mainPageHtml.append(foot);
		Util.writeFileContents("site/" + "index.html", mainPageHtml.toString());

	}

	// 상단 메뉴 생성 함수
	private String getHeadHtml(String head) {

		ArrayList<Board> boards = articleService.getBoardsForPrint();

		StringBuilder changeMenuHtml = new StringBuilder();
		
		for (Board board : boards) {
			changeMenuHtml.append("<li>");
			changeMenuHtml.append("<a href=\"" + board.getCode() + "-list-1.html" + "\">");

			switch (board.getCode()) {
			case "notice":
				changeMenuHtml.append("<i class=\"fas fa-flag\"></i>");
				break;
			case "free":
				changeMenuHtml.append("<i class=\"fas fa-clipboard\"></i>");
				break;
			default:
				changeMenuHtml.append("<i class=\"far fa-clipboard\"></i>");
				break;
			}

			changeMenuHtml.append("<span>");
			changeMenuHtml.append(board.getName() + "게시판");
			changeMenuHtml.append("</span>");
			changeMenuHtml.append("</a>");
			changeMenuHtml.append("</li>");
		}
		
		return head = head.replace("${boardListHead}", changeMenuHtml.toString());

	}

	

}
