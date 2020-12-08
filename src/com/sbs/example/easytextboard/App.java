package com.sbs.example.easytextboard;

import java.util.Scanner;

import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.controller.*;

import com.sbs.example.easytextboard.service.*;
import com.sbs.example.easytextboard.mysqlutil.*;

public class App {
	ArticleController articleController;
	MemberController memberController;
	ArticleService articleService;
	MemberService memberService;
	BuildController buildController;

	public App() {

		articleController = Container.articleController;
		memberController = Container.memberController;
		articleService = Container.articleService;
		memberService = Container.memberService;
		buildController = Container.buildController;

	}

	// run 메소드
	public void run() {

		
		
		Scanner scanner = new Scanner(System.in);
		Controller controller;
		
		

		while (true) {
			System.out.printf("명령어) ");
			String command = scanner.nextLine();

			MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "textBoard");

			if (command.equals("system exit")) {
				System.out.println("프로그램을 종료합니다.");
				MysqlUtil.closeConnection();
				break;
			}

			controller = getControllerByCmd(command);

			if (controller == null) {
				System.out.println("존재하지 않는 명령어");
				continue;
			}

			controller.doCommand(command);

		}
		scanner.close();
	}

	// getControllerByCmd
	public Controller getControllerByCmd(String command) {
		if (command.startsWith("article")) {
			return articleController;
		} else if (command.startsWith("member")) {
			return memberController;
		} else if (command.startsWith("build")) {
			return buildController;
		}
		return null;
	}
}