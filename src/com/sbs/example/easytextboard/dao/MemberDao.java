package com.sbs.example.easytextboard.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;

public class MemberDao {

	private String url = "jdbc:mysql://localhost/a1?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";

	private ArrayList<Member> members;
	private String userId = "sbsst";
	private String userPw = "sbs123414";

	public MemberDao() {
		members = new ArrayList<>();

	}

	// getMemberByNum
	public Member getMemberById(int number) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Member member = new Member();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from `member` where id = " + number;

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt(1);
				String loginId = rs.getString(2);
				String pw = rs.getString(3);
				String name = rs.getString(4);

				member.setNumber(id);
				member.setId(loginId);
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

	// modify
	public void modify(int number, String newName) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "update `member` set name = '" + newName + "' where id =" + number;

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

	// getLoginedMember
	public Member getLoginedMember() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Member member = new Member();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from `member` where id=" + Container.session.getLoginedId();

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt(1);
				String loginId = rs.getString(2);
				String pw = rs.getString(3);
				String name = rs.getString(4);

				member.setNumber(id);
				member.setId(loginId);
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

	// getMemberByLoginId
	public Member getMemberByLoginId(String loginId) {
		Member member = new Member();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "select * from `member` where loginId = '" + loginId + "'";

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt(1);
				String loginId2 = rs.getString(2);
				String pw = rs.getString(3);
				String name = rs.getString(4);

				member.setNumber(id);
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

	// memberJoin
	public int memberJoin(String loginId, String pw, String name) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int id = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, userId, userPw);

			String sql = "insert into `member` set loginId = '" + loginId + "', pw = '" + pw + "', name = '" + name
					+ "'";

			stmt = conn.createStatement();

			stmt.executeUpdate(sql);

			sql = "select * from `member` order by id desc limit 1";

			rs = stmt.executeQuery(sql);

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

}
