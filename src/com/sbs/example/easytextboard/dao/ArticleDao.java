package com.sbs.example.easytextboard.dao;

import java.util.ArrayList;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;
import com.sbs.example.easytextboard.service.*;

public class ArticleDao {

	private ArrayList<Article> articles;
	private ArrayList<Board> boards;

	public ArticleDao() {
		boards = new ArrayList<>();
		articles = new ArrayList<>();

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

	public ArrayList<Board> getBoards() {
		return boards;
	}

	public int add(String title, String body, int writerNumber, int boardId, int lastArticleId) {
		Board board1 = null;

		for (Board board : boards) {
			if (board.getBoardId() == boardId) {
				board1 = board;
			}
		}

		Article article = new Article();
		article.setNumber(lastArticleId + 1);
		article.setTitle(title);
		article.setBody(body);
		article.setWriteMemberNum(writerNumber);
		article.setBoardId(boardId);
		board1.setLastArticleId(article.getNumber());
		articles.add(article);

		return article.getNumber();
	}

}
