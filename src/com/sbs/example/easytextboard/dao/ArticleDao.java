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

		sql.append("SELECT * FROM article? WHERE id = ?", Container.session.getSelectBoardId(), id);

		Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

		if (articleMap.size() > 0) {
			article = new Article();
			article.setId((int) articleMap.get("id"));
			article.setRegDate((String) articleMap.get("regDate"));
			article.setUpdateDate((String) articleMap.get("updateDate"));
			article.setTitle((String) articleMap.get("title"));
			article.setBody((String) articleMap.get("body"));
			article.setWriterId((int) articleMap.get("writerId"));
			article.setHit((int) articleMap.get("hit"));
		}

		return article;
	}

	// getArticles
	public ArrayList<Article> getArticles() {
		ArrayList<Article> articles = new ArrayList<>();
		Article article = null;

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM article? ORDER BY id DESC", Container.session.getSelectBoardId());

		List<Map<String, Object>> articlesMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articlesMapList) {
			article = new Article();
			article.setId((int) articleMap.get("id"));
			article.setRegDate((String) articleMap.get("regDate"));
			article.setUpdateDate((String) articleMap.get("updateDate"));
			article.setTitle((String) articleMap.get("title"));
			article.setBody((String) articleMap.get("body"));
			article.setWriterId((int) articleMap.get("writerId"));
			article.setHit((int) articleMap.get("hit"));

			articles.add(article);

		}

		return articles;

	}

	// getArticlesByKeyword
	public ArrayList<Article> getArticlesByKeyword(String searchKeyword) {

		ArrayList<Article> articles = new ArrayList<>();

		Article article = null;

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM article? where title like CONCAT ('%' , ? , '%') ORDER BY id DESC",
				Container.session.getSelectBoardId(), searchKeyword);

		List<Map<String, Object>> articlesMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articlesMapList) {
			article = new Article();
			article.setId((int) articleMap.get("id"));
			article.setRegDate((String) articleMap.get("regDate"));
			article.setUpdateDate((String) articleMap.get("updateDate"));
			article.setTitle((String) articleMap.get("title"));
			article.setBody((String) articleMap.get("body"));
			article.setWriterId((int) articleMap.get("writerId"));
			article.setHit((int) articleMap.get("hit"));

			articles.add(article);

		}

		return articles;

	}

	// doMakeBoard
	public int doMakeBoard(String name) {
		int id = 0;

		SecSql sql = new SecSql();

		sql.append("INSERT INTO `board` set `name` = ?", name);

		id = MysqlUtil.insert(sql);

		SecSql sql2 = new SecSql();

		sql2.append(
				"CREATE TABLE article? ( id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT , regDate DATETIME NOT NULL , updateDate DATETIME NOT NULL , title VARCHAR(200) NOT NULL , `body` TEXT NOT NULL , writerId INT(10) UNSIGNED NOT NULL , hit INT(100) UNSIGNED NOT NULL)",
				id);

		MysqlUtil.update(sql2);

		return id;
	}

	// getBoardByName
	public Board getBoardByName(String name) {

		Board board = null;

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `board` where `name` = ?", name);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.size() > 0) {
			board = new Board();
			board.setBoardId((int) boardMap.get("id"));
			board.setBoardName((String) boardMap.get("name"));
		}

		return board;
	}

	// getBoardById
	public Board getBoardById(int id) {

		Board board = null;

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `board` where id = ?", id);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.size() > 0) {
			board = new Board();
			board.setBoardId((int) boardMap.get("id"));
			board.setBoardName((String) boardMap.get("name"));
		}

		return board;
	}

	// doAdd
	public int doAdd(String title, String body, int loginedId, int selectBoardId) {

		int id = 0;
		SecSql sql = new SecSql();

		sql.append(
				"INSERT INTO article? SET regDate = NOW() , updateDate = NOW() , title = ? , `body` = ? , writerId = ? , hit = 0",
				Container.session.getSelectBoardId(), title, body, Container.session.getLoginedId());

		id = MysqlUtil.insert(sql);

		return id;

	}

	// doDelete
	public void doDelete(int articleId) {

		SecSql sql = new SecSql();

		sql.append("DELETE FROM article? where id = ?", Container.session.getSelectBoardId(), articleId);

		MysqlUtil.delete(sql);

	}

	// doModify
	public void doModify(int articleId, String title, String body) {

		SecSql sql = new SecSql();

		sql.append("UPDATE article? SET updateDate = NOW() , title = ? , `body` = ? WHERE id = ?",
				Container.session.getSelectBoardId(), title, body, articleId);

		MysqlUtil.update(sql);

	}

}
