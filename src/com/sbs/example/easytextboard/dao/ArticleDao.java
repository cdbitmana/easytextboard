package com.sbs.example.easytextboard.dao;

import java.sql.*;
import java.util.ArrayList;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;

public class ArticleDao {

	private ArrayList<Board> boards;

	private String url = "jdbc:mysql://localhost/a1?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
	private String userId = "sbsst";
	private String userPw = "sbs123414";

	public ArticleDao() {

		boards = new ArrayList<>();

	}

	// getArticleByNum
	public Article getArticleByNum(int number) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from article" + Container.session.getSelectBoardId() + " where id = " + number;

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				Article article = new Article();
				int id = rs.getInt(1);
				String regDate = rs.getString(2);
				String updateDate = rs.getString(3);
				String title = rs.getString(4);
				String body = rs.getString(5);
				int writerId = rs.getInt(6);

				int hit = rs.getInt(7);

				article.setId(id);
				article.setRegDate(regDate);
				article.setUpdateDate(updateDate);
				article.setTitle(title);
				article.setBody(body);
				article.setWriteMemberNum(writerId);
				article.setArticleHit(hit);

				return article;

			}

		} catch (ClassNotFoundException e) {
			return null;
		} catch (SQLException e) {
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}
		return null;
	}

	// removeArticle
	public void removeArticle(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "delete from article" + Container.session.getSelectBoardId() + " where id =" + id;

			stmt = conn.createStatement();

			stmt.executeUpdate(sql);

			System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}

	}

	// addArticle
	public int addArticle(String title, String body, int writerNumber, int boardId) {

		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		int id = 0;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "insert into article" + boardId + " set regDate = now() , updateDate = now() , title = '"
					+ title + "', `body` ='" + body + "', writerId =" + writerNumber + ", hit = 0";

			stmt = conn.createStatement();

			stmt.executeUpdate(sql);

			sql = "select * from article" + boardId + " order by id desc limit 1";

			stmt2 = conn.createStatement();

			rs = stmt2.executeQuery(sql);

			rs.next();

			id = rs.getInt(1);

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}

		return id;

	}

	// getArticles
	public ArrayList<Article> getArticles() {
		ArrayList<Article> articles = new ArrayList<>();
		Article article;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from article" + Container.session.getSelectBoardId() + " order by id desc";

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt(1);
				String regDate = rs.getString(2);
				String updateDate = rs.getString(3);
				String title = rs.getString(4);
				String body = rs.getString(5);
				int writerId = rs.getInt(6);
				int hit = rs.getInt(7);

				article = new Article(id, regDate, updateDate, title, body, writerId, hit);

				articles.add(article);

			}

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}

		return articles;

	}

	// modifyArticle
	public void modifyArticle(String title, String body, int articleNum) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "update article" + Container.session.getSelectBoardId() + " set updateDate = now() , title = '"
					+ title + "', `body` = '" + body + "' where id = " + articleNum;

			stmt = conn.createStatement();

			stmt.executeUpdate(sql);

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}

	}

	// getArticlesByKeyword
	public ArrayList<Article> getArticlesByKeyword(String searchKeyword) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Article> articles = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from article" + Container.session.getSelectBoardId() + " where title like '%"
					+ searchKeyword + "%'";

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Article article;
				int id = rs.getInt(1);
				String regDate = rs.getString(2);
				String updateDate = rs.getString(3);
				String title = rs.getString(4);
				String body = rs.getString(5);
				int writerId = rs.getInt(6);
				int hit = rs.getInt(7);

				article = new Article(id, regDate, updateDate, title, body, writerId, hit);

				articles.add(article);
			}

			return articles;

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}

		return null;

	}

	// makeBoard
	public int makeBoard(String name) {
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		ResultSet rs = null;
		int id = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "insert into `board` set `name` = '" + name + "'";

			stmt = conn.createStatement();

			stmt.executeUpdate(sql);

			sql = "select * from `board` where `name` = '" + name + "'" + "order by id desc limit 1";

			stmt2 = conn.createStatement();

			rs = stmt2.executeQuery(sql);

			while (rs.next()) {
				id = rs.getInt(1);
			}

			sql = "create table article" + id + " (id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,"
					+ "regDate DATETIME NOT NULL," + "updateDate DATETIME NOT NULL," + "title VARCHAR(100) NOT NULL,"
					+ "`body` TEXT NOT NULL," + "writerId INT(10) UNSIGNED NOT NULL,"
					+ "hit INT(100) UNSIGNED NOT NULL)";

			stmt3 = conn.createStatement();

			stmt3.executeUpdate(sql);

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
				if (stmt2 != null && !stmt2.isClosed()) {
					stmt2.close();
				}
				if (stmt3 != null & !stmt3.isClosed()) {
					stmt3.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}

		return id;
	}

	// getBoardByName
	public Board getBoardByName(String name) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Board board = new Board();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from `board` where `name` = '" + name + "'";

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt(1);
				String BoardName = rs.getString(2);

				board.setBoardId(id);
				board.setBoardName(BoardName);

				return board;

			}

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}

		return null;
	}

	// isExistBoard
	public boolean isExistBoard(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from `board` where id =" + id;

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}
		return false;
	}

	// getBoardById
	public Board getBoardById(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Board board = new Board();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from `board` where id =" + id;

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int boardId = rs.getInt(1);
				String name = rs.getString(2);

				board.setBoardId(boardId);
				board.setBoardName(name);

				return board;
			}

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}

		return null;
	}

}
