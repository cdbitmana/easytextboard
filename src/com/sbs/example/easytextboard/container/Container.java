package com.sbs.example.easytextboard.container;

import com.sbs.example.easytextboard.session.*;
import com.sbs.example.easytextboard.service.*;

import java.util.Scanner;

import com.sbs.example.easytextboard.AppConfig;
import com.sbs.example.easytextboard.controller.*;
import com.sbs.example.easytextboard.dao.*;

public class Container {
	
	public static Session session;
	public static Scanner scanner;
	public static MemberDao memberDao;
	public static ArticleDao articleDao;
	public static GuestBookDao guestBookDao;
	public static MemberService memberService;
	public static ArticleService articleService;
	public static GuestBookService guestBookService;
	public static BuildService buildService;
	public static DisqusApiService disqusApiService;
	public static MemberController memberController;
	public static ArticleController articleController;	
	public static BuildController buildController;
	public static AppConfig config;

	static {
		config = new AppConfig();
		session = new Session();
		scanner = new Scanner(System.in);
		memberDao = new MemberDao();
		articleDao = new ArticleDao();
		guestBookDao = new GuestBookDao();
		memberService = new MemberService();
		articleService = new ArticleService();
		disqusApiService = new DisqusApiService();
		guestBookService = new GuestBookService();
		buildService = new BuildService();
		memberController = new MemberController();
		articleController = new ArticleController();		
		buildController = new BuildController();
		
	}
}
