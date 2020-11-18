package com.sbs.example.easytextboard.service;

import java.util.ArrayList;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dao.*;
import com.sbs.example.easytextboard.dto.*;

public class ArticleService {

	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;

	}

	public int doMakeBoard(String name) {
		return articleDao.doMakeBoard(name);

	}

	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public Board getBoardById(int id) {
		return articleDao.getBoardById(id);
	}

	public ArrayList<Article> getArticles() {
		return articleDao.getArticles();
	}

	public ArrayList<Article> getArticlesByKeyword(String searchKeyword) {
		return articleDao.getArticlesByKeyword(searchKeyword);
	}

	public Board getBoardByName(String name) {
		return articleDao.getBoardByName(name);
	}

	public int doAdd(String title, String body, int loginedId, int selectBoardId) {
		return articleDao.doAdd(title, body, loginedId, selectBoardId);
	}

	public void doDelete(int articleId) {
		articleDao.doDelete(articleId);

	}

	public void doModify(int articleId, String title, String body) {
		articleDao.doModify(articleId, title, body);

	}

	public int doWriteReply(String reply, int id) {
		return articleDao.doWriteReply(reply, id);

	}

	public ArrayList<ArticleReply> getReplysByArticleId(int articleId) {
		return articleDao.getReplysByArticleId(articleId);
	}

	public ArticleReply getArticleReplyById(int articleId, int replyId) {
		return articleDao.getArticleReplyById(articleId, replyId);
	}

	public void doModifyReply(int articleId, int replyId, String newBody) {
		articleDao.doModifyReply(articleId, replyId, newBody);

	}

	public void doDeleteReply(int articleId, int replyId) {
		articleDao.doDeleteReply(articleId, replyId);

	}

}
