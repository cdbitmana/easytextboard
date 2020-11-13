package com.sbs.example.easytextboard.dao;

import java.sql.*;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;

public class MemberDao {

	private String url = "jdbc:mysql://localhost/a1?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";

	private String userId = "sbsst";
	private String userPw = "sbs123414";

	public MemberDao() {

	}

	// getMemberById
	public Member getMemberById(int id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from `member` where id = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				member = new Member();
				int memberId = rs.getInt(1);
				String loginId = rs.getString(2);
				String pw = rs.getString(3);
				String name = rs.getString(4);

				member.setId(memberId);
				member.setId(loginId);
				member.setPw(pw);
				member.setName(name);

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

		return member;
	}

	// doModify
	public void doModify(int id, String newName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "update `member` set `name` =  ? where id = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, newName);
			pstmt.setInt(2, id);

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
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}
	}

	// getLoginedMember
	public Member getLoginedMember() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from `member` where id = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, Container.session.getLoginedId());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				member = new Member();
				int id = rs.getInt(1);
				String loginId = rs.getString(2);
				String pw = rs.getString(3);
				String name = rs.getString(4);

				member.setId(id);
				member.setId(loginId);
				member.setPw(pw);
				member.setName(name);

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

		return member;
	}

	// getMemberByLoginId
	public Member getMemberByLoginId(String loginId) {
		Member member = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from `member` where loginId = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, loginId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				member = new Member();
				int id = rs.getInt(1);
				String loginId2 = rs.getString(2);
				String pw = rs.getString(3);
				String name = rs.getString(4);

				member.setId(id);
				member.setId(loginId2);
				member.setPw(pw);
				member.setName(name);

				return member;
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

		return member;
	}

	// doJoin
	public int doJoin(String loginId, String pw, String name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int id = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "insert into `member` set loginId = ? , pw = ? , `name` = ?";

			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, loginId);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);

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
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}

		return id;

	}

}
