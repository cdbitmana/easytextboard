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

	public ArticleDao() {
		boards = new ArrayList<>();
		articles = new ArrayList<>();

	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public Article getArticleByNum(int number) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from article where id = " + number;

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

	public Article getArticleByIndex(int index) {
		return articles.get(index);
	}

	public void remove(int id) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/a1";

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

	public ArrayList<Article> searchArticle(String title) {
		ArrayList<Article> searchArticles = new ArrayList<>();
		for (Article article : articles) {
			if (article.getTitle().contains(title) && article.getBoardId() == Container.session.getSelectBoardId()) {
				searchArticles.add(article);
			}
		}
		return searchArticles;
	}

	public ArrayList<Board> getBoards() {
		return boards;
	}

	public int add(String title, String body, int writerNumber, int boardId) {
		int num = 0;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "insert into article set regDate = now(),title ='" + title + "', `body` ='" + body
					+ "', writerId =" + writerNumber + ", boardId =" + boardId;

			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();

			sql = "select * from article order by id desc limit 1";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				num = rs.getInt(1);

			}

			return num;

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
			return num;
		} catch (SQLException e) {
			System.out.println("에러: " + e);
			return num;
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}

	}

	public void printList() {

		try {
			ResultSet rs2;
			PreparedStatement pstmt2;
			ResultSet rs3;
			int writerId = 0;
			String nickname = "";
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "select * from article order by id desc";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			String sql2 = "select * from article order by id desc";

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

	public void modify(String title, String body, int articleNum) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/a1";

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

}
