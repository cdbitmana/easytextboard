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

	// getArticleRecommend
	public int getArticleRecommend(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) AS cnt FROM articleRecommend");
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
	public int doModify(Map<String, Object> args) {
		SecSql sql = new SecSql();

		int id = (int) args.get("id");
		String title = args.get("title") != null ? (String) args.get("title") : null;
		String body = args.get("body") != null ? (String) args.get("body") : null;
		int likesCount = args.get("likesCount") != null ? (int) args.get("likesCount") : -1;
		int commentsCount = args.get("commentsCount") != null ? (int) args.get("commentsCount") : -1;

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		if (title != null) {
			sql.append(", title = ?", title);
		}

		if (body != null) {
			sql.append(", body = ?", body);
		}

		if (likesCount != -1) {
			sql.append(", likesCount = ?", likesCount);
		}

		if (commentsCount != -1) {
			sql.append(", commentsCount = ?", commentsCount);
		}
		sql.append("WHERE id = ?", id);

		return MysqlUtil.update(sql);

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

	// isRecommended
	public boolean isRecommended(int articleId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM articleRecommend");
		sql.append("WHERE articleId = ?", articleId);
		sql.append("AND memberId = ?", Container.session.getLoginedId());

		Map<String, Object> map = MysqlUtil.selectRow(sql);

		if (map.isEmpty()) {
			return false;
		}

		return true;
	}

	// doRecommend
	public void doRecommend(int articleId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO articleRecommend");
		sql.append("SET articleId = ?", articleId);
		sql.append(", memberId = ?", Container.session.getLoginedId());

		MysqlUtil.insert(sql);

	}

	// doCancelRecommend
	public void doCancelRecommend(int articleId) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM articleRecommend");
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
		sql.append("hit = ?", article.getHitCount() + 1);
		sql.append("WHERE boardId = ?", Container.session.getCurrentBoardId());

		MysqlUtil.update(sql);

	}

	// getArticlesByBoardCode
	public List<Article> getArticlesByBoardCode(String boardCode) {
		List<Article> articles = new ArrayList<>();
		SecSql sql = new SecSql();

		Board board = null;
		board = getBoardByCode(boardCode);
		sql.append("SELECT A.* , M.name AS extra__writer , B.code AS extra__boardCode FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("INNER JOIN `board` AS B");
		sql.append("ON A.boardId = B.id");
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

		sql.append(
				"SELECT A.* , M.name AS extra__writer , B.name AS extra__boardName , B.code AS extra__boardCode FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("INNER JOIN `board` AS B");
		sql.append("ON A.boardId = B.id");

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}

		return articles;

	}

	// getArticlesByBoardId
	public List<Article> getArticlesByBoardId() {
		List<Article> articles = new ArrayList<>();
		SecSql sql = new SecSql();

		sql.append(
				"SELECT A.* , B.name AS extra__boardName , B.code AS extra__boardCode, M.name AS extra__writer FROM article AS A");
		sql.append("INNER JOIN `board` AS B");
		sql.append("ON A.boardId = B.id");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}
		return articles;
	}

	// updatePageHits
	public int updatePageHits() {

		SecSql sql = new SecSql();
		sql.append("UPDATE article AS AR");
		sql.append("INNER JOIN (");
		sql.append(
				"    SELECT CAST(REPLACE(REPLACE(SUBSTR(GA4_PP.pagePathWoQueryStr, INSTR(GA4_PP.pagePathWoQueryStr , '-detail-')), '-detail-' , ''), '.html', '') AS UNSIGNED) AS articleId, hit");
		sql.append("    FROM (");
		sql.append("        SELECT");
		sql.append("        IF(");
		sql.append("            INSTR(GA4_PP.pagePath, '?') = 0,");
		sql.append("            GA4_PP.pagePath,");
		sql.append("            SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)");
		sql.append("        ) AS pagePathWoQueryStr,");
		sql.append("        SUM(GA4_PP.hit) AS hit");
		sql.append("        FROM ga4DataPagePath AS GA4_PP");
		sql.append("        WHERE GA4_PP.pagePath LIKE '%-detail-%.html%'");
		sql.append("        GROUP BY pagePathWoQueryStr");
		sql.append("    ) AS GA4_PP");
		sql.append(") AS GA4_PP");
		sql.append("ON AR.id = GA4_PP.articleId");
		sql.append("SET AR.hitCount = GA4_PP.hit");

		return MysqlUtil.update(sql);
	}

	// getArticlesForStaticPage
	public List<Article> getArticlesForStaticPage(int boardId) {
		List<Article> articles = new ArrayList<>();
		SecSql sql = new SecSql();

		sql.append(
				"SELECT A.* , M.name AS extra__writer , B.name AS extra__boardName , B.code AS extra__boardCode FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("INNER JOIN `board` AS B");
		sql.append("ON A.boardId = B.id");
		sql.append("WHERE A.boardId = ?", boardId);
		sql.append("ORDER BY A.hitCount DESC");
		sql.append("LIMIT 5");

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}

		return articles;
	}

	public List<Article> getArticleByBoardCodeDesc(String code) {
		List<Article> articles = new ArrayList<>();
		SecSql sql = new SecSql();

		Board board = null;
		board = getBoardByCode(code);
		sql.append("SELECT A.* , M.name AS extra__writer , B.code AS extra__boardCode FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("INNER JOIN `board` AS B");
		sql.append("ON A.boardId = B.id");
		sql.append("WHERE A.boardId = ?", board.getId());
		sql.append("ORDER BY A.id DESC");

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}

		return articles;
	}

	public List<Tag> getTagById(int id) {
		List<Tag> tags = null;
		SecSql sql = new SecSql();
		
		sql.append("SELECT * FROM `tag`");
		sql.append("WHERE relTypeCode = 'article'");
		sql.append("AND relId = ?", id);
		
		List<Map<String,Object>> tagMapList = MysqlUtil.selectRows(sql);
		
		if(!tagMapList.isEmpty()) {
			tags = new ArrayList<Tag>();
			for(Map<String,Object> tagMap : tagMapList) {
				tags.add(new Tag(tagMap));
			}
		}
		
		return tags;
	}

	public List<Tag> getTagBodys() {
		List<Tag> tags = null;
		SecSql sql = new SecSql();
		
		sql.append("SELECT * FROM `tag`");
		sql.append("GROUP BY `body`");
		
		List<Map<String,Object>> tagsMapList = MysqlUtil.selectRows(sql);
		
		if(!tagsMapList.isEmpty()) {
			tags = new ArrayList<Tag>();
			for(Map<String,Object> tagsMap : tagsMapList) {
				tags.add(new Tag(tagsMap));
			}
		}
		
		return tags;
	}

	public List<Article> getArticlesByTagBody(String tagBody) {
		List<Article> articles = new ArrayList<>();
		List<Tag> tags = null;
		SecSql sql = new SecSql();
		
		sql.append("SELECT * FROM `tag`");
		sql.append("WHERE  `body`= ?", tagBody);
		
		List<Map<String,Object>> tagsMapList = MysqlUtil.selectRows(sql);
		
		if(!tagsMapList.isEmpty()) {
			tags = new ArrayList<>();
			for(Map<String,Object> tagsMap : tagsMapList) {
				tags.add(new Tag(tagsMap));
			}
		}
		
		for(Tag tag : tags) {
			Article article = getArticleByTag(tag.getRelId());
			articles.add(article);
		}
		
		
		
		return articles;
	}

	private Article getArticleByTag(int relId) {
		Article article = null;
		
		SecSql sql = new SecSql();
		
		sql.append("SELECT * FROM article");
		sql.append("WHERE id = ? ", relId);
		
		Map<String,Object> articleMap = MysqlUtil.selectRow(sql);
		
		if(!articleMap.isEmpty()) {
			article = new Article(articleMap);
		}
		
		return article;
	}

	

}
