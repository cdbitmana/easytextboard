package com.sbs.example.easytextboard.dao;

import java.util.ArrayList;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;

public class ArticleDao {

	private int articleNumber;
	private ArrayList<Article> articles;

	public ArticleDao() {
		articles = new ArrayList<>();
		articleNumber = 1;
		makeTestArticle();
	}

	public void makeTestArticle() {
		for (int i = 0; i < 5; i++) {
			add("title" + (i + 1), "body" + (i + 1), "aaa", 1);
			

		}
		for (int i = 5; i < 10; i++) {
			add("title" + (i + 1), "body" + (i + 1), "bbb", 2);

		}
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public Article getArticleByNum(int number) {
		for (Article article : articles) {
			if (article.getNumber() == number) {
				return article;
			}
		}
		return null;
	}

	public int add(String title, String body, String name, int writerNumber) {
		Article article = new Article();
		article.setNumber(articleNumber);
		article.setTitle(title);
		article.setBody(body);
		article.setWriter(name);
		article.setWriteMemberNum(writerNumber);
		articles.add(article);
		articleNumber++;
		return article.getNumber();
	}

	public Article getArticleByIndex(int index) {
		return articles.get(index);
	}

	public void modifyWriter(int writeMemberNum, String newName) {
		for (Article article : articles) {
			if (article.getWriteMemberNum() == writeMemberNum) {
				article.setWriter(newName);
			}
		}
	}

	public int remove(int articleNum) {
		for (Article article : articles) {
			if (article.getNumber() == articleNum) {
				int num = article.getNumber();
				articles.remove(article);
				return num;
			}
		}
		return 0;

	}

	public int modify(int articleNum, String title, String body) {
		for (Article article : articles) {
			if (article.getNumber() == articleNum) {
				article.setTitle(title);
				article.setBody(body);
				return article.getNumber();
			}
		}
		return 0;

	}

	public void printList(int listNum) {
		System.out.println("== 게시물 리스트 ==");

		System.out.println("번호 / 제목 / 작성자");
		int itemsInAPage = 10;
		int start = articles.size() - 1;
		start -= (listNum - 1) * itemsInAPage;
		int end = start - (itemsInAPage - 1);
		if (end < 0) {
			end = 0;
		}
		for (int i = start; i >= end; i--) {
			System.out.printf(" %d / %s / %s\n", articles.get(i).getNumber(), articles.get(i).getTitle(),
					articles.get(i).getWriter());
		}
	}

	public ArrayList<Article> searchArticle(String title) {
		ArrayList<Article> searchArticles = new ArrayList<>();
		for (Article article : articles) {
			if (article.getTitle().contains(title)) {
				searchArticles.add(article);
			}
		}
		return searchArticles;
	}

	public void searchList(int listNum, ArrayList<Article> articles) {
		System.out.println("== 게시물 리스트 ==");

		System.out.println("번호 / 제목 / 작성자");
		int itemsInAPage = 10;
		int start = articles.size() - 1;
		start -= (listNum - 1) * itemsInAPage;
		int end = start - (itemsInAPage - 1);
		if (end < 0) {
			end = 0;
		}
		for (int i = start; i >= end; i--) {
			System.out.printf(" %d / %s / %s\n", articles.get(i).getNumber(), articles.get(i).getTitle(),
					articles.get(i).getWriter());
		}
	}

}
