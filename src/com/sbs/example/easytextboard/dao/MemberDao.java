package com.sbs.example.easytextboard.dao;

import java.util.ArrayList;
import java.util.List;
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

		sql.append("SELECT * FROM `member` ");
		sql.append("WHERE id = ?", id);

		Map<String, Object> memberMap = MysqlUtil.selectRow(sql);

		if (!memberMap.isEmpty()) {
			member = new Member(memberMap);

		}

		return member;
	}

	// doModify
	public void doModify(int id, String newName) {

		SecSql sql = new SecSql();

		sql.append("UPDATE `member` ");
		sql.append("SET `name` = ?", newName);
		sql.append("WHERE id = ?", id);

		MysqlUtil.update(sql);

	}

	// getLoginedMember
	public Member getLoginedMember() {

		Member member = null;

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `member`");
		sql.append("WHERE id = ?", Container.session.getLoginedId());

		Map<String, Object> memberMap = MysqlUtil.selectRow(sql);

		if (!memberMap.isEmpty()) {
			member = new Member(memberMap);

		}

		return member;
	}

	// getMemberByLoginId
	public Member getMemberByLoginId(String loginId) {

		Member member = null;
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `member`");
		sql.append("WHERE loginId = ?", loginId);

		Map<String, Object> memberMap = MysqlUtil.selectRow(sql);

		if (!memberMap.isEmpty()) {
			member = new Member(memberMap);

		}

		return member;
	}

	// doJoin
	public int doJoin(String loginId, String pw, String name) {

		SecSql sql = new SecSql();

		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginpw = ? ", pw);
		sql.append(", `name` = ?", name);

		return MysqlUtil.insert(sql);

	}

	// getMembers
	public List<Member> getMembers() {
		List<Member> members = new ArrayList<>();
		
		SecSql sql= new SecSql();
		
		sql.append("SELECT * FROM `member`");
		
		List<Map<String,Object>> memberMapList = MysqlUtil.selectRows(sql);
		
		for(Map<String,Object> memberMap : memberMapList) {
			members.add(new Member(memberMap));
		}
		
		return members;
	}

}
