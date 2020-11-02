package com.sbs.example.easytextboard.container;

import com.sbs.example.easytextboard.session.Session;

import dao.ArticleDao;
import dao.MemberDao;
import service.ArticleService;
import service.MemberService;

public class Container {

	public static Session session;
	public static ArticleDao articleDao;
	public static MemberDao memberDao;
	public static MemberService memberService;
	public static ArticleService articleService;

	static {
		session = new Session();
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
		memberService = new MemberService();
		articleService = new ArticleService();
	}

}
