package com.sbs.example.easytextboard.dao;


import java.util.Map;


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
			member = new Member(memberMap);

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
			member = new Member(memberMap);

		}

		return member;
	}

	// getMemberByLoginId
	public Member getMemberByLoginId(String loginId) {

		Member member = null;
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `member` WHERE loginId = ?", loginId);

		Map<String, Object> memberMap = MysqlUtil.selectRow(sql);

		if (memberMap.size() > 0) {
			member = new Member(memberMap);

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
