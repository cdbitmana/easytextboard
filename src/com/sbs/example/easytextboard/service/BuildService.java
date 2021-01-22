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
import com.sbs.example.easytextboard.dto.Tag;
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

		createTagListPage("article_tag", foot);

		createBoardPage("article_list", foot);

		createArticleDetail("article_detail", foot);

		createStatDetail("statistics", foot);

		createProfile("profile", foot);

	}

	// 태그리스트 페이지 생성 함수
	private void createTagListPage(String pageName, String foot) {
		StringBuilder tagListHtml = new StringBuilder();
		tagListHtml.append(getHeadHtml(pageName));
		String fileName = "article_tag.html";
		String tagList = Util.getFileContents("site_template/article/tagList/article_tag.html");

		Map<String, List<Article>> articleTag = new HashMap<>();
		List<Article> articles = new ArrayList<Article>();

		List<Tag> tags = articleService.getTagBodys();

		for (Tag tag : tags) {

			articles = articleService.getArticlesByTagBody(tag.getBody());

			articleTag.put(tag.getBody(), articles);
		}

		String jsonText = Util.getJsonText(articleTag);
		Util.writeFile("site/article_tag.json", jsonText);

		StringBuilder taggedArticles_list = new StringBuilder();

		ArrayList<Board> boards2 = articleService.getBoardsForPrint();
		StringBuilder topbar__menubox__boardList = new StringBuilder();
		for (Board board2 : boards2) {
			topbar__menubox__boardList.append("<li>");
			topbar__menubox__boardList
					.append("<a href=\"" + board2.getCode() + "-list.html?board=" + board2.getCode() + "&page=1\">");
			topbar__menubox__boardList.append("<span>");
			topbar__menubox__boardList.append(board2.getName() + "게시판");
			topbar__menubox__boardList.append("</span>");
			topbar__menubox__boardList.append("</a>");
			topbar__menubox__boardList.append("</li>");
		}
		tagList = tagList.replace("${topbar__menubox__boardList}", topbar__menubox__boardList.toString());
		taggedArticles_list.append("<tbody v-for=\"article in articles\">");
		taggedArticles_list.append("<tr class =\"line-separate\">");
		taggedArticles_list.append("<td colspan=\"6\"></td>");
		taggedArticles_list.append("</tr>");
		taggedArticles_list.append("<tr>");
		taggedArticles_list.append("<td class=\"cell-id\">" + "{{article.id}}" + "</td>");
		taggedArticles_list.append(
				"<td class=\"cell-title\"><a :href=\"'article-detail-'+article.id+'.html?board='+article.extra__boardCode+'&id='+article.id+'&page='+currentPage\">"
						+ "{{article.title}}</a>" + "</td>");
		taggedArticles_list.append("<td class=\"cell-writer\">" + "{{article.writer}}" + "</td>");
		taggedArticles_list.append("<td class=\"cell-regDate\">" + "{{article.regDate}}" + "</td>");
		taggedArticles_list.append("<td class=\"cell-hit\">" + "{{article.hitCount}}" + "</td>");
		taggedArticles_list.append("<td class=\"cell-recommend\">" + "{{article.likesCount}}" + "</td>");
		taggedArticles_list.append("</tr>");
		taggedArticles_list.append("</tbody>");

		tagList = tagList.replace("${board-list}", taggedArticles_list.toString());
		StringBuilder articles_list__firstpagebutton = new StringBuilder();
		articles_list__firstpagebutton.append("");

		articles_list__firstpagebutton.append("<div class=\"movePageFirst flex flex-basis-50px\">");
		articles_list__firstpagebutton.append("<span @click=\"movePageFirst\"><<</span>");
		articles_list__firstpagebutton.append("</div>");

		tagList = tagList.replace("${board-list__firstpagebutton}", articles_list__firstpagebutton.toString());

		StringBuilder articles_list__prevpagebutton = new StringBuilder();
		articles_list__prevpagebutton.append("");

		articles_list__prevpagebutton.append("<div class=\"flex flex-basis-50px\">");
		articles_list__prevpagebutton.append("<span class=\"movePagePrev\" @click=\"movePagePrev\">이전</span>");
		articles_list__prevpagebutton.append("</div>");

		tagList = tagList.replace("${board-list__prevpagebutton}", articles_list__prevpagebutton.toString());

		StringBuilder articles_list__pagebuttons = new StringBuilder();

		articles_list__pagebuttons.append("<li v-for=\"page in pages\" class=\"flex flex-jc-c flex-basis-50px\">");
		articles_list__pagebuttons.append("<span @click=\"movePage(page)\" class=\"currentPageCheck\">{{page}}</span>");
		articles_list__pagebuttons.append("</li>");

		tagList = tagList.replace("${board-list__pagebuttons}", articles_list__pagebuttons.toString());

		StringBuilder articles_list__nextpagebutton = new StringBuilder();
		articles_list__nextpagebutton.append("");

		articles_list__nextpagebutton.append("<div class=\"flex flex-basis-50px\">");
		articles_list__nextpagebutton.append("<span class=\"movePageNext\" @click=\"movePageNext\">다음</span>");
		articles_list__nextpagebutton.append("</div>");

		tagList = tagList.replace("${board-list__nextpagebutton}", articles_list__nextpagebutton.toString());

		StringBuilder article_list__lastpagebutton = new StringBuilder();

		article_list__lastpagebutton.append("<div class=\"movePageLast flex flex-basis-50px\">");
		article_list__lastpagebutton.append("<span @click=\"movePageLast\">>></span>");
		article_list__lastpagebutton.append("</div>");

		tagList = tagList.replace("${board-list__lastpagebutton}", article_list__lastpagebutton.toString());

		tagListHtml.append(tagList);
		tagListHtml.append(foot);

		Util.writeFileContents("site/" + fileName, tagListHtml.toString());

	}

	// 프로필 페이지 생성 함수
	private void createProfile(String pageName, String foot) {

		String fileName = "profile.html";

		String profileHtml = Util.getFileContents("site_template/profile/profile.html");

		StringBuilder profileBuilder = new StringBuilder();

		StringBuilder top__menubox__boardList = new StringBuilder();

		ArrayList<Board> boards = articleService.getBoardsForPrint();

		for (Board board : boards) {
			top__menubox__boardList.append("<li>");
			top__menubox__boardList
					.append("<a href=\"" + board.getCode() + "-list.html?board=" + board.getCode() + "&page=1\">");
			top__menubox__boardList.append("<span>");
			top__menubox__boardList.append(board.getName() + "게시판");
			top__menubox__boardList.append("</span>");
			top__menubox__boardList.append("</a>");
			top__menubox__boardList.append("</li>");
		}

		profileHtml = profileHtml.replace("${topbar__menubox__boardList}", top__menubox__boardList.toString());

		profileBuilder.append(getHeadHtml(pageName));
		profileBuilder.append(profileHtml);
		profileBuilder.append(foot);

		Util.writeFileContents("site/" + fileName, profileBuilder.toString());

	}

	// 통계페이지 생성 함수
	private void createStatDetail(String pageName, String foot) {
		ArrayList<Board> boards = articleService.getBoardsForPrint();
		String fileName = "statindex.html";


		StringBuilder statHtmlBuilder = new StringBuilder();
		String statHtml = Util.getFileContents("site_template/stat/index.html");
		String statJs = Util.getFileContents("site_template/resource/stat.js");
		statHtmlBuilder.append(getHeadHtml(pageName));

		ArrayList<Board> boards2 = articleService.getBoardsForPrint();
		StringBuilder topbar__menubox__boardList = new StringBuilder();
		for (Board board2 : boards2) {
			topbar__menubox__boardList.append("<li>");
			topbar__menubox__boardList
					.append("<a href=\"" + board2.getCode() + "-list.html?board=" + board2.getCode() + "&page=1\">");
			topbar__menubox__boardList.append("<span>");
			topbar__menubox__boardList.append(board2.getName() + "게시판");
			topbar__menubox__boardList.append("</span>");
			topbar__menubox__boardList.append("</a>");
			topbar__menubox__boardList.append("</li>");
		}
		statHtml = statHtml.replace("${topbar__menubox__boardList}", topbar__menubox__boardList.toString());

		String todayVisit = String.valueOf(Container.googleAnalyticsApiService.getTodayVisit());
		String totalVisit = String.valueOf(Container.googleAnalyticsApiService.getTotalVisit());
		statHtml = statHtml.replace("${totalvisit}", totalVisit);
		statHtml = statHtml.replace("${todayvisit}", todayVisit);
		StringBuilder articleHitChartHtml = new StringBuilder();
		StringBuilder articleHitChartJs = new StringBuilder();

		for (Board board : boards) {

			List<Article> articles = articleService.getArticlesForStaticPage(board.getId());
			if (articles.size() == 0) {
				articleHitChartHtml.append("");
				articleHitChartJs.append("");
				break;
			}
			
			articleHitChartHtml.append(" <div class=\"con flex flex-jc-s-ar articleHitBox\">");
			articleHitChartHtml.append("<div class=\"flex flex-dir-col flex-jc-c articleHitBox__articleHitText\">");
			articleHitChartHtml.append(
					"<div class=\"articleHitBox__articleHitText__boardName\">" + board.getName() + " 게시판</div>");
			
			int totalHit = articleService.getBoardTotalHitByBoardId(board.getId());
			
			articleHitChartHtml.append("<div class=\"articleHitBox__articleHitText__boardName__totalhit\">전체 조회수 "+totalHit+"</div>");
			articleHitChartHtml.append("<ul>");
			for (Article article : articles) {
				articleHitChartHtml.append("<li><a href=\"article-detail-" + article.getId() + ".html?board="
						+ article.getExtra__boardCode() + "&id=" + article.getId() + "\">" + article.getTitle()
						+ "</a><span>"+article.getHitCount()+"</span></li>");
			}

			articleHitChartHtml.append("</ul>");
			articleHitChartHtml.append("</div>");
			articleHitChartHtml.append("<div class=\"articleHitChartBox\">");
			articleHitChartHtml.append("<canvas id=\"articleHitChart"+board.getId()+"\" class=\"articleHitChart\"></canvas>");
			articleHitChartHtml.append("</div>");
			articleHitChartHtml.append("</div>");

			articleHitChartJs.append("var articleHit" + board.getId() + " = document.getElementById('articleHitChart"
					+ board.getId() + "');");
			articleHitChartJs.append("var chart" + board.getId() + " = new Chart(articleHit" + board.getId() + ", {");
			articleHitChartJs.append("type: 'bar',");
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
			articleHitChartJs.append("label: '조회수',");
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
			articleHitChartJs.append("options: {");
			articleHitChartJs.append("aspectRatio:1,");
			articleHitChartJs.append("scales:{");
			articleHitChartJs.append("yAxes: [{");
			articleHitChartJs.append("display: true,");
			articleHitChartJs.append("ticks: {");
			articleHitChartJs.append("beginAtZero: true");
			articleHitChartJs.append("}}]}");
			
			articleHitChartJs.append("}");
			articleHitChartJs.append("});");

		}
		statJs = statJs.replace("{{articleHitChart}}", articleHitChartJs.toString());
		statHtml = statHtml.replace("${{articleHitChart}}", articleHitChartHtml.toString());
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
				String fileName = "article-detail-" + articles.get(i).getId() + ".html";
				StringBuilder articleDetailHtmlBuilder = new StringBuilder();
				Article article = articles.get(i);
				articleDetailHtmlBuilder.append(getHeadHtml(pageName, article));
				ArrayList<Board> boards2 = articleService.getBoardsForPrint();
				StringBuilder topbar__menubox__boardList = new StringBuilder();
				for (Board board2 : boards2) {
					topbar__menubox__boardList.append("<li>");
					topbar__menubox__boardList
							.append("<a href=\"" + board2.getCode() + "-list.html?board=" + board2.getCode() + "&page=1\">");
					topbar__menubox__boardList.append("<span>");
					topbar__menubox__boardList.append(board2.getName() + "게시판");
					topbar__menubox__boardList.append("</span>");
					topbar__menubox__boardList.append("</a>");
					topbar__menubox__boardList.append("</li>");
				}
				articleDetailHtml = articleDetailHtml.replace("${topbar__menubox__boardList}",
						topbar__menubox__boardList.toString());
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

				List<Tag> tags = articleService.getTagById(articles.get(i).getId());
				StringBuilder articleDetail__tags = new StringBuilder();
				if (tags != null) {

					for (Tag tag : tags) {
						articleDetail__tags.append(
								"<a href=\"article_tag.html?tag=" + tag.getBody() + "&page=1\">#" + tag.getBody() + " </a>");
					}

				}
				articleDetailHtml = articleDetailHtml.replace("${articledetail__tags}", articleDetail__tags.toString());

				StringBuilder articleDetail__ArticleMoveButton = new StringBuilder();
				articleDetail__ArticleMoveButton.append("");
				if (i != articles.size() - 1) {
					articleDetail__ArticleMoveButton.append("<div class=\"con next-article\"><a :href=\"'article-detail-"
							+ articles.get(i + 1).getId() + ".html?board=" + board.getCode() + "&page='+currentPage\">다음글</a></div>");
				}

				if (i != 0) {
					articleDetail__ArticleMoveButton.append("<div class=\"con next-article\"><a :href=\"'article-detail-"
							+ articles.get(i - 1).getId() + ".html?board=" + board.getCode() + "&page='+currentPage\">이전글</a></div>");
				}

				articleDetailHtml = articleDetailHtml.replace("${articledetail_articlemovebutton}",
						articleDetail__ArticleMoveButton.toString());

				StringBuilder articleDetail__articlelist_boardName = new StringBuilder();

				articleDetail__articlelist_boardName.append("<a href=\"" + board.getCode() + "-list.html?board="
						+ board.getCode() + "\"><span>" + board.getName() + " 게시판</span></a>");

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
				articleDetail__articleList.append("<tbody class=\"articles_notExists\">");
				articleDetail__articleList.append("<tr>");
				articleDetail__articleList.append("<td colspan=\"6\">검색 결과가 없습니다.</td>");
				articleDetail__articleList.append("</tr>");
				articleDetail__articleList.append("</tbody>");

				articleDetail__articleList.append("<tbody class=\"articles_exists\" v-for=\"article in articles\">");
				articleDetail__articleList.append("<tr class =\"line-separate\">");
				articleDetail__articleList.append("<td colspan=\"6\"></td>");
				articleDetail__articleList.append("</tr>");
				articleDetail__articleList.append("<tr>");
				articleDetail__articleList.append("<td class=\"cell-id\">" + "{{article.id}}" + "</td>");
				articleDetail__articleList
						.append("<td class=\"cell-title\"><a :href=\"'article-detail-'+article.id+'.html?board="
								+ board.getCode() + "&id='+article.id+'&page='+currentPage\">" + "{{article.title}}</a>" + "</td>");
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

				articledetail__articlelist_firstpagelink.append("<div class=\"movePageFirst flex flex-basis-50px\">");
				articledetail__articlelist_firstpagelink.append("<span @click=\"movePageFirst\">&lt;&lt;</span>");
				articledetail__articlelist_firstpagelink.append("</div>");

				articleDetailHtml = articleDetailHtml.replace("${articledetail__articlelist-firstpagelink}",
						articledetail__articlelist_firstpagelink.toString());

				StringBuilder articledetail__articlelist_prevpagelink = new StringBuilder();
				articledetail__articlelist_prevpagelink.append("");

				articledetail__articlelist_prevpagelink.append("<div class=\"flex flex-basis-50px\">");
				articledetail__articlelist_prevpagelink
						.append("<span class=\"movePagePrev\" @click=\"movePagePrev\">이전</span>");
				articledetail__articlelist_prevpagelink.append("</div>");

				articleDetailHtml = articleDetailHtml.replace("${articledetail__articlelist-prevpagelink}",
						articledetail__articlelist_prevpagelink.toString());

				StringBuilder articledetail__articlelist_pages = new StringBuilder();

				articledetail__articlelist_pages
						.append("<li v-for=\"page in pages\" class=\"flex flex-jc-c flex-basis-50px\">");
				articledetail__articlelist_pages
						.append("<span @click=\"movePage(page)\" class=\"currentPageCheck\">{{page}}</span>");
				articledetail__articlelist_pages.append("</li>");

				articleDetailHtml = articleDetailHtml.replace("${articledetail__articlelist-pages}",
						articledetail__articlelist_pages.toString());

				StringBuilder articledetail__articlelist_nextpagelink = new StringBuilder();
				articledetail__articlelist_nextpagelink.append("");

				articledetail__articlelist_nextpagelink.append("<div class=\"flex flex-basis-50px\">");
				articledetail__articlelist_nextpagelink
						.append("<span class=\"movePageNext\" @click=\"movePageNext\">다음</span>");
				articledetail__articlelist_nextpagelink.append("</div>");

				articleDetailHtml = articleDetailHtml.replace("${articledetail__articlelist-nextpagelink}",
						articledetail__articlelist_nextpagelink.toString());

				StringBuilder articledetail__articlelist_lastpagelink = new StringBuilder();

				articledetail__articlelist_lastpagelink.append("<div class=\"movePageLast flex flex-basis-50px\">");
				articledetail__articlelist_lastpagelink.append("<span @click=\"movePageLast\">&gt;&gt;</span>");
				articledetail__articlelist_lastpagelink.append("</div>");

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

		ArrayList<Board> boards = articleService.getBoardsForPrint();
		for (Board board : boards) {

			List<Article> articles = articleService.getArticleByBoardCodeDesc(board.getCode());

			String jsonText = Util.getJsonText(articles);
			Util.writeFile("site/" + board.getCode() + "-list.json", jsonText);

			String boardPageHtml = Util.getFileContents("site_template/article/list/list.html");

			StringBuilder boardPageHtmlBuilder = new StringBuilder();

			StringBuilder topbar__menubox__boardList = new StringBuilder();

			ArrayList<Board> boards2 = articleService.getBoardsForPrint();

			for (Board board2 : boards2) {
				topbar__menubox__boardList.append("<li>");
				topbar__menubox__boardList
						.append("<a href=\"" + board2.getCode() + "-list.html?board=" + board2.getCode() + "&page=1\">");
				topbar__menubox__boardList.append("<span>");
				topbar__menubox__boardList.append(board2.getName() + "게시판");
				topbar__menubox__boardList.append("</span>");
				topbar__menubox__boardList.append("</a>");
				topbar__menubox__boardList.append("</li>");
			}
			boardPageHtml = boardPageHtml.replace("${topbar__menubox__boardList}",
					topbar__menubox__boardList.toString());

			String fileName = "";
			fileName = board.getCode() + "-list.html";

			boardPageHtmlBuilder.append(getHeadHtml(pageName));

			StringBuilder board_list__boardname = new StringBuilder();

			board_list__boardname.append("<a href=\"" + board.getCode() + "-list.html" + board.getCode() + "\"><span>"
					+ board.getName() + " 게시판</span></a>");

			boardPageHtml = boardPageHtml.replace("${board-list__boardname}", board_list__boardname.toString());

			StringBuilder board_list = new StringBuilder();
			board_list.append("<tbody class=\"articles_notExists\">");
			board_list.append("<tr>");
			board_list.append("<td colspan=\"6\">검색 결과가 없습니다.</td>");
			board_list.append("</tr>");
			board_list.append("</tbody>");

			board_list.append("<tbody class=\"articles_exists\" v-for=\"article in articles\">");
			board_list.append("<tr class =\"line-separate\">");
			board_list.append("<td colspan=\"6\"></td>");
			board_list.append("</tr>");
			board_list.append("<tr>");
			board_list.append("<td class=\"cell-id\">" + "{{article.id}}" + "</td>");
			board_list.append("<td class=\"cell-title\"><a :href=\"'article-detail-'+article.id+'.html?board="
					+ board.getCode() + "&id='+article.id+'&page='+currentPage\">" + "{{article.title}}</a>" + "</td>");
			board_list.append("<td class=\"cell-writer\">" + "{{article.writer}}" + "</td>");
			board_list.append("<td class=\"cell-regDate\">" + "{{article.regDate}}" + "</td>");
			board_list.append("<td class=\"cell-hit\">" + "{{article.hitCount}}" + "</td>");
			board_list.append("<td class=\"cell-recommend\">" + "{{article.likesCount}}" + "</td>");
			board_list.append("</tr>");
			board_list.append("</tbody>");

			boardPageHtml = boardPageHtml.replace("${board-list}", board_list.toString());

			StringBuilder board_list__firstpagebutton = new StringBuilder();
			board_list__firstpagebutton.append("");

			board_list__firstpagebutton.append("<div class=\"movePageFirst flex flex-basis-50px\">");
			board_list__firstpagebutton.append("<span @click=\"movePageFirst\">&lt;&lt;</span>");
			board_list__firstpagebutton.append("</div>");

			boardPageHtml = boardPageHtml.replace("${board-list__firstpagebutton}",
					board_list__firstpagebutton.toString());

			StringBuilder board_list__prevpagebutton = new StringBuilder();
			board_list__prevpagebutton.append("");

			board_list__prevpagebutton.append("<div class=\"flex flex-basis-50px\">");
			board_list__prevpagebutton.append("<span class=\"movePagePrev\" @click=\"movePagePrev\">이전</span>");
			board_list__prevpagebutton.append("</div>");

			boardPageHtml = boardPageHtml.replace("${board-list__prevpagebutton}",
					board_list__prevpagebutton.toString());

			StringBuilder board_list__pagebuttons = new StringBuilder();

			board_list__pagebuttons.append("<li v-for=\"page in pages\" class=\"flex flex-jc-c flex-basis-50px\">");
			board_list__pagebuttons
					.append("<span @click=\"movePage(page)\" class=\"currentPageCheck\">{{page}}</span>");
			board_list__pagebuttons.append("</li>");

			boardPageHtml = boardPageHtml.replace("${board-list__pagebuttons}", board_list__pagebuttons.toString());

			StringBuilder board_list__nextpagebutton = new StringBuilder();
			board_list__nextpagebutton.append("");

			board_list__nextpagebutton.append("<div class=\"flex flex-basis-50px\">");
			board_list__nextpagebutton.append("<span class=\"movePageNext\" @click=\"movePageNext\">다음</span>");
			board_list__nextpagebutton.append("</div>");

			boardPageHtml = boardPageHtml.replace("${board-list__nextpagebutton}",
					board_list__nextpagebutton.toString());

			StringBuilder board_list__lastpagebutton = new StringBuilder();

			board_list__lastpagebutton.append("<div class=\"movePageLast flex flex-basis-50px\">");
			board_list__lastpagebutton.append("<span @click=\"movePageLast\">&gt;&gt;</span>");
			board_list__lastpagebutton.append("</div>");

			boardPageHtml = boardPageHtml.replace("${board-list__lastpagebutton}",
					board_list__lastpagebutton.toString());

			boardPageHtmlBuilder.append(boardPageHtml);

			boardPageHtmlBuilder.append(foot);

			Util.writeFileContents("site/" + fileName, boardPageHtmlBuilder.toString());

		}

	}

	// 메인화면 생성 함수
	private void createMainPage(String pageName, String foot) {

		StringBuilder mainPageHtml = new StringBuilder();

		mainPageHtml.append(getHeadHtml(pageName));

		String homeHtml = Util.getFileContents("site_template/home/index.html");

		StringBuilder homeHtmlTemplate = new StringBuilder();

		ArrayList<Board> boards = articleService.getBoardsForPrint();

		for (Board board : boards) {
			homeHtmlTemplate.append("<li>");
			homeHtmlTemplate.append("<a href=\"" + board.getCode() + "-list.html?board=" + board.getCode() + "&page=1\">");
			homeHtmlTemplate.append("<span>");
			homeHtmlTemplate.append(board.getName() + "게시판");
			homeHtmlTemplate.append("</span>");
			homeHtmlTemplate.append("</a>");
			homeHtmlTemplate.append("</li>");
		}

		homeHtml = homeHtml.replace("${topbar__menubox__boardList}", homeHtmlTemplate.toString());

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
