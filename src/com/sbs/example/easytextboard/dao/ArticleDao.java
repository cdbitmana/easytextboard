package com.sbs.example.easytextboard.dao;

import java.util.ArrayList;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;
import com.sbs.example.easytextboard.service.*;

public class ArticleDao {

	private ArrayList<Article> articles;
	Board board;
	ArrayList<Board> boards;

	public ArticleDao() {
		articles = new ArrayList<>();
		makeTestArticle();
	}

	public void makeTestArticle() {

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

	public Article getArticleByIndex(int index) {
		return articles.get(index);
	}

	public int remove(Article article) {

		int num = article.getNumber();
		articles.remove(article);
		return num;

	}



	public ArrayList<Article> searchArticle(String title) {
		ArrayList<Article> searchArticles = new ArrayList<>();
		for (Article article : articles) {
			if (article.getTitle().contains(title) && article.getBoardId() == Container.session.getSelectBoardId()) {
				searchArticles.add(article);
			}
		}
		return searchArticles;
	}

}
