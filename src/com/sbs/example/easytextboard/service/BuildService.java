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

		File statDir = new File("site/stat/");
		if (statDir.exists() == false) {
			statDir.mkdirs();
		}
		File articleDir = new File("site/article/");
		if (articleDir.exists() == false) {
			articleDir.mkdirs();
		}

		File homeDir = new File("site/home/");
		if (homeDir.exists() == false) {
			homeDir.mkdirs();
		}

		Util.copy(cssSource, "site/article/app.css");
		Util.copy(cssSource, "site/stat/app.css");
		Util.copy(cssSource, "site/home/app.css");

		String head = Util.getFileContents("site_template/part/head.html");
		String foot = Util.getFileContents("site_template/part/foot.html");

		

		head = getHeadHtml(head);

		createMainPage(head, foot);

		createBoardPage(head, foot);

		createArticleDetail(head, foot);

		createStatDetail(head, foot);

	}

	// 통계페이지 생성 함수
	private void createStatDetail(String head, String foot) {
		ArrayList<Board> boards = articleService.getBoardsForPrint();
		String fileName = "index.html";

		List<Member> members = memberService.getMembers();
		List<Article> articles = articleService.getArticles();

		StringBuilder statHtmlBuilder = new StringBuilder();
		
		head = head.replace("<a href=\"notice-list-1.html\">", "<a href=\"../article/notice-list-1.html\">");
		head = head.replace("<a href=\"free-list-1.html\">", "<a href=\"../article/free-list-1.html\">");
		statHtmlBuilder.append(head);
		
		String statHtml = Util.getFileContents("site_template/stat/index.html");
		
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
		Util.writeFileContents("site/stat/" + fileName, statHtmlBuilder.toString());

	}

	// 게시물 상세보기 생성 함수
	private void createArticleDetail(String head, String foot) {
		
		ArrayList<Board> boards = articleService.getBoardsForPrint();
		for (Board board : boards) {

			List<Article> articles = articleService.getArticlesByBoardCode(board.getCode());

			for (int i = 0; i < articles.size(); i++) {

				String writer = memberService.getMemberById(articles.get(i).getMemberId()).getName();
				String fileName = board.getCode() + "-" + articles.get(i).getId() + ".html";
				StringBuilder articleDetailHtml = new StringBuilder();
				articleDetailHtml.append(head);
				articleDetailHtml.append("<main class=\"flex flex-grow-1 flex-dir-col con-min-width\">");
				articleDetailHtml.append("<section class=\"article con flex flex-dir-col\">");

				articleDetailHtml.append("<div class=\"article__title-bar flex flex-jc-s-bet flex-ai-c\">");
				articleDetailHtml.append("<div class=\"article_id flex-basis-50px\">");
				articleDetailHtml.append(articles.get(i).getId());
				articleDetailHtml.append("</div>");
				articleDetailHtml.append("<div class=\"article_title flex-grow-1\">");
				articleDetailHtml.append(articles.get(i).getTitle());
				articleDetailHtml.append("</div>");
				articleDetailHtml.append("<div class=\"article_writer flex-basis-100px\">");
				articleDetailHtml.append("<i class=\"fas fa-user\"></i>");
				articleDetailHtml.append(writer);
				articleDetailHtml.append("</div>");
				articleDetailHtml.append("<div class=\"article_regDate flex-basis-200px\">");
				articleDetailHtml.append("<i class=\"fas fa-clock\"></i>");
				articleDetailHtml.append(articles.get(i).getRegDate());
				articleDetailHtml.append("</div>");
				articleDetailHtml.append("<div class=\"article_hit flex-basis-50px\">");
				articleDetailHtml.append("<i class=\"fas fa-eye\"></i>");
				articleDetailHtml.append(articles.get(i).getHit());
				articleDetailHtml.append("</div>");
				articleDetailHtml.append("<div class=\"article_recommand flex-basis-50px\">");
				articleDetailHtml.append("<i class=\"fas fa-thumbs-up\"></i>");
				articleDetailHtml.append(articleService.getArticleRecommand(articles.get(i).getId()));
				articleDetailHtml.append("</div>");
				articleDetailHtml.append("</div>");

				articleDetailHtml.append("<div class=\"article_body flex-grow-1\">");
				articleDetailHtml.append("<span>");
				articleDetailHtml.append(articles.get(i).getBody());
				articleDetailHtml.append("</span>");
				articleDetailHtml.append("</div>");

				ArrayList<ArticleReply> articleReplys = articleService
						.getReplysForPrintByArticleId(articles.get(i).getId());

				articleDetailHtml.append("<section class=\"con-min-width reply\">");
				articleDetailHtml.append("<div class=\"reply__section\">");
				articleDetailHtml.append("<div class=\"con reply-box\">");
				articleDetailHtml.append("<i class=\"fas fa-comment-alt\"></i>");
				articleDetailHtml.append("댓글 " + articleReplys.size() + "개");
				articleDetailHtml.append("</div>");

				if (articleReplys.size() > 0) {
					articleDetailHtml.append("<div class=\"reply-box__replys\">");
					for (ArticleReply articleReply : articleReplys) {
						articleDetailHtml.append("<div class=\"con reply-list flex\">");
						articleDetailHtml.append("<div class=\"reply_writer flex-basis-100px\">");
						articleDetailHtml.append("<span>");
						articleDetailHtml.append(articleReply.getExtraName());
						articleDetailHtml.append("</span>");
						articleDetailHtml.append("</div>");
						articleDetailHtml.append("<div class=\"reply_body flex-grow-1\">");
						articleDetailHtml.append("<span>");
						articleDetailHtml.append(articleReply.getBody());
						articleDetailHtml.append("</span>");
						articleDetailHtml.append("</div>");
						articleDetailHtml.append("<div class=\"reply_regDate\">");
						articleDetailHtml.append("<span>");
						articleDetailHtml.append(articleReply.getRegDate());
						articleDetailHtml.append("</span>");
						articleDetailHtml.append("</div>");
						articleDetailHtml.append("</div>");
					}
					articleDetailHtml.append("</div>");
				}

				articleDetailHtml.append("</div>");
				articleDetailHtml.append("</section>");

				articleDetailHtml.append("<div class=\"con-min-width article-move-button\">");

				if (i != articles.size() - 1) {
					articleDetailHtml.append("<div class=\"con next-article\"><a href=\"" + board.getCode() + "-"
							+ articles.get(i + 1).getId() + ".html\">다음글</a></div>");
				}

				if (i != 0) {
					articleDetailHtml.append("<div class=\"con next-article\"><a href=\"" + board.getCode() + "-"
							+ articles.get(i - 1).getId() + ".html\">이전글</a></div>");
				}
				articleDetailHtml.append("</div>");
				articleDetailHtml.append("</section>");

				articleDetailHtml.append("<section class=\"con article-list\">");

				int itemsInAPage = 10;
				int pageSum = articles.size() / itemsInAPage;
				if (articles.size() % 10 != 0) {
					pageSum++;
				}

				if (pageSum == 0 && articles.size() > 0) {
					pageSum = 1;
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
				if (endPageButton > pageSum) {
					endPageButton = pageSum;
				}

				articleDetailHtml.append("<section class=\"title-bar con-min-width\">");
				articleDetailHtml.append("<h1 class=\"title-bar__title-box con flex flex-jc-c\">");
				switch (board.getCode()) {
				case "notice":
					articleDetailHtml.append("<i class=\"fas fa-flag\"></i>");
					break;
				case "free":
					articleDetailHtml.append("<i class=\"fas fa-clipboard\"></i>");
					break;
				default:
					articleDetailHtml.append("<i class=\"far fa-clipboard\"></i>");
					break;
				}
				articleDetailHtml.append("<span>");
				articleDetailHtml.append(board.getName() + " 게시판");
				articleDetailHtml.append("</span>");
				articleDetailHtml.append("</h1>");
				articleDetailHtml.append("</section>");

				articleDetailHtml.append("<section class=\"board-list con-min-width\">");
				articleDetailHtml.append("<table class =\"con\">");
				articleDetailHtml.append("<tr class =\"tag\">");
				articleDetailHtml.append("<td class =\"font-bold cell-id\">번호</td>");
				articleDetailHtml.append("<td class =\"font-bold cell-title\">제목</td>");
				articleDetailHtml.append("<td class =\"font-bold cell-writer\">작성자</td>");
				articleDetailHtml.append("<td class =\"font-bold cell-regDate\">작성일</td>");
				articleDetailHtml.append("<td class =\"font-bold cell-hit\">조회 수</td>");
				articleDetailHtml.append("<td class =\"font-bold cell-recommand\">추천 수</td>");
				articleDetailHtml.append("</tr>");

				for (int j = start; j >= end; j--) {
					articleDetailHtml.append("<tr>");
					articleDetailHtml.append("<td colspan=\"6\" class =\"line-separate\"></td>");
					articleDetailHtml.append("</tr>");
					articleDetailHtml.append("<tr>");
					if (articles.get(i).getId() == articles.get(j).getId()) {
						articleDetailHtml.append("<td class=\"cell-id\"> &gt; </td>");
					} else {
						articleDetailHtml.append("<td class=\"cell-id\">" + articles.get(j).getId() + "</td>");
					}

					articleDetailHtml.append("<td class=\"cell-title\"><a href=\"" + board.getCode() + "-"
							+ articles.get(j).getId() + ".html\">" + articles.get(j).getTitle() + "</td>");
					articleDetailHtml.append("<td class=\"cell-writer\">" + articles.get(j).getExtraWriter() + "</td>");
					articleDetailHtml.append("<td class=\"cell-regDate\">" + articles.get(j).getRegDate() + "</td>");
					articleDetailHtml.append("<td class=\"cell-hit\">" + articles.get(j).getHit() + "</td>");
					articleDetailHtml.append("<td class=\"cell-recommand\">"
							+ articleService.getArticleRecommand(articles.get(j).getId()) + "</td>");
					articleDetailHtml.append("</tr>");
				}

				articleDetailHtml.append("</table>");
				articleDetailHtml.append("</section>");

				articleDetailHtml.append("<section class=\"page-button con-min-width\">");
				articleDetailHtml.append("<div class=\"con flex flex-ai-c flex-jc-s-bet\">");
				articleDetailHtml.append("<div class=\"flex flex-basis-100px\">");
				if (pageSum >= 2) {
					articleDetailHtml.append("<div class=\"flex flex-basis-50px\">");
					articleDetailHtml.append("<a href=\"" + board.getCode() + "-list-1.html\"> &lt;&lt; </a>");
					articleDetailHtml.append("</div>");
				}
				if (currentPage != 1) {
					articleDetailHtml.append("<div class=\"flex flex-basis-50px\">");
					articleDetailHtml.append("<a href=\"" + board.getCode() + "-list-"
							+ ((pageButton - 1) * buttonsInAPage - 9) + ".html\"> 이전 </a>");
					articleDetailHtml.append("</div>");
				}
				articleDetailHtml.append("</div>");

				articleDetailHtml.append("<ul class=\"flex flex-jc-c flex-ai-e flex-grow-1\">");

				for (int k = startPageButton; k <= endPageButton; k++) {
					String page = board.getCode() + "-list-" + k + ".html";
					if (k == currentPage) {
						articleDetailHtml.append("<li class=\"flex flex-jc-c flex-basis-50px\">");
						articleDetailHtml.append("<span>" + k + "</span>");
						articleDetailHtml.append("</li>");
					} else {
						articleDetailHtml.append("<li class=\"flex flex-jc-c flex-basis-50px\">");
						articleDetailHtml.append("<a href=\"" + page + "\"> " + k + "</a>");
						articleDetailHtml.append("</li>");
					}
				}

				articleDetailHtml.append("</ul>");

				articleDetailHtml.append("<div class=\"flex flex-basis-100px\">");
				if (currentPage < pageSum) {
					articleDetailHtml.append("<div class=\"flex flex-basis-50px\">");
					articleDetailHtml.append("<a href=\"" + board.getCode() + "-list-"
							+ (pageButton * buttonsInAPage + 1) + ".html\"> 다음 </a>");
					articleDetailHtml.append("</div>");
				}

				if (pageSum >= 2) {
					articleDetailHtml.append("<div class=\"flex flex-basis-50px\">");
					articleDetailHtml
							.append("<a href=\"" + board.getCode() + "-list-" + pageSum + ".html\"> &gt&gt; </a>");
					articleDetailHtml.append("</div>");
				}
				articleDetailHtml.append("</div>");
				articleDetailHtml.append("</section>");

				articleDetailHtml.append("</section>");

				articleDetailHtml.append("</main>");
				articleDetailHtml.append(foot);

				Util.writeFileContents("site/article/" + fileName, articleDetailHtml.toString());

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
				StringBuilder boardPageHtml = new StringBuilder();
				String fileName = "";
				fileName = board.getCode() + "-list-1.html";
				boardPageHtml.append(head);
				boardPageHtml.append("<main class=\"flex flex-dir-col flex-grow-1\">");
				boardPageHtml.append("<section class=\"title-bar con-min-width\">");
				boardPageHtml.append("<h1 class=\"title-bar__title-box con flex flex-jc-c\">");

				switch (board.getCode()) {
				case "notice":
					boardPageHtml.append("<i class=\"fas fa-flag\"></i>");
					break;
				case "free":
					boardPageHtml.append("<i class=\"fas fa-clipboard\"></i>");
					break;
				default:
					boardPageHtml.append("<i class=\"far fa-clipboard\"></i>");
					break;
				}
				boardPageHtml.append("<span>");
				boardPageHtml.append(board.getName() + " 게시판");
				boardPageHtml.append("</span>");
				boardPageHtml.append("</h1>");
				boardPageHtml.append("</section>");

				boardPageHtml.append("<section class=\"board-list con-min-width\">");
				boardPageHtml.append("<table class =\"con\">");
				boardPageHtml.append("<tr class =\"tag\">");
				boardPageHtml.append("<td class =\"font-bold cell-id\">번호</td>");
				boardPageHtml.append("<td class =\"font-bold cell-title\">제목</td>");
				boardPageHtml.append("<td class =\"font-bold cell-writer\">작성자</td>");
				boardPageHtml.append("<td class =\"font-bold cell-regDate\">작성일</td>");
				boardPageHtml.append("<td class =\"font-bold cell-hit\">조회 수</td>");
				boardPageHtml.append("<td class =\"font-bold cell-recommand\">추천 수</td>");
				boardPageHtml.append("</tr>");
				boardPageHtml.append("<tr>");
				boardPageHtml.append("<td colspan=\"6\" class =\"line-separate\"></td>");
				boardPageHtml.append("</tr>");
				boardPageHtml.append("<tr class=\"article-not-exists\">");
				boardPageHtml.append("<td colspan=\"6\" class=\"font-bold\">작성된 게시물이 없습니다.</td>");
				boardPageHtml.append("</tr>");
				boardPageHtml.append("</table>");
				boardPageHtml.append("</section>");
				boardPageHtml.append("</section>");
				boardPageHtml.append("</main>");
				boardPageHtml.append(foot);
				Util.writeFileContents("site/article/" + fileName, boardPageHtml.toString());
			} else if (articles.size() != 0) {
				for (int i = 1; i <= pages; i++) {
					int buttonsInAPage = 10;
					int pageButton = (int) Math.ceil((double) i / buttonsInAPage);
					int startPageButton = (pageButton - 1) * buttonsInAPage + 1;
					int endPageButton = startPageButton + buttonsInAPage - 1;
					if (endPageButton > pages) {
						endPageButton = pages;
					}
					int endPage = (int) Math.ceil((double) pages / buttonsInAPage);

					StringBuilder boardPageListHtml = new StringBuilder();
					String fileName = "";
					fileName = board.getCode() + "-list-" + i + ".html";

					boardPageListHtml.append(head);
					boardPageListHtml.append("<main class=\"flex flex-dir-col flex-grow-1\">");
					boardPageListHtml.append("<section class=\"title-bar con-min-width\">");
					boardPageListHtml.append("<h1 class=\"title-bar__title-box con flex flex-jc-c\">");
					switch (board.getCode()) {
					case "notice":
						boardPageListHtml.append("<i class=\"fas fa-flag\"></i>");
						break;
					case "free":
						boardPageListHtml.append("<i class=\"fas fa-clipboard\"></i>");
						break;
					default:
						boardPageListHtml.append("<i class=\"far fa-clipboard\"></i>");
						break;
					}
					boardPageListHtml.append("<span>");
					boardPageListHtml.append(board.getName() + " 게시판");
					boardPageListHtml.append("</span>");
					boardPageListHtml.append("</h1>");
					boardPageListHtml.append("</section>");

					boardPageListHtml.append("<section class=\"board-list con-min-width\">");
					boardPageListHtml.append("<table class =\"con\">");
					boardPageListHtml.append("<tr class =\"tag\">");
					boardPageListHtml.append("<td class =\"font-bold cell-id\">번호</td>");
					boardPageListHtml.append("<td class =\"font-bold cell-title\">제목</td>");
					boardPageListHtml.append("<td class =\"font-bold cell-writer\">작성자</td>");
					boardPageListHtml.append("<td class =\"font-bold cell-regDate\">작성일</td>");
					boardPageListHtml.append("<td class =\"font-bold cell-hit\">조회 수</td>");
					boardPageListHtml.append("<td class =\"font-bold cell-recommand\">추천 수</td>");
					boardPageListHtml.append("</tr>");

					int start = articles.size() - ((i - 1) * itemsInAPage) - 1;
					int end = (start - itemsInAPage + 1);
					if (end < 0) {
						end = 0;
					}

					for (int j = start; j >= end; j--) {
						boardPageListHtml.append("<tr>");
						boardPageListHtml.append("<td colspan=\"6\" class =\"line-separate\"></td>");
						boardPageListHtml.append("</tr>");
						boardPageListHtml.append("<tr>");
						boardPageListHtml.append("<td class=\"cell-id\">" + articles.get(j).getId() + "</td>");
						boardPageListHtml.append("<td class=\"cell-title\"><a href=\"" + board.getCode() + "-"
								+ articles.get(j).getId() + ".html\">" + articles.get(j).getTitle() + "</td>");
						boardPageListHtml
								.append("<td class=\"cell-writer\">" + articles.get(j).getExtraWriter() + "</td>");
						boardPageListHtml
								.append("<td class=\"cell-regDate\">" + articles.get(j).getRegDate() + "</td>");
						boardPageListHtml.append("<td class=\"cell-hit\">" + articles.get(j).getHit() + "</td>");
						boardPageListHtml.append("<td class=\"cell-recommand\">"
								+ articleService.getArticleRecommand(articles.get(j).getId()) + "</td>");
						boardPageListHtml.append("</tr>");
					}

					boardPageListHtml.append("</table>");
					boardPageListHtml.append("</section>");

					boardPageListHtml.append("<div class=\"flex-grow-1\"></div>");

					boardPageListHtml.append("<section class=\"page-button con-min-width\">");
					boardPageListHtml.append("<div class=\"con flex flex-ai-c flex-jc-s-bet\">");
					boardPageListHtml.append("<div class=\"flex flex-basis-100px\">");
					if (pages >= 2) {
						boardPageListHtml.append("<div class=\"flex flex-basis-50px\">");
						boardPageListHtml.append("<a href=\"" + board.getCode() + "-list-1.html\"> << </a>");
						boardPageListHtml.append("</div>");
					}
					if (pageButton != 1) {
						boardPageListHtml.append("<div class=\"flex flex-basis-50px\">");
						boardPageListHtml.append("<a href=\"" + board.getCode() + "-list-"
								+ ((pageButton - 1) * buttonsInAPage - 9) + ".html\"> 이전 </a>");
						boardPageListHtml.append("</div>");
					}
					boardPageListHtml.append("</div>");

					boardPageListHtml.append("<ul class=\"flex flex-jc-c flex-ai-e flex-grow-1\">");

					for (int k = startPageButton; k <= endPageButton; k++) {
						String page = board.getCode() + "-list-" + k + ".html";
						if (k == i) {
							boardPageListHtml.append("<li class=\"flex flex-jc-c flex-basis-50px\">");
							boardPageListHtml.append("<span>" + k + "</span>");
							boardPageListHtml.append("</li>");
						} else {
							boardPageListHtml.append("<li class=\"flex flex-jc-c flex-basis-50px\">");
							boardPageListHtml.append("<a href=\"" + page + "\"> " + k + "</a>");
							boardPageListHtml.append("</li>");
						}
					}

					boardPageListHtml.append("</ul>");

					boardPageListHtml.append("<div class=\"flex flex-basis-100px\">");
					if (pageButton < endPage) {
						boardPageListHtml.append("<div class=\"flex flex-basis-50px\">");
						boardPageListHtml.append("<a href=\"" + board.getCode() + "-list-"
								+ (pageButton * buttonsInAPage + 1) + ".html\"> 다음 </a>");
						boardPageListHtml.append("</div>");
					}

					if (pages >= 2) {
						boardPageListHtml.append("<div class=\"flex flex-basis-50px\">");
						boardPageListHtml
								.append("<a href=\"" + board.getCode() + "-list-" + pages + ".html\"> >> </a>");
						boardPageListHtml.append("</div>");
					}
					boardPageListHtml.append("</div>");
					boardPageListHtml.append("</div>");
					boardPageListHtml.append("</section>");
					boardPageListHtml.append("</main>");
					boardPageListHtml.append(foot);

					Util.writeFileContents("site/article/" + fileName, boardPageListHtml.toString());
				}
			}
		}

	}

	// 메인화면 생성 함수
	private void createMainPage(String head, String foot) {

		StringBuilder mainPageHtml = new StringBuilder();
		mainPageHtml.append(head);
		mainPageHtml.append("<main class=\"flex-grow-1\">");
		mainPageHtml.append("<section class=\"con-min-width\">");
		mainPageHtml.append("<div class=\"con home-title-box\">");
		mainPageHtml.append(" <i class=\"fas fa-home\"></i>");
		mainPageHtml.append("<span>HOME</span>");
		mainPageHtml.append("</div>");
		mainPageHtml.append("</section>");

		mainPageHtml.append("<section class=\"home-body-box con-min-width\">");
		mainPageHtml.append("<div class=\"con\">");
		String homeBody = Util.getFileContents("site_template/part/homeMainText.txt");
		mainPageHtml.append(homeBody);
		mainPageHtml.append("</div>");
		mainPageHtml.append("</section>");
		mainPageHtml.append("</main>");
		mainPageHtml.append(foot);
		Util.writeFileContents("site/home/" + "index.html", mainPageHtml.toString());

	}

	// 상단 메뉴 생성 함수
	private String getHeadHtml(String head) {
		ArrayList<Board> boards = articleService.getBoardsForPrint();
		StringBuilder changeMenuHtml = new StringBuilder();
		
		for (Board board : boards) {
			changeMenuHtml.append("<li>");
			changeMenuHtml.append("<a href=\"../article/" + board.getCode() + "-list-1.html" + "\">");

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
