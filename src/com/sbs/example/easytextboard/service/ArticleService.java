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

	public int doWrite(String title, String body, int memberId, int boardId) {
		return articleDao.doWrite(title, body, memberId, boardId);
	}

	public ArrayList<Article> getArticlesForPrint() {
		return articleDao.getArticlesForPrint();
	}

	public Board getBoardByCode(String currentBoardCode) {
		return articleDao.getBoardByCode(currentBoardCode);
	}

	public int getArticleRecommand(int id) {
		return articleDao.getArticleRecommand(id);
	}

	public Article getArticleById(int articleId) {
		return articleDao.getArticleById(articleId);
	}

	public void doModify(String title, String body, int articleId) {
		articleDao.doModify(title, body, articleId);

	}

	public Article getArticleForPrintById(int articleId) {
		return articleDao.getArticleForPrintById(articleId);
	}

	public void doDelete(int articleId) {
		articleDao.doDelete(articleId);

	}

	public ArrayList<Article> getArticlesForPrintByKeyword(String keyword) {
		return articleDao.getArticlesForPrintByKeyword(keyword);
	}

	public int doWriteReply(int articleId, String body) {
		return articleDao.doWriteReply(articleId, body);

	}

	public void doDeleteReply(int articleId, int replyId) {
		articleDao.doDeleteReply(articleId, replyId);

	}

	public boolean isRecommanded(int articleId) {
		return articleDao.isRecommanded(articleId);
	}

	public void doRecommand(int articleId) {
		articleDao.doRecommand(articleId);

	}

	public void doCancelRecommand(int articleId) {
		articleDao.doCancelRecommand(articleId);

	}

	public int doMakeBoard(String name, String code) {
		return articleDao.doMakeBoard(name, code);

	}

	public ArrayList<Board> getBoardsForPrint() {
		return articleDao.getBoardsForPrint();
	}

	public ArrayList<ArticleReply> getReplysForPrintByArticleId(int articleId) {
		return articleDao.getReplysForPrintByArticleId(articleId);
	}

	public void doIncreaseHit(int articleId) {
		articleDao.doIncreaseHit(articleId);
	}

	

}
