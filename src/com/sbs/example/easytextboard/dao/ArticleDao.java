package com.sbs.example.easytextboard.dao;

import java.util.ArrayList;

import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.dto.Article;

public class ArticleDao {

	private ArrayList<Article> articles;
	private int lastArticleId = 0;

	public ArticleDao() {
		articles = new ArrayList<Article>();
		for (int i = 0; i < 32; i++) {
			add("title" + (lastArticleId + 1), "body" + (lastArticleId + 1));
		}
	}

	// getLastArticleId 메소드 : lastArticleId 값 리턴
	public int getLastArticleId() {
		return lastArticleId;
	}

	// getArticles 메소드 : articles 리스트 리턴
	public ArrayList<Article> getArticles() {
		return articles;
	}

	// add 메소드 : 게시물 추가
	public void add(String title, String body) {

		lastArticleId += 1;
		articles.add(new Article(lastArticleId, title, body, Container.session.loginName));
	}

	// printList 메소드 : 게시물 리스트를 10개씩 페이지로 출력
	public void printList(ArrayList<Article> list, int listNum) {
		int itemsInAPage = 10;
		int start = list.size() - 1;
		start -= itemsInAPage * (listNum - 1);
		int end = start - (itemsInAPage - 1);
		if (end < 0) {
			end = 0;
		}

		for (int i = start; i >= end; i--) {
			System.out.printf("%d / %s / %s\n", list.get(i).id, list.get(i).title, list.get(i).writerName);

		}
	}

	// getIndexById 메소드 : 게시물 번호에 맞는 게시물 인덱스 리턴
	public int getIndexById(int id) {
		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).id == id) {
				return i;
			}
		}
		return -1;
	}

	// getArticle 메소드 : 게시물 번호에 맞는 게시물 객체 리턴
	public Article getArticle(int id) {
		int index = getIndexById(id);
		if (index == -1) {
			return null;
		}
		return articles.get(index);
	}
}
