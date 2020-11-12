package com.sbs.example.easytextboard.dao;

import java.sql.*;
import java.util.ArrayList;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;

public class ArticleDao {

	private ArrayList<Article> articles;
	private ArrayList<Board> boards;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String url = "jdbc:mysql://localhost/a1";

	public ArticleDao() {

		boards = new ArrayList<>();
		articles = new ArrayList<>();

	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	// getArticleByNum
	public Article getArticleByNum(int number) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from article where id = " + number + " and boardId ="
					+ Container.session.getSelectBoardId();

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

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
		}
		return null;
	}

	// getArticleByIndex
	public Article getArticleByIndex(int index) {
		return articles.get(index);
	}

	// remove
	public void remove(int id) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from article where id =" + id;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			sql = "delete from article where id =" + id;

			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();

			System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		}

	}

	// searchArticle
	public ArrayList<Article> searchArticle(String title) {
		ArrayList<Article> searchArticles = new ArrayList<>();

		return searchArticles;
	}

	// getBoards
	public ArrayList<Board> getBoards() {
		return boards;
	}

	// add
	public int add(String title, String body, int writerNumber, int boardId) {
		int boardLastArticleId = 0;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "insert into article set regDate = now(),title ='" + title + "', `body` ='" + body
					+ "', writerId =" + writerNumber + ", boardId =" + boardId;

			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();

			sql = "select * from `board` where id =" + boardId;

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				boardLastArticleId = rs.getInt(3);
			}

			sql = "update `board` set lastArticleId = " + (boardLastArticleId + 1) + " where id =" + boardId;

			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();

			sql = "select * from `board` where id =" + boardId;

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

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
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		return boardLastArticleId;

	}

	// printList
	public void printList() {

		try {
			ResultSet rs2;
			PreparedStatement pstmt2;
			ResultSet rs3;
			int writerId = 0;
			String nickname = "";
			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from article where boardId = " + Container.session.getSelectBoardId()
					+ " order by id desc";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			String sql2 = "select * from article where boardId = " + Container.session.getSelectBoardId()
					+ " order by id desc";

			pstmt2 = conn.prepareStatement(sql2);
			rs2 = pstmt2.executeQuery();

			while (rs.next()) {

				int hit = 0;
				int id = rs.getInt(1);
				String regDate = rs.getString(2);
				String title = rs.getString(3);

				if (rs2.next()) {
					writerId = rs2.getInt(5);

				}
				sql = "select * from `member` where id =" + writerId;
				pstmt2 = conn.prepareStatement(sql);
				rs3 = pstmt2.executeQuery();
				if (rs3.next()) {
					nickname = rs3.getString(4);
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
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// modify
	public void modify(String title, String body, int articleNum) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from article where id =" + articleNum;

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (!rs.next()) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}

			sql = "update article set title = '" + title + "', `body` = '" + body + "' where id = " + articleNum;

			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

			System.out.printf("%d번 게시물이 수정되었습니다.\n", articleNum);

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		}

	}

	// searchArticles
	public ArrayList<Article> searchArticles(String searchKeyword) {

		ArrayList<Article> articles = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from article where title like '%" + searchKeyword + "%'";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

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

		}

		return null;

	}

	// makeBoard
	public int makeBoard(String name) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "insert into `board` set `name` = '" + name + "', lastArticleId =" + 0;

			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();

			sql = "select * from `board` where `name` = '" + name + "'";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt(1);

				return id;
			}
		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		}

		return 0;
	}

	// getBoardByName
	public Board getBoardByName(String name) {
		Board board = new Board();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from `board` where `name` = '" + name + "'";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

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

		}

		return null;
	}

	// isExistBoard
	public boolean isExistBoard(int id) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from `board` where id =" + id;

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		}
		return false;
	}

	// getBoardById
	public Board getBoardById(int id) {
		Board board = new Board();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from `board` where id =" + id;

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

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

		}

		return null;
	}

}
