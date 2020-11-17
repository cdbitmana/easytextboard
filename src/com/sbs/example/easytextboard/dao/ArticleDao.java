package com.sbs.example.easytextboard.dao;

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

		sql.append("SELECT * FROM article");
		sql.append("WHERE id = ? ", id);
		sql.append("AND boardId = ? ", Container.session.getSelectBoardId());

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

		sql.append("SELECT * FROM article ");
		sql.append("WHERE boardId = ? ", Container.session.getSelectBoardId());
		sql.append("ORDER BY id DESC");

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

		sql.append("SELECT * FROM article ");
		sql.append("WHERE title LIKE CONCAT ('%' , ? , '%')", searchKeyword);
		sql.append("AND boardId = ? ORDER BY id DESC", Container.session.getSelectBoardId());

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

		sql.append("INSERT INTO `board` ");
		sql.append("SET `name` = ?", name);
		sql.append(", articleId = 0");

		id = MysqlUtil.insert(sql);

		return id;
	}

	// getBoardByName
	public Board getBoardByName(String name) {

		Board board = null;

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `board`");
		sql.append("WHERE `name` = ?", name);

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

		sql.append("SELECT * FROM `board` ");
		sql.append("WHERE id = ?", id);

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
		sql.append("INSERT INTO article ");
		sql.append("SET id = ?", board.getArticleId() + 1);
		sql.append(",regDate = NOW() ");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ? ", title);
		sql.append(", `body` = ?", body);
		sql.append(", writerId = ?", Container.session.getLoginedId());
		sql.append(", hit = 0 , replyId = 0");
		sql.append(", boardId = ?", Container.session.getSelectBoardId());

		MysqlUtil.insert(sql);

		SecSql sql2 = new SecSql();

		Article article = getArticleById(board.getArticleId() + 1);

		sql2.append("UPDATE `board` ");
		sql2.append("SET articleID = ?", article.getId());
		sql2.append("WHERE id = ?", selectBoardId);

		MysqlUtil.update(sql2);
		id = article.getId();
		return id;

	}

	// doDelete
	public void doDelete(int articleId) {

		SecSql sql = new SecSql();

		sql.append("DELETE FROM article ");
		sql.append("WHERE id = ? ", articleId);
		sql.append("AND boardID = ?", Container.session.getSelectBoardId());

		MysqlUtil.delete(sql);

		SecSql sql2 = new SecSql();

		sql2.append("DELETE FROM articleReply ");
		sql2.append("WHERE articleId = ?", articleId);
		sql2.append("AND boardId = ?", Container.session.getSelectBoardId());

		MysqlUtil.delete(sql2);
	}

	// doModify
	public void doModify(int articleId, String title, String body) {

		SecSql sql = new SecSql();

		sql.append("UPDATE article ");
		sql.append("SET updateDate = NOW() ");
		sql.append(", title = ? ", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ? ", articleId);
		sql.append("AND boardId = ?", Container.session.getSelectBoardId());

		MysqlUtil.update(sql);

	}

	// doWriteReply
	public int doWriteReply(String reply, int id) {

		SecSql sql = new SecSql();

		Article article = null;

		article = getArticleById(id);

		sql.append("INSERT INTO articleReply ");
		sql.append("SET id = ?", article.getReplyId() + 1);
		sql.append(", regDate = NOW()");
		sql.append(", `body` = ?", reply);
		sql.append(", articleId = ?", id);
		sql.append(", boardId = ?", Container.session.getSelectBoardId());
		sql.append(", memberId = ?", Container.session.getLoginedId());

		MysqlUtil.insert(sql);

		SecSql sql2 = new SecSql();

		sql2.append("UPDATE article ");
		sql2.append("SET replyId = ?", article.getReplyId() + 1);
		sql2.append("WHERE id = ?", id);
		sql2.append("AND boardId = ?", Container.session.getSelectBoardId());

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
