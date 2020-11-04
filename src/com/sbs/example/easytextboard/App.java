package com.sbs.example.easytextboard;

import java.util.Scanner;

import com.sbs.example.easytextboard.controller.*;

public class App {

	public void run() {

		ArticleController articleController = new ArticleController();
		MemberController memberController = new MemberController();
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.printf("명령어) ");
			String command = sc.nextLine();

			if (command.startsWith("article")) {
				articleController.doCommand(sc, command);
			} else if (command.startsWith("member")) {
				memberController.doCommand(sc, command);
			} else if (command.equals("system exit")) {
				System.out.println("프로그램 종료");
				break;
			} else {
				System.out.println("존재하지 않는 명령어");
				continue;
			}
		}
		sc.close();
	}

}
