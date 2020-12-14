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
			statDir.mkdir();
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

		ArrayList<Board> boards = articleService.getBoardsForPrint();

		head = createBoardMenu(head, boards);

		createMainPage(head, foot);

		createBoardPage(head, foot, boards);

		createArticleDetail(head, foot, boards);

		createStatDetail(head, foot, boards);

	}

	// 통계페이지 생성 함수
	private void createStatDetail(String head, String foot, ArrayList<Board> boards) {

		String fileName = "index.html";

		List<Member> members = memberService.getMembers();
		List<Article> articles = articleService.getArticles();

		StringBuilder statHtml = new StringBuilder();
		head = head.replace("<a href=\"notice-list-1.html\">", "<a href=\"../article/notice-list-1.html\">");
		head = head.replace("<a href=\"free-list-1.html\">", "<a href=\"../article/free-list-1.html\">");
		statHtml.append(head);
		statHtml.append("<main class=\"flex flex-jc-c flex-grow-1\">");
		statHtml.append("<section class=\"stat-box con-min-width\">");
		statHtml.append("<div class = \"con\">");
		statHtml.append("<ul>");
		statHtml.append("<li>회원 수 : " + members.size() + "</li>");
		statHtml.append("<li>전체 게시물 수 : " + articles.size() + "</li>");
		statHtml.append("<li>각 게시판 별 게시물 수");
		statHtml.append("<ul>");
		for (Board board : boards) {
			List<Article> articlesByBoard = articleService.getArticlesByBoardCode(board.getCode());
			statHtml.append("<li>" + board.getName() + " : " + articlesByBoard.size() + "</li>");
		}
		statHtml.append("</ul>");
		statHtml.append("</li>");
		int hitSum = 0;
		for (Article article : articles) {
			hitSum += article.getHit();
		}

		statHtml.append("<li>전체 게시물 조회 수 : " + hitSum + "</li>");

		statHtml.append("<li>각 게시판 별 조회 수");
		statHtml.append("<ul>");
		for (Board board : boards) {
			List<Article> articlesByBoard = articleService.getArticlesByBoardCode(board.getCode());
			int hitSumByBoard = 0;
			for (Article article : articlesByBoard) {
				hitSumByBoard += article.getHit();
			}
			statHtml.append("<li>" + board.getName() + " : " + hitSumByBoard + "</li>");

		}
		statHtml.append("</li>");
		statHtml.append("</ul>");

		statHtml.append("</ul>");
		statHtml.append("</section>");
		statHtml.append("</main>");

		statHtml.append(foot);
		Util.writeFileContents("site/stat/" + fileName, statHtml.toString());

	}

	// 게시물 상세보기 생성 함수
	private void createArticleDetail(String head, String foot, ArrayList<Board> boards) {

		for (Board board : boards) {

			List<Article> articles = articleService.getArticlesByBoardCode(board.getCode());

			for (int i = 0; i < articles.size(); i++) {

				String writer = memberService.getMemberById(articles.get(i).getMemberId()).getName();
				String fileName = board.getCode() + "-" + articles.get(i).getId() + ".html";
				StringBuilder articleDetailHtml = new StringBuilder();
				articleDetailHtml.append(head);
				articleDetailHtml.append("<main class=\"flex flex-grow-1 flex-dir-col con-min-width\">");
				articleDetailHtml.append("<section class=\"article con flex flex-dir-col flex-grow-1\">");

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

				int pageSum = articles.size() / 10;
				if (articles.size() % 10 != 0) {
					pageSum++;
				}
				int itemsInAPage = 10;
				if (pageSum == 0 && articles.size() > 0) {
					pageSum = 1;
				}
				
				int y = ((int)(articles.size() - i) / 10) +1;
				if((articles.size()-i)%10 == 0) {
					y--;
				}
				int start = articles.size() - ((y - 1) * itemsInAPage ) -1 ;
				int end = (start - itemsInAPage + 1);
				if (end < 0) {
					end = 0;
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
				articleDetailHtml.append(board.getName());
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
					articleDetailHtml.append("<td class=\"cell-id\">" + articles.get(j).getId() + "</td>");
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
					articleDetailHtml.append("<a href=\"" + board.getCode() + "-list-1.html\"> << </a>");
					articleDetailHtml.append("</div>");
				}
				if (y != 1) {
					articleDetailHtml.append("<div class=\"flex flex-basis-50px\">");
					articleDetailHtml.append("<a href=\"" + board.getCode() + "-list-" + (y-1) + ".html\"> < </a>");
					articleDetailHtml.append("</div>");
				}
				articleDetailHtml.append("</div>");

				articleDetailHtml.append("<ul class=\"flex flex-jc-c flex-ai-e flex-grow-1\">");

				for (int l = 1; l <= pageSum; l++) {
					String page2 = board.getCode() + "-list-" + l + ".html";
					if (l == y) {
						articleDetailHtml.append("<li class=\"flex flex-jc-c flex-basis-50px\">");
						articleDetailHtml.append("<span>" + l + "</span>");
						articleDetailHtml.append("</li>");
					} else {
						articleDetailHtml.append("<li class=\"flex flex-jc-c flex-basis-50px\">");
						articleDetailHtml.append("<a href=\"" + page2 + "\"> " + l + "</a>");
						articleDetailHtml.append("</li>");
					}

				}
				articleDetailHtml.append("</ul>");

				articleDetailHtml.append("<div class=\"flex flex-basis-100px\">");
				if (y < pageSum) {
					articleDetailHtml.append("<div class=\"flex flex-basis-50px\">");
					articleDetailHtml.append("<a href=\"" + board.getCode() + "-list-" + (y + 1) + ".html\"> > </a>");
					articleDetailHtml.append("</div>");
				}

				if (pageSum >= 2) {
					articleDetailHtml.append("<div class=\"flex flex-basis-50px\">");
					articleDetailHtml.append("<a href=\"" + board.getCode() + "-list-" + pageSum + ".html\"> >> </a>");
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
	private void createBoardPage(String head, String foot, ArrayList<Board> boards) {
		for (Board board : boards) {

			List<Article> articles = articleService.getArticlesByBoardCode(board.getCode());

			int pages = articles.size() / 10;
			if (articles.size() % 10 != 0) {
				pages++;
			}
			int itemsInAPage = 10;
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
				boardPageHtml.append(board.getName());
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
				boardPageHtml.append("<tr>");
				boardPageHtml.append("<td colspan=\"6\" class=\"font-bold \">작성된 게시물이 없습니다.</td>");
				boardPageHtml.append("</tr>");
				boardPageHtml.append("</table>");
				boardPageHtml.append("</section>");
				boardPageHtml.append("</section>");
				boardPageHtml.append("</main>");
				boardPageHtml.append(foot);
				Util.writeFileContents("site/article/" + fileName, boardPageHtml.toString());
			} else if (articles.size() != 0) {
				for (int i = 1; i <= pages; i++) {
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
					boardPageListHtml.append(board.getName());
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
					if (i != 1) {
						boardPageListHtml.append("<div class=\"flex flex-basis-50px\">");
						boardPageListHtml
								.append("<a href=\"" + board.getCode() + "-list-" + (i - 1) + ".html\"> < </a>");
						boardPageListHtml.append("</div>");
					}
					boardPageListHtml.append("</div>");

					boardPageListHtml.append("<ul class=\"flex flex-jc-c flex-ai-e flex-grow-1\">");

					for (int k = 1; k <= pages; k++) {
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
					if (i < pages) {
						boardPageListHtml.append("<div class=\"flex flex-basis-50px\">");
						boardPageListHtml
								.append("<a href=\"" + board.getCode() + "-list-" + (i + 1) + ".html\"> > </a>");
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
		String homeBody = Util.getFileContents("site/home/homeBody.txt");
		mainPageHtml.append(homeBody);
		mainPageHtml.append("</div>");
		mainPageHtml.append("</section>");
		mainPageHtml.append("</main>");
		mainPageHtml.append(foot);
		Util.writeFileContents("site/home/" + "index.html", mainPageHtml.toString());

	}

	// 상단 메뉴 생성 함수
	private String createBoardMenu(String head, ArrayList<Board> boards) {
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
