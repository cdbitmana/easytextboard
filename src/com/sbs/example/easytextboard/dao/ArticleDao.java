package com.sbs.example.easytextboard.dao;

import java.sql.*;
import java.util.ArrayList;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;

public class ArticleDao {

	private ArrayList<Article> articles;
	private ArrayList<Board> boards;

	private String url = "jdbc:mysql://localhost/a1";

	public ArticleDao() {

		boards = new ArrayList<>();
		articles = new ArrayList<>();

	}

	// getArticleByNum
	public Article getArticleByNum(int number) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from article where id = " + number + " and boardId ="
					+ Container.session.getSelectBoardId();

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				Article article = new Article();
				int id = rs.getInt(1);
				String regDate = rs.getString(2);
				String title = rs.getString(3);
				String body = rs.getString(4);
				int writerId = rs.getInt(5);
				int boardId = rs.getInt(6);

				article.setNumber(id);
				article.setRegDate(regDate);
				article.setTitle(title);
				article.setBody(body);
				article.setWriteMemberNum(writerId);
				article.setBoardId(boardId);

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

	// remove
	public void remove(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "delete from article where id =" + id;

			stmt = conn.createStatement();

			stmt.executeUpdate(sql);

			System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

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

	}

	// add
	public int add(String title, String body, int writerNumber, int boardId) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int boardLastArticleId = 0;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "insert into article set regDate = now(),title ='" + title + "', `body` ='" + body
					+ "', writerId =" + writerNumber + ", boardId =" + boardId;

			stmt = conn.createStatement();

			stmt.executeUpdate(sql);

			sql = "select * from `board` where id =" + boardId;

			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				boardLastArticleId = rs.getInt(3);
			}

			sql = "update `board` set lastArticleId = " + (boardLastArticleId + 1) + " where id =" + boardId;

			stmt.executeUpdate(sql);

			sql = "select * from `board` where id =" + boardId;

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				boardLastArticleId = rs.getInt(3);

			}

			return boardLastArticleId;

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");

		} catch (SQLException e) {
			System.out.println("에러: " + e);

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
		return boardLastArticleId;

	}

	// printList
	public void printList() {
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		try {

			int writerId = 0;
			String nickname = "";
			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from article where boardId = " + Container.session.getSelectBoardId()
					+ " order by id desc";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			String sql2 = "select * from article where boardId = " + Container.session.getSelectBoardId()
					+ " order by id desc";
			stmt2 = conn.createStatement();
			rs = stmt2.executeQuery(sql2);

			while (rs.next()) {

				int hit = 0;
				int id = rs.getInt(1);
				String regDate = rs.getString(2);
				String title = rs.getString(3);

				writerId = rs.getInt(5);

				sql = "select * from `member` where id =" + writerId;

				stmt3 = conn.createStatement();

				rs2 = stmt3.executeQuery(sql);
				if (rs2.next()) {
					nickname = rs.getString(4);
				}

				System.out.printf("%d / %s / %s / %s / %d\n", id, regDate, title, nickname, hit);

			}

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러: " + e);

		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
				if (stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (rs2 != null && !rs2.isClosed()) {
					rs2.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// modify
	public void modify(String title, String body, int articleNum) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from article where id =" + articleNum;

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (!rs.next()) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}

			sql = "update article set title = '" + title + "', `body` = '" + body + "' where id = " + articleNum;

			stmt.executeUpdate(sql);

			System.out.printf("%d번 게시물이 수정되었습니다.\n", articleNum);

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

	}

	// searchArticles
	public ArrayList<Article> searchArticles(String searchKeyword) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Article> articles = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from article where title like '%" + searchKeyword + "%'" + " and boardId ="
					+ Container.session.getSelectBoardId();

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Article article = new Article();
				int id = rs.getInt(1);
				String regDate = rs.getString(2);
				String title = rs.getString(3);
				String body = rs.getString(4);
				int writerId = rs.getInt(5);
				int boardId = rs.getInt(6);

				article.setNumber(id);
				article.setRegDate(regDate);
				article.setTitle(title);
				article.setBody(body);
				article.setWriteMemberNum(writerId);
				article.setBoardId(boardId);

				articles.add(article);
			}

			return articles;

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

	// makeBoard
	public int makeBoard(String name) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "insert into `board` set `name` = '" + name + "', lastArticleId =" + 0;

			stmt = conn.createStatement();

			stmt.executeUpdate(sql);

			sql = "select * from `board` where `name` = '" + name + "'";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt(1);

				return id;
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

		return 0;
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

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from `board` where `name` = '" + name + "'";

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt(1);
				String BoardName = rs.getString(2);
				int lastArticleId = rs.getInt(3);

				board.setBoardId(id);
				board.setBoardName(BoardName);
				board.setLastArticleId(lastArticleId);

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

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

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

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from `board` where id =" + id;

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int boardId = rs.getInt(1);
				String name = rs.getString(2);
				int lastArticleId = rs.getInt(3);

				board.setBoardId(boardId);
				board.setBoardName(name);
				board.setLastArticleId(lastArticleId);

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
