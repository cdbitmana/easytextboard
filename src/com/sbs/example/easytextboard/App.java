package com.sbs.example.easytextboard;

import java.util.ArrayList;

import java.util.Scanner;

import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.controller.*;

import com.sbs.example.easytextboard.service.*;

public class App {
	ArticleController articleController;
	MemberController memberController;
	ArticleService articleService;
	MemberService memberService;

	public App() {

		articleController = Container.articleController;
		memberController = Container.memberController;
		articleService = Container.articleService;
		memberService = Container.memberService;

	}

	// run 메소드
	public void run() {

		Scanner scanner = new Scanner(System.in);
		Controller controller;
		while (true) {
			System.out.printf("명령어) ");
			String command = scanner.nextLine();

			// 프로그램 종료
			if (command.equals("system exit")) {
				System.out.println("프로그램을 종료합니다.");
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

	public Controller getControllerByCmd(String command) {
		if (command.startsWith("article")) {
			return articleController;
		} else if (command.startsWith("member")) {
			return memberController;
		}
		return null;
	}
}