package com.sbs.example.easytextboard.dao;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;
import com.sbs.example.easytextboard.mysqlutil.MysqlUtil;
import com.sbs.example.easytextboard.mysqlutil.SecSql;

public class ArticleDao {
	
	

	// doWrite
	public int doWrite(String title, String body, int memberId, int boardId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article SET");
		sql.append("regDate = NOW() , updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append(", memberId = ?", memberId);
		sql.append(", boardId = ?", boardId);
		sql.append(",hit = 0");
		return MysqlUtil.insert(sql);
	}

	// getArticlesForPrint
	public ArrayList<Article> getArticlesForPrint() {

		SecSql sql = new SecSql();

		sql.append("SELECT A.* , M.name AS extra__writer ");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE A.boardId = ?", Container.session.getCurrentBoardId());
		sql.append("ORDER BY A.id DESC");

		ArrayList<Article> articles = new ArrayList<>();

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}

		return articles;
	}

	// getBoardByCode
	public Board getBoardByCode(String currentBoardCode) {
		SecSql sql = new SecSql();
		Board board = null;

		sql.append("SELECT * FROM `board`");
		sql.append("WHERE code = ?", currentBoardCode);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (!boardMap.isEmpty()) {
			board = new Board(boardMap);
		}
		return board;
	}

	// getArticleRecommand
	public int getArticleRecommand(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) AS cnt FROM articleRecommand");
		sql.append("WHERE articleId = ?", id);

		return MysqlUtil.selectRowIntValue(sql);
	}

	// getArticleById
	public Article getArticleById(int articleId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM article");
		sql.append("WHERE id = ?", articleId);
		sql.append("AND boardId = ?", Container.session.getCurrentBoardId());

		Map<String, Object> articleMap = MysqlUtil.selectRow(sql);
		Article article = null;
		if (!articleMap.isEmpty()) {
			article = new Article(articleMap);
		}

		return article;
	}

	// doModify
	public void doModify(String title, String body, int articleId) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", articleId);
		sql.append("AND boardId = ?", Container.session.getCurrentBoardId());

		MysqlUtil.update(sql);

	}

	// getArticleForPrintById
	public Article getArticleForPrintById(int articleId) {
		SecSql sql = new SecSql();

		sql.append("SELECT A.* , M.name AS extra__writer ");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE A.boardId = ?", Container.session.getCurrentBoardId());
		sql.append("AND A.id = ?", articleId);
		sql.append("ORDER BY A.id DESC");

		Article article = null;

		Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

		if (!articleMap.isEmpty()) {
			article = new Article(articleMap);
		}

		return article;
	}

	// doDelete
	public void doDelete(int articleId) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", articleId);
		sql.append("AND boardId = ?", Container.session.getCurrentBoardId());

		MysqlUtil.delete(sql);

	}

	// getArticlesForPrintByKeyword
	public ArrayList<Article> getArticlesForPrintByKeyword(String keyword) {
		SecSql sql = new SecSql();

		sql.append("SELECT A.* , M.name AS extra__writer");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE A.title LIKE CONCAT('%',?,'%')", keyword);
		sql.append("AND A.boardId = ?", Container.session.getCurrentBoardId());
		sql.append("ORDER BY id DESC");

		ArrayList<Article> articles = new ArrayList<>();

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}

		return articles;
	}

	// doWriteReply
	public int doWriteReply(int articleId, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO articleReply");
		sql.append("SET regDate = NOW() , updateDate = NOW()");
		sql.append(", `body` = ?", body);
		sql.append(", articleId = ?", articleId);
		sql.append(", memberId = ?", Container.session.getLoginedId());

		return MysqlUtil.insert(sql);

	}

	// doDeleteReply
	public void doDeleteReply(int articleId, int replyId) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM articleReply");
		sql.append("WHERE articleId = ?", articleId);
		sql.append("AND id = ?", replyId);

		MysqlUtil.delete(sql);

	}

	// isRecommanded
	public boolean isRecommanded(int articleId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM articleRecommand");
		sql.append("WHERE articleId = ?", articleId);
		sql.append("AND memberId = ?", Container.session.getLoginedId());

		Map<String, Object> map = MysqlUtil.selectRow(sql);

		if (map.isEmpty()) {
			return false;
		}

		return true;
	}

	// doRecommand
	public void doRecommand(int articleId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO articleRecommand");
		sql.append("SET articleId = ?", articleId);
		sql.append(", memberId = ?", Container.session.getLoginedId());

		MysqlUtil.insert(sql);

	}

	// doCancelRecommand
	public void doCancelRecommand(int articleId) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM articleRecommand");
		sql.append("WHERE articleId = ?", articleId);
		sql.append("AND memberId = ?", Container.session.getLoginedId());

		MysqlUtil.delete(sql);

	}

	// doMakeBoard
	public int doMakeBoard(String name, String code) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO `board`");
		sql.append("SET regDate = NOW(), updateDate = NOW()");
		sql.append(", `name` = ?", name);
		sql.append(", `code` = ?", code);

		return MysqlUtil.insert(sql);

	}

	// getBoardsForPrint
	public ArrayList<Board> getBoardsForPrint() {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `board`");

		List<Map<String, Object>> boardMapList = MysqlUtil.selectRows(sql);

		ArrayList<Board> boards = new ArrayList<>();

		for (Map<String, Object> boardMap : boardMapList) {
			boards.add(new Board(boardMap));
		}
		return boards;
	}

	// getReplysForPrintByArticleId
	public ArrayList<ArticleReply> getReplysForPrintByArticleId(int articleId) {
		ArrayList<ArticleReply> replys = new ArrayList<>();

		SecSql sql = new SecSql();

		sql.append("SELECT R.* , M.name AS extra__name FROM articleReply AS R");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON R.memberId = M.id");
		sql.append("WHERE articleId = ?", articleId);

		List<Map<String, Object>> replyMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> replyMap : replyMapList) {
			replys.add(new ArticleReply(replyMap));
		}

		return replys;
	}

	// doIncreaseHit
	public void doIncreaseHit(int articleId) {
		SecSql sql = new SecSql();
		Article article = getArticleById(articleId);
		sql.append("UPDATE article SET");
		sql.append("hit = ?", article.getHit() + 1);
		sql.append("WHERE boardId = ?", Container.session.getCurrentBoardId());

		MysqlUtil.update(sql);

	}

	// getArticlesByBoardCode
	public List<Article> getArticlesByBoardCode(String boardCode) {
		List<Article> articles = new ArrayList<>();
		SecSql sql = new SecSql();

		Board board = null;
		board = getBoardByCode(boardCode);
		sql.append("SELECT A.* , M.name AS extra__writer FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE A.boardId = ?", board.getId());

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}

		return articles;
	}

	// getArticles
	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM article");

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}

		return articles;

	}

	

}
