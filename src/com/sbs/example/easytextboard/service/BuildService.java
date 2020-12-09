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

		String head = Util.getFileContents("site_template/part/head.html");
		String foot = Util.getFileContents("site_template/part/foot.html");

		// 게시물 페이지와 게시물
		List<Board> boards = articleService.getBoardsForPrint();
		for (Board board : boards) {

			File file = new File("site/article/");
			if (file.exists() == false) {
				file.mkdirs();
			}

			List<Article> articles = articleService.getArticlesByBoardCode(board.getCode());

			int pages = articles.size() / 10;
			if (articles.size() % 10 != 0) {
				pages++;
			}
			int itemsInAPage = 10;

			for (int i = 1; i <= pages; i++) {
				String fileName = "";
				fileName = board.getCode() + "-list-" + i + ".html";

				int start = articles.size() - ((i - 1) * itemsInAPage) - 1;
				int end = (start - itemsInAPage + 1);
				if (end < 0) {
					end = 0;
				}

				String html = "<section class=\"board-list con-min-width\">";
				html += "<table class =\"con\">";
				html += "<tr class =\"tag\">";
				html += "<td class =\"font-bold\">번호</td>";
				html += "<td class =\"font-bold\">제목</td>";
				html += "<td class =\"font-bold\">작성자</td>";
				html += "<td class =\"font-bold\">작성일</td>";
				html += "<td class =\"font-bold\">조회 수</td>";
				html += "<td class =\"font-bold\">추천 수</td>";
				html += "</tr>";
				for (int j = start; j >= end; j--) {
					html += "<tr>";
					html += "<td colspan=\"6\" class =\"line-separate\"></td>";
					html += "</tr>";
					html += "<tr>";
					html += "<td class=\"cell-id\">" + articles.get(j).getId() + "</td>";
					html += "<td class=\"cell-title\"><a href=\"" + board.getCode() + "-" + articles.get(j).getId()
							+ ".html\">" + articles.get(j).getTitle() + "</td>";
					html += "<td class=\"cell-writer\">" + articles.get(j).getExtraWriter() + "</td>";
					html += "<td class=\"cell-regDate\">" + articles.get(j).getRegDate() + "</td>";
					html += "<td class=\"cell-hit\">" + articles.get(j).getHit() + "</td>";
					html += "<td class=\"cell-recommand\">"
							+ articleService.getArticleRecommand(articles.get(j).getId()) + "</td>";
					html += "</tr>";

				}
				html += "</table>";
				html += "</section>";

				html += "<section class=\"page-button con-min-width\">";
				html += "<div class=\"con\">";
				html += "<ul>";

				if (i != 1) {

					html += "<li class=\"page-button-left\">";
					html += "<a href=\"" + board.getCode() + "-list-" + (i - 1) + ".html\"> < </a>";
					html += "</li>";
				}
				for (int k = 1; k <= pages; k++) {
					String page = board.getCode() + "-list-" + k + ".html";
					html += "<li>";
					html += "<a href=\"" + page + "\"> " + k + "</a>";
					html += "</li>";
				}

				if (i < pages) {
					html += "<li class=\"page-button-right\">";
					html += "<a href=\"" + board.getCode() + "-list-" + (i + 1) + ".html\"> > </a>";
					html += "</li>";
				}

				html += "</ul>";
				html += "</div>";
				html += "</section>";

				html = head + html + foot;
				Util.writeFileContents("site/article/" + fileName, html);
			}

			for (int i = 0; i < articles.size(); i++) {
				String writer = memberService.getMemberById(articles.get(i).getMemberId()).getName();

				String fileName = board.getCode() + "-" + articles.get(i).getId() + ".html";
				String html = "";
				html += "<section class=\"article con-min-width\">";
				html += "<div class=\"con\">";
				html += "<div>번호 : " + articles.get(i).getId() + "</div>";
				html += "<div>날짜 : " + articles.get(i).getRegDate() + "</div>";
				html += "<div>작성자 : " + writer + "</div>";
				html += "<div>제목 : " + articles.get(i).getTitle() + "</div>";
				html += "<div>내용 : " + articles.get(i).getBody() + "</div>";
				html += "</div>";

				html += "<div class=\"con-min-width article-move-button\">";
				if (i != articles.size() - 1) {
					int articleId = articles.get(i).getId() + 1;
					while (true) {
						String fileCheck = "";
						fileCheck += board.getCode() + "-" + articleId + ".html";
						if (Util.isFileExists("site/article/" + fileCheck)) {
							html += "<div class=\"con next-article\"><a href=\"" + board.getCode() + "-" + articleId
									+ ".html\">다음글</a></div>";
							break;
						} else if (Util.isFileExists(fileCheck) == false) {
							articleId++;
						}
					}
				}
				if (i != 0) {
					int articleId = articles.get(i).getId() - 1;
					while (true) {
						String fileCheck = "";
						fileCheck += board.getCode() + "-" + articleId + ".html";
						if (Util.isFileExists("site/article/" + fileCheck)) {
							html += "<div class=\"con next-article\"><a href=\"" + board.getCode() + "-" + articleId
									+ ".html\">이전글</a></div>";
							break;
						} else if (Util.isFileExists(fileCheck) == false) {
							articleId--;
						}
					}
				}
				html += "</div>";
				html += "</section>";
				html = head + html + foot;

				Util.writeFileContents("site/article/" + fileName, html);
			}

		}

		// 통계
		File file = new File("site/stat/");
		if (file.exists() == false) {
			file.mkdir();
		}

		String fileName = "index.html";

		List<Member> members = memberService.getMembers();
		List<Article> articles = articleService.getArticles();

		String html = "";

		html += "<section class=\"stat-box con-min-width\">";
		html += "<div class = \"con\">";
		html += "<ul>";
		html += "<li>회원 수 : " + members.size() + "</li>";
		html += "<li>전체 게시물 수 : " + articles.size() + "</li>";
		html += "<li>각 게시판 별 게시물 수";
		html += "<ul>";
		for (Board board : boards) {
			List<Article> articlesByBoard = articleService.getArticlesByBoardCode(board.getCode());
			html += "<li>" + board.getName() + " : " + articlesByBoard.size() + "</li>";
		}
		html += "</ul>";
		html += "</li>";
		int hitSum = 0;
		for (Article article : articles) {
			hitSum += article.getHit();
		}

		html += "<li>전체 게시물 조회 수 : " + hitSum + "</li>";

		html += "<li>각 게시판 별 조회 수";
		html += "<ul>";
		for (Board board : boards) {
			List<Article> articlesByBoard = articleService.getArticlesByBoardCode(board.getCode());
			int hitSumByBoard = 0;
			for (Article article : articlesByBoard) {
				hitSumByBoard += article.getHit();
			}
			html += "<li>" + board.getName() + " : " + hitSumByBoard + "</li>";

		}
		html += "</li>";
		html += "</ul>";

		html += "</ul>";
		html += "</section>";
		head = head.replace("<a href=\"notice-list-1.html\">", "<a href=\"../article/notice-list-1.html\">");
		head = head.replace("<a href=\"free-list-1.html\">", "<a href=\"../article/free-list-1.html\">");
		html = head + html + foot;
		Util.writeFileContents("site/stat/" + fileName, html);
	}

}
