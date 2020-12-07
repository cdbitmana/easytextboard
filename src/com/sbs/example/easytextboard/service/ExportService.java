package com.sbs.example.easytextboard.service;

import java.util.ArrayList;
import java.util.List;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.Article;
import com.sbs.example.easytextboard.util.Util;

public class ExportService {
	
	ArticleService articleService;
	MemberService memberService;

	public ExportService() {
		articleService = Container.articleService;
		memberService = Container.memberService;
	}

	public void makeHtml() {
		
		List<Article> articles = articleService.getArticlesForPrint();
		
		for(Article article : articles) {
			String writer = memberService.getMemberById(article.getMemberId()).getName();

			String fileName = article.getId() + ".html";
			String html = "<meta charset=\"UTF-8\">";			
			html += "<div>번호 : " + article.getId() + "</div>";
			html += "<div>날짜 : " + article.getRegDate() + "</div>";
			html += "<div>작성자 : " + writer + "</div>";
			html += "<div>제목 : " + article.getTitle() + "</div>";
			html += "<div>내용 : " + article.getBody() + "</div>";
			if(article.getId()>1) {
				html+="<div><a href=\"" + (article.getId()-1) + ".html\">이전글</a></div>";
			}
			
			html += "<div><a href=\"" + (article.getId()+1) + ".html\">다음글</a></div>";
			Util.writeFileContents("exportHtml/" + fileName, html);
		}
		
	}

}
