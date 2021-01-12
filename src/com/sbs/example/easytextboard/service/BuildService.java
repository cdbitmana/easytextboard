package com.sbs.example.easytextboard.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.Article;
import com.sbs.example.easytextboard.dto.ArticleReply;
import com.sbs.example.easytextboard.dto.Board;
import com.sbs.example.easytextboard.dto.GuestBook;
import com.sbs.example.easytextboard.dto.Member;
import com.sbs.example.easytextboard.util.Util;

public class BuildService {

	GuestBookService guestBookService;
	ArticleService articleService;
	MemberService memberService;
	private DisqusApiService disqusApiService;

	public BuildService() {
		guestBookService = Container.guestBookService;
		articleService = Container.articleService;
		memberService = Container.memberService;
		disqusApiService = Container.disqusApiService;
	}

	public void makeHtml() {
		String cssSource = "site_template/part/app.css";
		String jsSource = "site_template/resource/common.js";
		File siteDir = new File("site");
		if (siteDir.exists() == false) {
			siteDir.mkdir();
		}
		/*
		 * File statDir = new File("site/stat/");
		 * 
		 * if (statDir.exists() == false) { statDir.mkdirs(); } File articleDir = new
		 * File("site/article/");
		 * 
		 * if (articleDir.exists() == false) { articleDir.mkdirs(); }
		 * 
		 * File homeDir = new File("site/home/");
		 * 
		 * if (homeDir.exists() == false) { homeDir.mkdirs(); } Util.copy(cssSource,
		 * "site/home/app.css");
		 * 
		 * Util.copy(cssSource, "site/article/app.css"); Util.copy(cssSource,
		 * "site/stat/app.css");
		 * 
		 * 
		 * 
		 * Util.copy(jsSource, "site/article/common.js"); Util.copy(jsSource,
		 * "site/stat/common.js");
		 */

		Util.copy(cssSource, "site/app.css");
		Util.copy(jsSource, "site/common.js");

		String foot = Util.getFileContents("site_template/part/foot.html");

		Container.disqusApiService.loadDisqusData();

		Container.googleAnalyticsApiService.updatePageHits();

		createMainPage("index", foot);

		createBoardPage("article_list", foot);

		createArticleDetail("article_detail", foot);

		createStatDetail("statistics", foot);

		createProfile("profile", foot);

		createGuestBook("guestbook", foot);

	}

	// 방명록 페이지 생성 함수
	private void createGuestBook(String pageName, String foot) {
		/*
		 * List<GuestBook> guestBooks = guestBookService.getGuestBooks();
		 */

		List<GuestBook> guestBooks = guestBookService.getGuestBooks();
		int itemsInAPage = 10;
		int pages = guestBooks.size() / itemsInAPage;
		if (guestBooks.size() % 10 != 0) {
			pages++;
		}

		if (pages == 0 && guestBooks.size() > 0) {
			pages = 1;
		}

		for (int i = 1; i <= pages; i++) {
			String guestBookHtml = Util.getFileContents("site_template/guestbook/guestbook.html");

			StringBuilder guestBookHtmlBuilder = new StringBuilder();
			int buttonsInAPage = 10;
			int pageButton = (int) Math.ceil((double) i / buttonsInAPage);
			int startPageButton = (pageButton - 1) * buttonsInAPage + 1;
			int endPageButton = startPageButton + buttonsInAPage - 1;
			if (endPageButton > pages) {
				endPageButton = pages;
			}
			int endPage = (int) Math.ceil((double) pages / buttonsInAPage);

			String fileName = "";
			fileName = "guestbook" + "-list-" + i + ".html";

			guestBookHtmlBuilder.append(getHeadHtml(pageName));

			int start = guestBooks.size() - ((i - 1) * itemsInAPage) - 1;
			int end = (start - itemsInAPage + 1);
			if (end < 0) {
				end = 0;
			}

			StringBuilder board_list = new StringBuilder();

			for (int j = start; j >= end; j--) {

				board_list.append("<div class=\"flex\">");

				board_list.append("<div class=\"guestbook-writer\">" + guestBooks.get(j).getExtra__writer() + "</div>");
				board_list.append("<div class=\"flex-grow-1 guestbook-title\">" + guestBooks.get(j).getTitle());
				board_list.append("</div>");
				board_list.append("<div class=\"guestbook-regDate\">" + guestBooks.get(j).getRegDate() + "</div>");

				board_list.append("</div>");

			}

			guestBookHtml = guestBookHtml.replace("${guestbook-list}", board_list.toString());

			StringBuilder guestbook_list__firstpagebutton = new StringBuilder();
			guestbook_list__firstpagebutton.append("");
			if (pages >= 2) {
				guestbook_list__firstpagebutton.append("<div class=\"flex flex-basis-50px\">");
				guestbook_list__firstpagebutton.append("<a href=\"guestbook-list-1.html\"> << </a>");
				guestbook_list__firstpagebutton.append("</div>");
			}
			guestBookHtml = guestBookHtml.replace("${guestbook-list__firstpagebutton}",
					guestbook_list__firstpagebutton.toString());

			StringBuilder guestbook_list__prevpagebutton = new StringBuilder();
			guestbook_list__prevpagebutton.append("");
			if (pageButton != 1) {
				guestbook_list__prevpagebutton.append("<div class=\"flex flex-basis-50px\">");
				guestbook_list__prevpagebutton.append(
						"<a href=\"guestbook-list-" + ((pageButton - 1) * buttonsInAPage - 9) + ".html\"> 이전 </a>");
				guestbook_list__prevpagebutton.append("</div>");
			}
			guestBookHtml = guestBookHtml.replace("${guestbook-list__prevpagebutton}",
					guestbook_list__prevpagebutton.toString());

			StringBuilder guestbook_list__pagebuttons = new StringBuilder();

			for (int k = startPageButton; k <= endPageButton; k++) {
				String page = "guestbook-list-" + k + ".html";
				if (k == i) {
					guestbook_list__pagebuttons.append("<li class=\"flex flex-jc-c flex-basis-50px\">");
					guestbook_list__pagebuttons.append("<span>" + k + "</span>");
					guestbook_list__pagebuttons.append("</li>");
				} else {
					guestbook_list__pagebuttons.append("<li class=\"flex flex-jc-c flex-basis-50px\">");
					guestbook_list__pagebuttons.append("<a href=\"" + page + "\"> " + k + "</a>");
					guestbook_list__pagebuttons.append("</li>");
				}
			}
			guestBookHtml = guestBookHtml.replace("${guestbook-list__pagebuttons}",
					guestbook_list__pagebuttons.toString());

			StringBuilder guestbook_list__nextpagebutton = new StringBuilder();
			guestbook_list__nextpagebutton.append("");
			if (pageButton < endPage) {
				guestbook_list__nextpagebutton.append("<div class=\"flex flex-basis-50px\">");
				guestbook_list__nextpagebutton
						.append("<a href=\"guestbook-list-" + (pageButton * buttonsInAPage + 1) + ".html\"> 다음 </a>");
				guestbook_list__nextpagebutton.append("</div>");
			}
			guestBookHtml = guestBookHtml.replace("${guestbook-list__nextpagebutton}",
					guestbook_list__nextpagebutton.toString());

			StringBuilder guestbook_list__lastpagebutton = new StringBuilder();
			if (pages >= 2) {
				guestbook_list__lastpagebutton.append("<div class=\"flex flex-basis-50px\">");
				guestbook_list__lastpagebutton.append("<a href=\"guestbook-list-" + pages + ".html\"> >> </a>");
				guestbook_list__lastpagebutton.append("</div>");
			}
			guestBookHtml = guestBookHtml.replace("${guestbook-list__lastpagebutton}",
					guestbook_list__lastpagebutton.toString());

			guestBookHtmlBuilder.append(guestBookHtml);

			guestBookHtmlBuilder.append(foot);

			Util.writeFileContents("site/" + fileName, guestBookHtmlBuilder.toString());

		}

	}

	// 프로필 페이지 생성 함수
	private void createProfile(String pageName, String foot) {

		String fileName = "profile.html";

		String profileHtml = Util.getFileContents("site_template/profile/profile.html");
		StringBuilder profileBuilder = new StringBuilder();
		pageName = getHeadHtml(pageName);
		profileBuilder.append(pageName);
		profileBuilder.append(profileHtml);
		profileBuilder.append(foot);

		Util.writeFileContents("site/" + fileName, profileBuilder.toString());

	}

	// 통계페이지 생성 함수
	private void createStatDetail(String pageName, String foot) {
		ArrayList<Board> boards = articleService.getBoardsForPrint();
		String fileName = "statindex.html";

		List<Member> members = memberService.getMembers();

		StringBuilder statHtmlBuilder = new StringBuilder();
		String statHtml = Util.getFileContents("site_template/stat/index.html");
		String statJs = Util.getFileContents("site_template/resource/stat.js");
		statHtmlBuilder.append(getHeadHtml(pageName));

		StringBuilder articleHitChartHtml = new StringBuilder();
		StringBuilder articleHitChartJs = new StringBuilder();

		for (Board board : boards) {

			List<Article> articles = articleService.getArticlesForStaticPage(board.getId());
			if (articles.size() == 0) {
				articleHitChartHtml.append("");
				articleHitChartJs.append("");
				break;
			}
			articleHitChartHtml.append("<div class=\"articleChart con\">");
			articleHitChartHtml.append("<div>");
			articleHitChartHtml.append("<canvas id=\"articleHitChart" + board.getId() + "\"></canvas>");
			articleHitChartHtml.append("</div>");
			articleHitChartHtml.append("</div>");

			articleHitChartJs.append("var articleHit" + board.getId() + " = document.getElementById('articleHitChart"
					+ board.getId() + "');");
			articleHitChartJs.append("var chart" + board.getId() + " = new Chart(articleHit" + board.getId() + ", {");
			articleHitChartJs.append("type: 'doughnut',");
			articleHitChartJs.append("data: {");
			articleHitChartJs.append("labels: [");
			for (int i = 0; i < articles.size(); i++) {
				if (i == articles.size() - 1) {
					articleHitChartJs.append("'" + articles.get(i).getTitle() + "'");
					continue;
				}
				articleHitChartJs.append("'" + articles.get(i).getTitle() + "',");
			}
			articleHitChartJs.append("],");
			articleHitChartJs.append("datasets: [{");
			articleHitChartJs.append("data: [");
			for (int i = 0; i < articles.size(); i++) {
				if (i == articles.size() - 1) {
					articleHitChartJs.append(articles.get(i).getHitCount());
					continue;
				}
				articleHitChartJs.append(articles.get(i).getHitCount() + ",");
			}
			articleHitChartJs.append("],");
			articleHitChartJs
					.append("backgroundColor:['#F8E88B','#F69069','#8482ff','#ff8293','#E4B660','#ff82ff','#66d4f5']");
			articleHitChartJs.append("}]");
			articleHitChartJs.append("},");
			articleHitChartJs.append("options: {}");
			articleHitChartJs.append("});");

		}
		statHtml = statHtml.replace("{{articleHitChart}}", articleHitChartHtml.toString());
		statJs = statJs.replace("{{articleHitChart}}", articleHitChartJs.toString());

		statHtmlBuilder.append(statHtml);
		statHtmlBuilder.append(foot);

		Util.writeFileContents("site/stat.js", statJs.toString());
		Util.writeFileContents("site/" + fileName, statHtmlBuilder.toString());

	}

	// 게시물 상세보기 생성 함수
	private void createArticleDetail(String pageName, String foot) {

		ArrayList<Board> boards = articleService.getBoardsForPrint();

		for (Board board : boards) {

			List<Article> articles = articleService.getArticlesByBoardCode(board.getCode());

			for (int i = 0; i < articles.size(); i++) {
				String articleDetailHtml = Util.getFileContents("site_template/article/article/detail.html");
				String writer = memberService.getMemberById(articles.get(i).getMemberId()).getName();
				String fileName = board.getCode() + "-detail-" + articles.get(i).getId() + ".html";
				StringBuilder articleDetailHtmlBuilder = new StringBuilder();
				Article article = articles.get(i);
				articleDetailHtmlBuilder.append(getHeadHtml(pageName, article));

				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-id}",
						String.valueOf(articles.get(i).getId()));
				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-title}",
						articles.get(i).getTitle());
				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-writer}", writer);
				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-regDate}",
						articles.get(i).getRegDate());
				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-hit}",
						String.valueOf(articles.get(i).getHitCount()));
				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-recommend}",
						String.valueOf(articleService.getArticleRecommend(articles.get(i).getId())));
				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-comments}",
						String.valueOf(articles.get(i).getCommentsCount()));
				articleDetailHtml = articleDetailHtml.replace("${articledetail__article-body}",
						articles.get(i).getBody());

				StringBuilder articleDetail__ArticleMoveButton = new StringBuilder();
				articleDetail__ArticleMoveButton.append("");
				if (i != articles.size() - 1) {
					articleDetail__ArticleMoveButton.append("<div class=\"con next-article\"><a href=\""
							+ board.getCode() + "-detail-" + articles.get(i + 1).getId() + ".html\">다음글</a></div>");
				}

				if (i != 0) {
					articleDetail__ArticleMoveButton.append("<div class=\"con next-article\"><a href=\""
							+ board.getCode() + "-detail-" + articles.get(i - 1).getId() + ".html\">이전글</a></div>");
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
				int endPage = (int) Math.ceil((double) total_pages / itemsInAPage);
				int buttonsInAPage = 10;
				int pageButton = (int) Math.ceil((double) currentPage / buttonsInAPage);
				int startPageButton = (pageButton - 1) * buttonsInAPage + 1;
				int endPageButton = startPageButton + buttonsInAPage - 1;
				if (endPageButton > total_pages) {
					endPageButton = total_pages;
				}

				StringBuilder articleDetail__articlelist_boardName = new StringBuilder();

				articleDetail__articlelist_boardName.append("<a href=\"" + board.getCode() + "-list-1.html"
						+ "\"><span>" + board.getName() + " 게시판</span></a>");

				articleDetailHtml = articleDetailHtml.replace("${articledetail__articlelist-boardname}",
						articleDetail__articlelist_boardName.toString());

				StringBuilder articleDetail__articleList = new StringBuilder();

				/*
				 * 기존 버전 for (int j = start; j >= end; j--) {
				 * articleDetail__articleList.append("<tr class =\"line-separate\">");
				 * articleDetail__articleList.append("<td colspan=\"6\"></td>");
				 * articleDetail__articleList.append("</tr>");
				 * articleDetail__articleList.append("<tr>"); if (articles.get(i).getId() ==
				 * articles.get(j).getId()) {
				 * articleDetail__articleList.append("<td class=\"cell-id\"> &gt; </td>"); }
				 * else { articleDetail__articleList.append("<td class=\"cell-id\">" +
				 * articles.get(j).getId() + "</td>"); }
				 * 
				 * articleDetail__articleList.append("<td class=\"cell-title\"><a href=\"" +
				 * board.getCode() + "-detail-" + articles.get(j).getId() + ".html\">" +
				 * articles.get(j).getTitle() + "</td>"); articleDetail__articleList
				 * .append("<td class=\"cell-writer\">" + articles.get(j).getExtraWriter() +
				 * "</td>"); articleDetail__articleList .append("<td class=\"cell-regDate\">" +
				 * articles.get(j).getRegDate() + "</td>"); articleDetail__articleList
				 * .append("<td class=\"cell-hit\">" + articles.get(j).getHitCount() + "</td>");
				 * articleDetail__articleList.append("<td class=\"cell-recommend\">" +
				 * articleService.getArticleRecommend(articles.get(j).getId()) + "</td>");
				 * articleDetail__articleList.append("</tr>"); }
				 */

				/* Vue 적용 버전 */
				articleDetail__articleList.append("<tbody v-for=\"article in articles\">");
				articleDetail__articleList.append("<tr class =\"line-separate\">");
				articleDetail__articleList.append("<td colspan=\"6\"></td>");
				articleDetail__articleList.append("</tr>");
				articleDetail__articleList.append("<tr>");
				articleDetail__articleList.append("<td class=\"cell-id\">" + "{{article.id}}" + "</td>");
				articleDetail__articleList.append("<td class=\"cell-title\"><a :href=\"'" + board.getCode()
						+ "-detail-'+article.id+'.html'\">" + "{{article.title}}" + "</td>");
				articleDetail__articleList.append("<td class=\"cell-writer\">" + "{{article.writer}}" + "</td>");
				articleDetail__articleList.append("<td class=\"cell-regDate\">" + "{{article.regDate}}" + "</td>");
				articleDetail__articleList.append("<td class=\"cell-hit\">" + "{{article.hitCount}}" + "</td>");
				articleDetail__articleList.append("<td class=\"cell-recommend\">" + "{{article.likesCount}}" + "</td>");
				articleDetail__articleList.append("</tr>");
				articleDetail__articleList.append("</tbody>");
				/* Vue 적용 버전 */

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
				if (pageButton != 1 && total_pages > 10) {
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
				if (pageButton < endPage && total_pages > 10) {
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
				articleDetailHtml = articleDetailHtml.replace("${site-domain}", "blog.hailrain.site");
				articleDetailHtml = articleDetailHtml.replace("${file-name}", getArticleDetailFileName(article));
				articleDetailHtmlBuilder.append(articleDetailHtml);
				articleDetailHtmlBuilder.append(foot);

				Util.writeFileContents("site/" + fileName, articleDetailHtmlBuilder.toString());

			}
		}
	}

	// 게시판 페이지 생성 함수
	private void createBoardPage(String pageName, String foot) {
		Util.copy("site_template/resource/article_list.js", "site/article_list.js");
		ArrayList<Board> boards = articleService.getBoardsForPrint();
		for (Board board : boards) {

			List<Article> articles = articleService.getArticleByBoardCodeDesc(board.getCode());

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
				boardPageHtmlBuilder.append(getHeadHtml(pageName));

				StringBuilder board_list__boardname = new StringBuilder();
				board_list__boardname.append("<a href=\"" + board.getCode() + "-list-1.html" + "\"><span>"
						+ board.getName() + " 게시판</span></a>");
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
				String jsonText = Util.getJsonText(articles);
				Util.writeFile("site/" + board.getCode() + "-list.json", jsonText);
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

					boardPageHtmlBuilder.append(getHeadHtml(pageName));

					StringBuilder board_list__boardname = new StringBuilder();

					board_list__boardname.append("<a href=\"" + board.getCode() + "-list-1.html" + "\"><span>"
							+ board.getName() + " 게시판</span></a>");

					boardPageHtml = boardPageHtml.replace("${board-list__boardname}", board_list__boardname.toString());

					int start = articles.size() - ((i - 1) * itemsInAPage) - 1;
					int end = (start - itemsInAPage + 1);
					if (end < 0) {
						end = 0;
					}

					StringBuilder board_list = new StringBuilder();

					board_list.append("<tbody v-for=\"article in articles\">");
					board_list.append("<tr class =\"line-separate\">");
					board_list.append("<td colspan=\"6\"></td>");
					board_list.append("</tr>");
					board_list.append("<tr>");
					board_list.append("<td class=\"cell-id\">" + "{{article.id}}" + "</td>");
					board_list.append("<td class=\"cell-title\"><a :href=\"'" + board.getCode()
							+ "-detail-'+article.id+'.html'\">" + "{{article.title}}" + "</td>");
					board_list.append("<td class=\"cell-writer\">" + "{{article.writer}}" + "</td>");
					board_list.append("<td class=\"cell-regDate\">" + "{{article.regDate}}" + "</td>");
					board_list.append("<td class=\"cell-hit\">" + "{{article.hitCount}}" + "</td>");
					board_list.append("<td class=\"cell-recommend\">" + "{{article.likesCount}}" + "</td>");
					board_list.append("</tr>");
					board_list.append("</tbody>");

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

					board_list__pagebuttons
							.append("<li v-for=\"page in pages\" class=\"flex flex-jc-c flex-basis-50px\">");
					board_list__pagebuttons.append("<span @click=\"movePage(page)\" class=\"currentPageCheck\">{{page.index}}</span>");
					
					board_list__pagebuttons.append("</li>");

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
	private void createMainPage(String pageName, String foot) {

		StringBuilder mainPageHtml = new StringBuilder();

		mainPageHtml.append(getHeadHtml(pageName));

		String homeHtml = Util.getFileContents("site_template/home/index.html");

		List<Article> articles = articleService.getArticlesByBoardId();

		int start = articles.size() - 1;
		int itemsInAPage = 6;
		int end = start - itemsInAPage + 1;
		if (end < 0) {
			end = 0;
		}
		StringBuilder article_box = new StringBuilder();
		for (int i = start; i >= end; i--) {

			article_box.append("<div class=\"home-main__article-box\">");
			article_box.append("<div class=\"flex home-main__article-box__titlebar\">");
			article_box.append("<div class=\"home-main__article-box__boardname\">"
					+ articles.get(i).getExtra__boardName() + "</div>");
			article_box.append("<div class=\"home-main__article-box__title flex-grow-1\">" + articles.get(i).getTitle()
					+ "</div>");
			article_box.append(
					"<div class=\"home-main__article-box__writer\">" + articles.get(i).getExtra__writer() + "</div>");
			article_box.append("</div>");
			article_box.append("<div class=\"home-main__article-box__body\">" + articles.get(i).getBody() + "</div>");
			article_box.append("<div class=\"home-main__article-box__detail\">");
			article_box.append("<a href=\"" + articles.get(i).getExtra__boardCode() + "-detail-"
					+ articles.get(i).getId() + ".html\">");
			article_box.append("자세히 보기");
			article_box.append("</a>");
			article_box.append("</div>");
			article_box.append("</div>");
		}

		homeHtml = homeHtml.replace("${home-main__article-box}", article_box.toString());
		mainPageHtml.append(homeHtml);

		mainPageHtml.append(foot);
		Util.writeFileContents("site/" + "index.html", mainPageHtml.toString());

	}

	// getHeadHtml
	private String getHeadHtml(String pageName) {
		return getHeadHtml(pageName, null);
	}

	private String getHeadHtml(String pageName, Object relObj) {

		String head = Util.getFileContents("site_template/part/head.html");

		ArrayList<Board> boards = articleService.getBoardsForPrint();

		StringBuilder changeMenuHtml = new StringBuilder();

		for (Board board : boards) {
			changeMenuHtml.append("<li>");
			changeMenuHtml.append("<a href=\"" + board.getCode() + "-list-1.html" + "\">");
			changeMenuHtml.append("<span>");
			changeMenuHtml.append(board.getName() + "게시판");
			changeMenuHtml.append("</span>");
			changeMenuHtml.append("</a>");
			changeMenuHtml.append("</li>");
		}

		head = head.replace("${boardListHead}", changeMenuHtml.toString());

		String pageTitle = getPageTitle(pageName, relObj);

		head = head.replace("${page-title}", pageTitle);

		String siteName = "DLOG";
		String siteSubject = "이명학의 개인 블로그";
		String siteDescription = "개발자의 기술/일상 관련 글들을 공유합니다.";
		String siteKeywords = "HTML, CSS, JAVASCRIPT, JAVA, SPRING, MySQL, 리눅스, 리액트";
		String siteDomain = "blog.hailrain.site";
		String siteMainUrl = "https://" + siteDomain;
		String currentDate = Util.getNowDateStr().replace(" ", "T");

		if (relObj instanceof Article) {
			Article article = (Article) relObj;
			siteSubject = article.getTitle();
			siteDescription = article.getBody();
			siteDescription = siteDescription.replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", "");
		}

		head = head.replace("${site-name}", siteName);
		head = head.replace("${site-subject}", siteSubject);
		head = head.replace("${site-description}", siteDescription);
		head = head.replace("${site-domain}", siteDomain);
		head = head.replace("${site-domain}", siteDomain);
		head = head.replace("${current-date}", currentDate);
		head = head.replace("${site-main-url}", siteMainUrl);
		head = head.replace("${site-keywords}", siteKeywords);

		return head;

	}

	// getPageTitle
	private String getPageTitle(String pageName, Object relObj) {
		StringBuilder sb = new StringBuilder();

		String forPrintPageName = pageName;

		if (forPrintPageName.equals("index")) {
			forPrintPageName = "home";
		}

		forPrintPageName = forPrintPageName.toUpperCase();
		forPrintPageName = forPrintPageName.replaceAll("_", " ");

		sb.append("DLOG | ");
		sb.append(forPrintPageName);

		if (relObj instanceof Article) {

			Article article = (Article) relObj;

			sb.insert(0, article.getTitle() + " | ");
		}

		return sb.toString();
	}

	public String getArticleDetailFileName(Article article) {
		return article.getExtra__boardCode() + "-detail-" + article.getId() + ".html";

	}
}
