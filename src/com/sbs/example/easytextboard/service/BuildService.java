package com.sbs.example.easytextboard.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.Article;
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
		String sourceFile = "site_template/part/app.css";

		Util.copy(sourceFile, "site/article/app.css");
		Util.copy(sourceFile, "site/stat/app.css");
		Util.copy(sourceFile, "site/home/app.css");

		String head = Util.getFileContents("site_template/part/head.html");
		String foot = Util.getFileContents("site_template/part/foot.html");

		ArrayList<Board> boards = articleService.getBoardsForPrint();
		StringBuilder changeMenu = new StringBuilder();
		for (Board board : boards) {
			changeMenu.append("<li>");
			changeMenu.append("<a href=\"../article/" + board.getCode() + "-list-1.html" + "\">");
			switch(board.getCode()) {
			case "notice":
				changeMenu.append("<i class=\"fas fa-flag\"></i>");
				break;
			case "free":
				changeMenu.append("<i class=\"fas fa-clipboard\"></i>");
				break;
			default:
				changeMenu.append("<i class=\"far fa-clipboard\"></i>");
				break;
			}
			
			changeMenu.append("<span>");
			changeMenu.append(board.getName() + "게시판");
			changeMenu.append("</span>");
			changeMenu.append("</a>");
			changeMenu.append("</li>");
		}

		head = head.replace("boardListHead", changeMenu.toString());
		
		// 메인홈 화면 페이지
		File homeDir = new File("site/home/");
		if (homeDir.exists() == false) {
			homeDir.mkdirs();
		}
		
		StringBuilder homeSb = new StringBuilder();
		homeSb.append(head);
		homeSb.append("<main class=\"flex-grow-1\">");
		homeSb.append("<section class=\"con-min-width\">");
		homeSb.append("<div class=\"con home-title-box\">");
		homeSb.append(" <i class=\"fas fa-home\"></i>");
		homeSb.append("<span>HOME</span>");
		homeSb.append("</div>");
		homeSb.append("</section>");
		
		homeSb.append("<section class=\"home-body-box con-min-width\">");
		homeSb.append("<div class=\"con\">");
		String homeBody = Util.getFileContents("site/home/homeBody.txt");
		homeSb.append(homeBody);
		homeSb.append("</div>");
		homeSb.append("</section>");		
		homeSb.append("</main>");
		homeSb.append(foot);
		Util.writeFileContents("site/home/" + "index.html", homeSb.toString());
		
		
		// 게시물 페이지와 게시물
		for (Board board : boards) {

			File file = new File("site/article/");
			if (file.exists() == false) {
				file.mkdirs();
			}

			List<Article> articles = articleService.getArticlesByBoardCode(board.getCode());

			// 게시판 페이징 시작
			int pages = articles.size() / 10;
			if (articles.size() % 10 != 0) {
				pages++;
			}
			int itemsInAPage = 10;
			if (pages == 0 && articles.size() > 0) {
				pages = 1;
			}
			if (articles.size() == 0) {
				StringBuilder sb = new StringBuilder();
				String fileName = "";
				fileName = board.getCode() + "-list-1.html";
				sb.append(head);
				sb.append("<main class=\"flex-grow-1\">");
				sb.append("<section class=\"title-bar con-min-width\">");
				sb.append("<h1 class=\"title-bar__title-box con flex flex-jc-c\">");
				switch(board.getCode()) {
				case "notice":
					sb.append("<i class=\"fas fa-flag\"></i>");
					break;
				case "free":
					sb.append("<i class=\"fas fa-clipboard\"></i>");
					break;
				default:
					sb.append("<i class=\"far fa-clipboard\"></i>");
					break;
				}
				sb.append("<span>");
				sb.append(board.getName());
				sb.append("</span>");
				sb.append("</h1>");
				sb.append("</section>");
				
				
				
				sb.append("<section class=\"board-list con-min-width\">");
				sb.append("<table class =\"con\">");
				sb.append("<tr class =\"tag\">");
				sb.append("<td class =\"font-bold cell-id\">번호</td>");
				sb.append("<td class =\"font-bold cell-title\">제목</td>");
				sb.append("<td class =\"font-bold cell-writer\">작성자</td>");
				sb.append("<td class =\"font-bold cell-regDate\">작성일</td>");
				sb.append("<td class =\"font-bold cell-hit\">조회 수</td>");
				sb.append("<td class =\"font-bold cell-recommand\">추천 수</td>");
				sb.append("</tr>");
				sb.append("<tr>");
				sb.append("<td colspan=\"6\" class =\"line-separate\"></td>");
				sb.append("</tr>");
				sb.append("<tr>");
				sb.append("<td colspan=\"6\" class=\"font-bold \">작성된 게시물이 없습니다.</td>");
				sb.append("</tr>");
				sb.append("</table>");
				sb.append("</section>");
				sb.append("</section>");
				sb.append("</main>");
				sb.append(foot);
				Util.writeFileContents("site/article/" + fileName, sb.toString());
			} else if (articles.size() != 0) {
				for (int i = 1; i <= pages; i++) {
					StringBuilder sb = new StringBuilder();
					String fileName = "";
					fileName = board.getCode() + "-list-" + i + ".html";

					sb.append(head);
					sb.append("<main class=\"flex-grow-1\">");
					sb.append("<section class=\"title-bar con-min-width\">");
					sb.append("<h1 class=\"title-bar__title-box con flex flex-jc-c\">");
					switch(board.getCode()) {
					case "notice":
						sb.append("<i class=\"fas fa-flag\"></i>");
						break;
					case "free":
						sb.append("<i class=\"fas fa-clipboard\"></i>");
						break;
					default:
						sb.append("<i class=\"far fa-clipboard\"></i>");
						break;
					}
					sb.append("<span>");
					sb.append(board.getName());
					sb.append("</span>");
					sb.append("</h1>");
					sb.append("</section>");

					sb.append("<section class=\"board-list con-min-width\">");
					sb.append("<table class =\"con\">");
					sb.append("<tr class =\"tag\">");
					sb.append("<td class =\"font-bold cell-id\">번호</td>");
					sb.append("<td class =\"font-bold cell-title\">제목</td>");
					sb.append("<td class =\"font-bold cell-writer\">작성자</td>");
					sb.append("<td class =\"font-bold cell-regDate\">작성일</td>");
					sb.append("<td class =\"font-bold cell-hit\">조회 수</td>");
					sb.append("<td class =\"font-bold cell-recommand\">추천 수</td>");
					sb.append("</tr>");

					int start = articles.size() - ((i - 1) * itemsInAPage) - 1;
					int end = (start - itemsInAPage + 1);
					if (end < 0) {
						end = 0;
					}

					for (int j = start; j >= end; j--) {
						sb.append("<tr>");
						sb.append("<td colspan=\"6\" class =\"line-separate\"></td>");
						sb.append("</tr>");
						sb.append("<tr>");
						sb.append("<td class=\"cell-id\">" + articles.get(j).getId() + "</td>");
						sb.append("<td class=\"cell-title\"><a href=\"" + board.getCode() + "-"
								+ articles.get(j).getId() + ".html\">" + articles.get(j).getTitle() + "</td>");
						sb.append("<td class=\"cell-writer\">" + articles.get(j).getExtraWriter() + "</td>");
						sb.append("<td class=\"cell-regDate\">" + articles.get(j).getRegDate() + "</td>");
						sb.append("<td class=\"cell-hit\">" + articles.get(j).getHit() + "</td>");
						sb.append("<td class=\"cell-recommand\">"
								+ articleService.getArticleRecommand(articles.get(j).getId()) + "</td>");
						sb.append("</tr>");
					}

					sb.append("</table>");
					sb.append("</section>");

					sb.append("<section class=\"page-button con-min-width\">");
					sb.append("<div class=\"con flex flex-jc-s-bet\">");
					
					if(pages >= 2) {
						sb.append("<div class=\"flex flex-basis-50px\">");
						sb.append("<a href=\"" +board.getCode() +"-list-1.html\"> << </a>");
						sb.append("</div>");
					}
					if (i != 1) {
						sb.append("<div class=\"flex flex-basis-50px\">");
						sb.append("<a href=\"" + board.getCode() + "-list-" + (i - 1) + ".html\"> < </a>");
						sb.append("</div>");
					}					

					sb.append("<ul class=\"flex flex-jc-s-bet flex-grow-1\">");	
					
					for (int k = 1; k <= pages; k++) {
						String page = board.getCode() + "-list-" + k + ".html";
						sb.append("<li class=\"flex flex-jc-c flex-grow-1\">");
						sb.append("<a href=\"" + page + "\"> " + k + "</a>");
						sb.append("</li>");
					}
					
					sb.append("</ul>");
					
					if (i < pages) {
						sb.append("<div class=\"flex flex-basis-50px\">");
						sb.append("<a href=\"" + board.getCode() + "-list-" + (i + 1) + ".html\"> > </a>");
						sb.append("</div>");
					}

					
					if(pages >= 2) {
						sb.append("<div class=\"flex flex-basis-50px\">");
						sb.append("<a href=\"" +board.getCode() +"-list-"+ pages+".html\"> >> </a>");
						sb.append("</div>");
					}
					
					sb.append("</div>");
					sb.append("</section>");
					sb.append("</main>");
					sb.append(foot);

					Util.writeFileContents("site/article/" + fileName, sb.toString());
				}
			}
			
			// 게시판 페이징 끝

			// 게시물 상세보기 시작
			for (int i = 0; i < articles.size(); i++) {
				String writer = memberService.getMemberById(articles.get(i).getMemberId()).getName();

				String fileName = board.getCode() + "-" + articles.get(i).getId() + ".html";
				StringBuilder sb = new StringBuilder();
				sb.append(head);
				sb.append("<main class=\"flex-grow-1\">");
				sb.append("<section class=\"article con-min-width\">");
				sb.append("<div class=\"con\">");
				sb.append("<div>번호 : " + articles.get(i).getId() + "</div>");
				sb.append("<div>날짜 : " + articles.get(i).getRegDate() + "</div>");
				sb.append("<div>작성자 : " + writer + "</div>");
				sb.append("<div>제목 : " + articles.get(i).getTitle() + "</div>");
				sb.append("<div>내용 : " + articles.get(i).getBody() + "</div>");
				sb.append("</div>");

				sb.append("<div class=\"con-min-width article-move-button\">");

				if (i != articles.size() - 1) {
					sb.append("<div class=\"con next-article\"><a href=\"" + board.getCode() + "-"
							+ articles.get(i + 1).getId() + ".html\">다음글</a></div>");
				}

				if (i != 0) {
					sb.append("<div class=\"con next-article\"><a href=\"" + board.getCode() + "-"
							+ articles.get(i - 1).getId() + ".html\">이전글</a></div>");
				}
				sb.append("</div>");
				sb.append("</section>");
				sb.append("</main>");
				sb.append(foot);

				Util.writeFileContents("site/article/" + fileName, sb.toString());
			}
			// 게시물 상세보기 끝

		}

		// 통계
		File file = new File("site/stat/");
		if (file.exists() == false) {
			file.mkdir();
		}

		String fileName = "index.html";

		List<Member> members = memberService.getMembers();
		List<Article> articles = articleService.getArticles();

		StringBuilder sb = new StringBuilder();
		head = head.replace("<a href=\"notice-list-1.html\">", "<a href=\"../article/notice-list-1.html\">");
		head = head.replace("<a href=\"free-list-1.html\">", "<a href=\"../article/free-list-1.html\">");
		sb.append(head);
		sb.append("<main class=\"flex-grow-1\">");
		sb.append("<section class=\"stat-box con-min-width\">");
		sb.append("<div class = \"con\">");
		sb.append("<ul>");
		sb.append("<li>회원 수 : " + members.size() + "</li>");
		sb.append("<li>전체 게시물 수 : " + articles.size() + "</li>");
		sb.append("<li>각 게시판 별 게시물 수");
		sb.append("<ul>");
		for (Board board : boards) {
			List<Article> articlesByBoard = articleService.getArticlesByBoardCode(board.getCode());
			sb.append("<li>" + board.getName() + " : " + articlesByBoard.size() + "</li>");
		}
		sb.append("</ul>");
		sb.append("</li>");
		int hitSum = 0;
		for (Article article : articles) {
			hitSum += article.getHit();
		}

		sb.append("<li>전체 게시물 조회 수 : " + hitSum + "</li>");

		sb.append("<li>각 게시판 별 조회 수");
		sb.append("<ul>");
		for (Board board : boards) {
			List<Article> articlesByBoard = articleService.getArticlesByBoardCode(board.getCode());
			int hitSumByBoard = 0;
			for (Article article : articlesByBoard) {
				hitSumByBoard += article.getHit();
			}
			sb.append("<li>" + board.getName() + " : " + hitSumByBoard + "</li>");

		}
		sb.append("</li>");
		sb.append("</ul>");

		sb.append("</ul>");
		sb.append("</section>");
		sb.append("</main>");

		sb.append(foot);
		Util.writeFileContents("site/stat/" + fileName, sb.toString());
	}

}
