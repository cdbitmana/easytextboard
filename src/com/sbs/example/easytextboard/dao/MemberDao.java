package com.sbs.example.easytextboard.dao;

import java.sql.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dto.*;
import com.sbs.example.easytextboard.mysqlutil.MysqlUtil;
import com.sbs.example.easytextboard.mysqlutil.SecSql;

public class MemberDao {

	public MemberDao() {

	}

	// getMemberById
	public Member getMemberById(int id) {

		Member member = null;

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `member` WHERE id = ?", id);

		Map<String, Object> memberMap = MysqlUtil.selectRow(sql);

		if (memberMap.size() > 0) {
			member = new Member();
			member.setId((int) memberMap.get("id"));
			member.setLoginId((String) memberMap.get("loginId"));
			member.setPw((String) memberMap.get("pw"));
			member.setName((String) memberMap.get("name"));
		}

		return member;
	}

	// doModify
	public void doModify(int id, String newName) {

		SecSql sql = new SecSql();

		sql.append("UPDATE `member` SET `name` = ? WHERE id = ?", newName, id);

		MysqlUtil.update(sql);

	}

	// getLoginedMember
	public Member getLoginedMember() {

		Member member = null;

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `member` WHERE id = ?", Container.session.getLoginedId());

		Map<String, Object> memberMap = MysqlUtil.selectRow(sql);

		if (memberMap.size() > 0) {
			member = new Member();
			member.setId((int) memberMap.get("id"));
			member.setLoginId((String) memberMap.get("loginId"));
			member.setPw((String) memberMap.get("pw"));
			member.setName((String) memberMap.get("name"));
		}

		return member;
	}

	// getMemberByLoginId
	public Member getMemberByLoginId(String loginId) {

		Member member = null;
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `member` WHERE loginId = ?", loginId);

		Map<String, Object> members = MysqlUtil.selectRow(sql);

		if (members.size() > 0) {
			member = new Member();
			member.setId((int) members.get("id"));
			member.setLoginId((String) members.get("loginId"));
			member.setPw((String) members.get("pw"));
			member.setName((String) members.get("name"));
		}

		return member;
	}

	// doJoin
	public int doJoin(String loginId, String pw, String name) {

		int id = 0;

		SecSql sql = new SecSql();

		sql.append("INSERT INTO `member` SET loginId = ? , pw = ? , `name` = ?", loginId, pw, name);

		id = MysqlUtil.insert(sql);

		return id;

	}

}
