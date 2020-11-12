package com.sbs.example.easytextboard.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;

public class MemberDao {

	private String url = "jdbc:mysql://localhost/a1";

	private ArrayList<Member> members;
	int memberNum;

	public MemberDao() {
		members = new ArrayList<>();
		memberNum = 1;

	}

	public Member getMemberByNum(int number) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Member member = new Member();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

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

	// join
	public int join(Scanner sc) {
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		ResultSet rs = null;
		int number = 0;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			System.out.printf("아이디 : ");
			String id = sc.nextLine().trim();
			if (id.length() == 0) {
				System.out.println("아이디를 입력해 주세요.");
				return number;
			}

			String sql = "select * from `member` where loginId ='" + id + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				return -1;
			}

			System.out.printf("비밀번호 : ");
			String pw = sc.nextLine();
			if (pw.length() == 0) {
				System.out.println("비밀번호를 입력해 주세요.");
				return number;
			}

			System.out.printf("이름 : ");
			String name = sc.nextLine();
			if (pw.length() == 0) {
				System.out.println("이름을 입력해 주세요.");

			}

			sql = "insert `member` set loginId ='" + id + "', pw ='" + pw + "', `name` ='" + name + "'";

			stmt2 = conn.createStatement();
			stmt2.executeUpdate(sql);

			sql = "select * from `member` order by id desc limit 1";

			stmt3 = conn.createStatement();
			rs = stmt3.executeQuery(sql);

			while (rs.next()) {
				number = rs.getInt(1);
				return number;
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
				if (stmt2 != null) {
					stmt2.close();
				}
				if (stmt3 != null) {
					stmt3.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}
		return number;
	}

	// modify
	public void modify(int number, String newName) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			String sql = "update `member` set name = '" + newName + "' where id =" + number;

			stmt = conn.createStatement();

			stmt.executeUpdate(sql);

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

	// login
	public Member login(Scanner sc) {
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		Member member = new Member();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");
			System.out.printf("아이디 : ");
			String id = sc.nextLine().trim();
			if (id.length() == 0) {
				System.out.println("아이디를 입력해 주세요.");
				return null;
			}

			String sql = "select * from `member` where loginId ='" + id + "'";

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			if (!rs.next()) {
				System.out.println("존재하지 않는 아이디입니다.");
				return null;
			}

			System.out.printf("비밀번호 : ");
			String pw = sc.nextLine().trim();
			if (pw.length() == 0) {
				System.out.println("비밀번호를 입력해 주세요.");
				return null;
			}
			sql = "select * from `member` where loginId ='" + id + "'and pw = '" + pw + "'";

			stmt2 = conn.createStatement();

			rs = stmt2.executeQuery(sql);

			if (!rs.next()) {
				System.out.println("비밀번호가 맞지 않습니다.");
				return null;
			}
			int number = rs.getInt(1);
			String name = rs.getString(4);
			member.setNumber(number);
			member.setId(id);
			member.setPw(pw);
			member.setName(name);
			return member;
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
				if (stmt2 != null) {
					stmt2.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {

			}
		}

	}

	public Member whoami() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Member member = new Member();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

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
