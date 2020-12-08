package com.sbs.example.easytextboard.container;

import com.sbs.example.easytextboard.session.*;
import com.sbs.example.easytextboard.service.*;

import java.util.Scanner;

import com.sbs.example.easytextboard.controller.*;
import com.sbs.example.easytextboard.dao.*;

public class Container {
	public static Session session;
	public static Scanner scanner;
	public static MemberDao memberDao;
	public static ArticleDao articleDao;
	public static MemberService memberService;
	public static ArticleService articleService;
	public static MemberController memberController;
	public static ArticleController articleController;
	public static BuildService buildService;
	public static BuildController buildController;
	

	static {
		session = new Session();
		scanner = new Scanner(System.in);
		memberDao = new MemberDao();
		articleDao = new ArticleDao();
		memberService = new MemberService();
		articleService = new ArticleService();
		memberController = new MemberController();
		articleController = new ArticleController();
		buildService = new BuildService();
		buildController = new BuildController();
	}
}
