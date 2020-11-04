package com.sbs.example.easytextboard;

import java.util.ArrayList;

import java.util.Scanner;

import com.sbs.example.easytextboard.controller.*;
import com.sbs.example.easytextboard.dto.*;

public class App {
	ArticleController articleController = new ArticleController();
	MemberController memberController = new MemberController();

	// run 메소드
	public void run() {

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.printf("명령어) ");
			String command = scanner.nextLine();

			// 프로그램 종료
			if (command.equals("system exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}

			Controller controller = getControllerByCmd(command);

			if (controller == null) {
				System.out.println("존재하지 않는 명령어");
				continue;
			}
			controller.doCommand(scanner, command);

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