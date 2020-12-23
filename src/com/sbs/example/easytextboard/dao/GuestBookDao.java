package com.sbs.example.easytextboard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.easytextboard.dto.GuestBook;
import com.sbs.example.easytextboard.mysqlutil.MysqlUtil;
import com.sbs.example.easytextboard.mysqlutil.SecSql;

public class GuestBookDao {

	// getGuestBooks 메소드
	public List<GuestBook> getGuestBooks() {
		
		List<GuestBook> guestBooks = new ArrayList<>();

		SecSql sql = new SecSql();

		sql.append("SELECT G.* , M.name AS extra__writer FROM guestbook AS G");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON G.memberId = M.id");

		List<Map<String, Object>> guestBookMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> guestBookMap : guestBookMapList) {
			guestBooks.add(new GuestBook(guestBookMap));
		}

		return guestBooks;
	}

}
