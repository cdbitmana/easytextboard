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

	}

	public int makeBoard(String name) {
		return articleDao.makeBoard(name);

	}

	public Article getArticleByNum(int number) {
		return articleDao.getArticleByNum(number);
	}

	public void removeArticle(int id) {
		articleDao.removeArticle(id);
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

	public int addArticle(String title, String body, int loginedId, int selectBoardId) {
		return articleDao.addArticle(title, body, loginedId, selectBoardId);
	}

	public ArrayList<Article> getArticles() {
		return articleDao.getArticles();

	}

	public void modifyArticle(String title, String body, int articleNum) {
		articleDao.modifyArticle(title, body, articleNum);

	}

	public ArrayList<Article> getArticlesByKeyword(String searchKeyword) {
		return articleDao.getArticlesByKeyword(searchKeyword);

	}

	public Board getBoardByName(String name) {
		return articleDao.getBoardByName(name);

	}

	
}
