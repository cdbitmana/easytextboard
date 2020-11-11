package com.sbs.example.easytextboard.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;

public class MemberDao {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private ArrayList<Member> members;
	int memberNum;

	public MemberDao() {
		members = new ArrayList<>();
		memberNum = 1;

	}

	public boolean isExistId(String id) {
		for (Member member : members) {
			if (member.getId().equals(id)) {

				return true;
			}
		}
		return false;
	}

	public Member getMemberByNum(int number) {
		for (Member member : members) {
			if (member.getNumber() == number) {
				return member;
			}
		}
		return null;
	}

	public Member getMemberById(String id) {
		for (Member member : members) {
			if (member.getId().equals(id)) {
				return member;
			}
		}
		return null;
	}

	public Member getLoginedMember(int loginedId) {
		for (Member member : members) {
			if (member.getNumber() == loginedId) {
				return member;
			}
		}
		return null;

	}

	public int join(Scanner sc) {
		int number = 0;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

			System.out.printf("아이디 : ");
			String id = sc.nextLine().trim();
			if (id.length() == 0) {
				System.out.println("아이디를 입력해 주세요.");
				return number;
			}

			String sql = "select * from `member` where loginId ='" + id + "'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println("이미 존재하는 아이디입니다.");
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

			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

			sql = "select * from `member` order by id desc limit 1";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				number = rs.getInt(1);
				return number;
			}
		} catch (ClassNotFoundException e) {
			return number;
		} catch (SQLException e) {
			return number;
		}
		return number;
	}

	public void modify(int number, String newName) {
		Member member;
		member = getLoginedMember(number);
		member.setName(newName);

	}

	public void whoami() {
		for (Member member : members) {
			if (member.getNumber() == Container.session.getLoginedId()) {
				System.out.println("== 회원 정보 ==");
				System.out.println("번호 / 아이디 / 이름");
				System.out.printf("%d / %s / %s\n", member.getNumber(), member.getId(), member.getName());
			}
		}

	}

	public Member login(Scanner sc) {
		Member member = new Member();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/a1";

			conn = DriverManager.getConnection(url, "sbsst", "sbs123414");
			System.out.printf("아이디 : ");
			String id = sc.nextLine().trim();
			if (id.length() == 0) {
				System.out.println("아이디를 입력해 주세요.");
				return null;
			}

			String sql = "select * from `member` where loginId ='" + id + "'";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

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

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

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
		}

	}

}
