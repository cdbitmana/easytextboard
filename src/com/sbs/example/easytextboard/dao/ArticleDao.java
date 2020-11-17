package com.sbs.example.easytextboard.dao;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;
import com.sbs.example.easytextboard.mysqlutil.MysqlUtil;
import com.sbs.example.easytextboard.mysqlutil.SecSql;

public class ArticleDao {

	public ArticleDao() {

	}

	// getArticleById
	public Article getArticleById(int id) {

		Article article = null;

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM article WHERE id = ? AND boardId = ?", id, Container.session.getSelectBoardId());

		Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

		if (!articleMap.isEmpty()) {
			article = new Article(articleMap);

		}

		return article;
	}

	// getArticles
	public ArrayList<Article> getArticles() {
		ArrayList<Article> articles = new ArrayList<>();
		Article article = null;

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM article WHERE boardId = ? ORDER BY id DESC", Container.session.getSelectBoardId());

		List<Map<String, Object>> articlesMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articlesMapList) {
			article = new Article(articleMap);

			articles.add(article);

		}

		return articles;

	}

	// getArticlesByKeyword
	public ArrayList<Article> getArticlesByKeyword(String searchKeyword) {

		ArrayList<Article> articles = new ArrayList<>();

		Article article = null;

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM article WHERE title LIKE CONCAT ('%' , ? , '%') AND boardId = ? ORDER BY id DESC",
				searchKeyword, Container.session.getSelectBoardId());

		List<Map<String, Object>> articlesMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articlesMapList) {
			article = new Article(articleMap);

			articles.add(article);

		}

		return articles;

	}

	// doMakeBoard
	public int doMakeBoard(String name) {
		int id = 0;

		SecSql sql = new SecSql();

		sql.append("INSERT INTO `board` SET `name` = ? , articleId = 0", name);

		id = MysqlUtil.insert(sql);

		return id;
	}

	// getBoardByName
	public Board getBoardByName(String name) {

		Board board = null;

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `board` WHERE `name` = ?", name);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.size() > 0) {
			board = new Board(boardMap);

		}

		return board;
	}

	// getBoardById
	public Board getBoardById(int id) {

		Board board = null;

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `board` WHERE id = ?", id);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.size() > 0) {
			board = new Board(boardMap);

		}

		return board;
	}

	// doAdd
	public int doAdd(String title, String body, int loginedId, int selectBoardId) {

		int id = 0;
		SecSql sql = new SecSql();
		Board board = getBoardById(Container.session.getSelectBoardId());
		sql.append(
				"INSERT INTO article SET id = ? ,regDate = NOW() , updateDate = NOW() , title = ? , `body` = ? , writerId = ? , hit = 0 , replyId = 0, boardId = ?",
				board.getArticleId() + 1, title, body, Container.session.getLoginedId(),
				Container.session.getSelectBoardId());

		MysqlUtil.insert(sql);

		SecSql sql2 = new SecSql();

		Article article = getArticleById(board.getArticleId() + 1);

		sql2.append("UPDATE `board` SET articleID = ?", article.getId());
		sql2.append("WHERE id = ?", selectBoardId);

		MysqlUtil.update(sql2);
		id = article.getId();
		return id;

	}

	// doDelete
	public void doDelete(int articleId) {

		SecSql sql = new SecSql();

		sql.append("DELETE FROM article WHERE id = ? AND boardID = ?", articleId, Container.session.getSelectBoardId());

		MysqlUtil.delete(sql);

		SecSql sql2 = new SecSql();

		sql2.append("DELETE FROM articleReply WHERE articleId = ? AND boardId = ?", articleId,
				Container.session.getSelectBoardId());

		MysqlUtil.delete(sql2);
	}

	// doModify
	public void doModify(int articleId, String title, String body) {

		SecSql sql = new SecSql();

		sql.append("UPDATE article SET updateDate = NOW() , title = ? , `body` = ? WHERE id = ? AND boardId = ?", title,
				body, articleId, Container.session.getSelectBoardId());

		MysqlUtil.update(sql);

	}

	// doWriteReply
	public int doWriteReply(String reply, int id) {

		SecSql sql = new SecSql();

		Article article = null;

		article = getArticleById(id);

		sql.append("INSERT INTO articleReply SET id = ?, regDate = NOW() , `body` = ?", article.getReplyId() + 1,
				reply);
		sql.append(", articleId = ?", id);
		sql.append(", boardId = ?", Container.session.getSelectBoardId());
		sql.append(", memberId = ?", Container.session.getLoginedId());

		MysqlUtil.insert(sql);

		SecSql sql2 = new SecSql();

		sql2.append("UPDATE article SET replyId = ? WHERE id = ? AND boardId = ?", article.getReplyId() + 1, id,
				Container.session.getSelectBoardId());

		MysqlUtil.update(sql2);

		article = getArticleById(id);

		int id2 = article.getReplyId();

		return id2;

	}

	// getReplysByArticleId
	public ArrayList<ArticleReply> getReplysByArticleId(int articleId) {
		ArrayList<ArticleReply> replys = new ArrayList<>();
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM articleReply");
		sql.append("WHERE articleId = ?", articleId);
		sql.append("AND boardId = ?", Container.session.getSelectBoardId());

		List<Map<String, Object>> articleReplyMapList = MysqlUtil.selectRows(sql);

		ArticleReply articleReply = null;

		for (Map<String, Object> articleReplyMap : articleReplyMapList) {
			articleReply = new ArticleReply(articleReplyMap);

			replys.add(articleReply);
		}

		return replys;

	}

}
