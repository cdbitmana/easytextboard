package com.sbs.example.easytextboard.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.Article;
import com.sbs.example.easytextboard.dto.Board;
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

		List<Board> boards = articleService.getBoardsForPrint();

		for (Board board : boards) {
			String fileName = board.getCode() + "-list-1.html";

			String html = "<table>";

			List<Article> articles = articleService.getArticlesByBoardCode(board.getCode());

			for (Article article : articles) {
				html += "<tr>";
				html += "<th>" + article.getId() + "</th>";
				html += "<th>" + article.getTitle() + "</th>";
				html += "<th>" + article.getExtraWriter() + "</th>";
				html += "<th>" + article.getHit() + "</th>";
				html += "<th>" + articleService.getArticleRecommand(article.getId()) + "</th>";
				html += "</tr>";
			}

			html += "</table>";
			html = head + html + foot;

			Util.writeFileContents("site/" + fileName, html);

		}

		List<Article> articles = articleService.getArticlesForPrint();

		for (Article article : articles) {
			String writer = memberService.getMemberById(article.getMemberId()).getName();

			String fileName = article.getId() + ".html";
			String html = "<div>번호 : " + article.getId() + "</div>";
			html += "<div>날짜 : " + article.getRegDate() + "</div>";
			html += "<div>작성자 : " + writer + "</div>";
			html += "<div>제목 : " + article.getTitle() + "</div>";
			html += "<div>내용 : " + article.getBody() + "</div>";

			if (article.getId() > 1) {
				html += "<div><a href=\"" + (article.getId() - 1) + ".html\">이전글</a></div>";
			}

			html += "<div><a href=\"" + (article.getId() + 1) + ".html\">다음글</a></div>";

			File file = new File("site/");
			if (file.exists() == false) {
				file.mkdir();
			}

			html = head + html + foot;

			Util.writeFileContents("site/" + fileName, html);
		}

	}

}
