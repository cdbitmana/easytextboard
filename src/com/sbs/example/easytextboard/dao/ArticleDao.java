package com.sbs.example.easytextboard.dao;

import java.sql.*;
import java.util.ArrayList;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;

public class ArticleDao {

	private String url = "jdbc:mysql://localhost/a1?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
	private String userId = "sbsst";
	private String userPw = "sbs123414";

	public ArticleDao() {

	}

	// getArticleById
	public Article getArticleById(int id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Article article = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from article? where id = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, Container.session.getSelectBoardId());
			pstmt.setInt(2, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				article = new Article();
				int articleId = rs.getInt(1);
				String regDate = rs.getString(2);
				String updateDate = rs.getString(3);
				String title = rs.getString(4);
				String body = rs.getString(5);
				int writerId = rs.getInt(6);
				int hit = rs.getInt(7);

				article.setId(articleId);
				article.setRegDate(regDate);
				article.setUpdateDate(updateDate);
				article.setTitle(title);
				article.setBody(body);
				article.setWriteMemberId(writerId);
				article.setHit(hit);
			}

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}
		return article;
	}

	// getArticles
	public ArrayList<Article> getArticles() {
		ArrayList<Article> articles = new ArrayList<>();
		Article article;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from article? order by id desc";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, Container.session.getSelectBoardId());

			rs = pstmt.executeQuery();

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
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}

		return articles;

	}

	// getArticlesByKeyword
	public ArrayList<Article> getArticlesByKeyword(String searchKeyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Article> articles = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from article? where title like concat('%',?,'%') order by id desc";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, Container.session.getSelectBoardId());
			pstmt.setString(2, searchKeyword);

			rs = pstmt.executeQuery();

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

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}

		return articles;

	}

	// doMakeBoard
	public int doMakeBoard(String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int id = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(url, userId, userPw);

			String sql = "insert into `board` set `name` = ?";

			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, name);

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();

			rs.next();

			id = rs.getInt(1);

			sql = "create table article? ( id int(10) unsigned not null primary key auto_increment , regDate datetime not null, updateDate datetime not null , title varchar(200) not null , `body` text not null ,writerId int (10) unsigned not null , hit int (100) unsigned not null)";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, id);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}

		return id;
	}

	// getBoardByName
	public Board getBoardByName(String name) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board board = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from `board` where `name` = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, name);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				board = new Board();
				int id = rs.getInt(1);
				String BoardName = rs.getString(2);

				board.setBoardId(id);
				board.setBoardName(BoardName);

			}

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}

		return board;
	}

	// getBoardById
	public Board getBoardById(int id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board board = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from `board` where id = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				board = new Board();
				board.setBoardId(rs.getInt(1));
				board.setBoardName(rs.getString(2));
			}

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}

		return board;
	}

	// doAdd
	public int doAdd(String title, String body, int loginedId, int selectBoardId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int id = 0;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(url, userId, userPw);

			String sql = "insert into article? set regDate = now() , updateDate = now(), title = ? , `body` = ? , writerId = ? , hit = 0";

			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, Container.session.getSelectBoardId());
			pstmt.setString(2, title);
			pstmt.setString(3, body);
			pstmt.setInt(4, Container.session.getLoginedId());

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}

		return id;

	}

	// doDelete
	public void doDelete(int articleId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(url, userId, userPw);

			String sql = "delete from article? where id = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, Container.session.getSelectBoardId());
			pstmt.setInt(2, articleId);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		}

	}

	// doModify
	public void doModify(int articleId, String title, String body) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(url, userId, userPw);

			String sql = "update article? set updateDate = now() , title = ? , `body` = ? where id = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, Container.session.getSelectBoardId());
			pstmt.setString(2, title);
			pstmt.setString(3, body);
			pstmt.setInt(4, articleId);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		}
	}

}
