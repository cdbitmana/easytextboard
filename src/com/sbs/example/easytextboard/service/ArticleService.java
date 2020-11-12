package com.sbs.example.easytextboard.service;

import java.util.ArrayList;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dao.*;
import com.sbs.example.easytextboard.dto.*;

public class ArticleService {

	private ArticleDao articleDao;
	
	private ArrayList<Board> boards;

	public ArticleService() {
		articleDao = Container.articleDao;
		boards = articleDao.getBoards();
		

	}

	public int makeBoard(String name) {
		return articleDao.makeBoard(name);

	}

	public ArrayList<Article> getArticles() {
		return articleDao.getArticles();
	}

	public Article getArticleByNum(int number) {
		return articleDao.getArticleByNum(number);
	}

	public Article getArticleByIndex(int index) {
		return articleDao.getArticleByIndex(index);
	}

	public void remove(int id) {
		articleDao.remove(id);
	}

	public ArrayList<Article> searchArticle(String title) {
		return articleDao.searchArticle(title);

	}

	public ArrayList<Board> getBoards() {
		return boards;
	}

	public Board getBoardById(int id) {
		return articleDao.getBoardById(id);
	}

	public boolean isExistBoard(int id) {
		return articleDao.isExistBoard(id);
	}

	public ArrayList<Article> getArticlesByBoardId() {
		ArrayList<Article> articles = new ArrayList<>();
		for (Article article : getArticles()) {
			if (article.getBoardId() == Container.session.getSelectBoardId()) {
				articles.add(article);
			}
		}
		return articles;

	}

	public int add(String title, String body, int loginedId, int selectBoardId) {
		return articleDao.add(title, body, loginedId, selectBoardId);
	}

	public void printList() {
		articleDao.printList();

	}

	public void modify(String title, String body, int articleNum) {
		articleDao.modify(title,body,articleNum);
		
	}

	public ArrayList<Article> searchArticles(String searchKeyword) {
		return articleDao.searchArticles(searchKeyword);
		
	}

	public Board getBoardByName(String name) {
		return articleDao.getBoardByName(name);
		
	}


}
