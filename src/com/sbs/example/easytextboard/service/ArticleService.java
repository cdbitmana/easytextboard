package com.sbs.example.easytextboard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public int getArticleRecommend(int id) {
		return articleDao.getArticleRecommend(id);
	}

	public Article getArticleById(int articleId) {
		return articleDao.getArticleById(articleId);
	}

	public int doModify(String title, String body, int articleId) {
		Map<String, Object> modifyArgs = new HashMap<>();
		modifyArgs.put("id", articleId);
		modifyArgs.put("title", title);
		modifyArgs.put("body", body);

		return modify(modifyArgs);

	}

	public int modify(Map<String, Object> args) {
		return (int)articleDao.doModify(args);
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

	public boolean isRecommended(int articleId) {
		return articleDao.isRecommended(articleId);
	}

	public void doRecommend(int articleId) {
		articleDao.doRecommend(articleId);

	}

	public void doCancelRecommend(int articleId) {
		articleDao.doCancelRecommend(articleId);

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

	public List<Article> getArticlesByBoardCode(String boardCode) {
		return articleDao.getArticlesByBoardCode(boardCode);
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public List<Article> getArticlesByBoardId() {
		return articleDao.getArticlesByBoardId();
	}
	
	public void updatePageHits() {
		articleDao.updatePageHits();
	}

}
