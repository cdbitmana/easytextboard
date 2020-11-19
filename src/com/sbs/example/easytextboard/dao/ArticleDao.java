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

		sql.append("SELECT article.* , member.name AS extra__writer FROM article");
		sql.append("INNER JOIN `member`");
		sql.append("ON article.writerId = member.id");
		sql.append("WHERE article.id = ? ", id);
		sql.append("AND article.boardId = ? ", Container.session.getSelectBoardId());

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

		sql.append("SELECT article.* , member.name AS extra__writer FROM article ");

		sql.append("INNER JOIN `member` ");

		sql.append("ON article.writerId = member.id ");

		sql.append("WHERE article.boardId = ?", Container.session.getSelectBoardId());

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

		sql.append("SELECT article.* , member.name AS extra__writer FROM article ");
		sql.append("INNER JOIN `member`");
		sql.append("ON article.writerId = member.id");
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

		SecSql sql = new SecSql();

		sql.append("INSERT INTO article ");
		sql.append("SET regDate = NOW() ");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ? ", title);
		sql.append(", `body` = ?", body);
		sql.append(", writerId = ?", Container.session.getLoginedId());

		sql.append(", hit = 0 ");
		sql.append(", boardId = ?", Container.session.getSelectBoardId());

		int id = MysqlUtil.insert(sql);

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
		int replyId = 0;

		sql.append("INSERT INTO articleReply ");
		sql.append("SET regDate = NOW()");
		sql.append(", `body` = ?", reply);
		sql.append(", articleId = ?", id);
		sql.append(", boardId = ?", Container.session.getSelectBoardId());
		sql.append(", memberId = ?", Container.session.getLoginedId());

		replyId = MysqlUtil.insert(sql);

		return replyId;

	}

	// getReplysByArticleId
	public ArrayList<ArticleReply> getReplysByArticleId(int articleId) {
		ArrayList<ArticleReply> replys = new ArrayList<>();
		SecSql sql = new SecSql();

		sql.append("SELECT articleReply.* , member.name AS extra__name FROM articleReply");
		sql.append("INNER JOIN `member`");
		sql.append("ON articleReply.memberId = member.id");
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

	// getArticleReplyById
	public ArticleReply getArticleReplyById(int articleId, int replyId) {

		ArticleReply articleReply = null;
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM articleReply");
		sql.append("WHERE id = ?", replyId);
		sql.append("AND articleId = ?", articleId);
		sql.append("AND boardId = ?", Container.session.getSelectBoardId());

		Map<String, Object> articleReplyMap = MysqlUtil.selectRow(sql);

		if (!articleReplyMap.isEmpty()) {
			articleReply = new ArticleReply(articleReplyMap);
		}

		return articleReply;

	}

	// doModifyReply
	public void doModifyReply(int articleId, int replyId, String newBody) {

		SecSql sql = new SecSql();

		sql.append("UPDATE articleReply");
		sql.append("SET `body` = ?", newBody);
		sql.append("WHERE id = ?", replyId);
		sql.append("AND articleId = ?", articleId);
		sql.append("AND boardId = ?", Container.session.getSelectBoardId());

		MysqlUtil.update(sql);

	}

	// doDeleteReply
	public void doDeleteReply(int articleId, int replyId) {

		SecSql sql = new SecSql();

		sql.append("DELETE FROM articleReply");
		sql.append("WHERE id = ?", replyId);
		sql.append("AND articleId = ?", articleId);
		sql.append("AND boardId = ?", Container.session.getSelectBoardId());

		MysqlUtil.delete(sql);

	}

	// doRecommand
	public void doRecommand(int articleId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO articleRecommand SET");
		sql.append("regDate = NOW() , updateDate = NOW()");

		sql.append(",articleID = ?", articleId);
		sql.append(", memberId = ?", Container.session.getLoginedId());

		MysqlUtil.insert(sql);

	}

	// getMemberIdRecommand
	public int getMemberIdRecommand(int articleId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM articleRecommand");
		sql.append("WHERE articleId = ?", articleId);

		List<Map<String, Object>> articleRecommandMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleRecommandMap : articleRecommandMapList) {
			if ((int) articleRecommandMap.get("memberId") == Container.session.getLoginedId()) {
				return (int) articleRecommandMap.get("memberId");
			}
		}
		return 0;
	}

	// doCancelRecommand
	public void doCancelRecommand(int articleId) {

		SecSql sql = new SecSql();

		sql.append("DELETE FROM articleRecommand");
		sql.append("WHERE articleId = ?", articleId);
		sql.append("AND memberId = ?", Container.session.getLoginedId());

		MysqlUtil.delete(sql);
	}

	// getRecommand
	public int getRecommand(int articleId) {
		SecSql sql = new SecSql();

		sql.append("SELECT memberId FROM articleRecommand");
		sql.append("WHERE articleId = ?", articleId);

		List<Map<String, Object>> articleRecommandMapList = MysqlUtil.selectRows(sql);
		int count = articleRecommandMapList.size();

		return count;

	}

	// doCountHit
	public void doCountHit(int articleId) {

		Article article = getArticleById(articleId);
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET hit = ?", article.getHit() + 1);
		sql.append("WHERE id = ?", articleId);
		sql.append("AND boardId = ?", Container.session.getSelectBoardId());

		MysqlUtil.update(sql);

	}

}
