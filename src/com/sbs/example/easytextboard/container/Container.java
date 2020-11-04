package com.sbs.example.easytextboard.container;

import com.sbs.example.easytextboard.session.*;
import com.sbs.example.easytextboard.service.*;
import com.sbs.example.easytextboard.dao.*;

public class Container {
	public static Session session;
	public static MemberDao memberDao;
	public static ArticleDao articleDao;
	public static MemberService memberService;
	public static ArticleService articleService;

	static {
		session = new Session();
		memberDao = new MemberDao();
		articleDao = new ArticleDao();
		memberService = new MemberService();
		articleService = new ArticleService();
	}
}
