package com.sbs.example.easytextboard;

import java.util.ArrayList;

import java.util.Scanner;

import com.sbs.example.easytextboard.controller.*;
import com.sbs.example.easytextboard.dto.*;

public class App {
	
	// run 메소드
	public void run() {

		Scanner scanner = new Scanner(System.in);
		ArticleController artcicleController = new ArticleController();
		MemberController memberController = new MemberController();

		while (true) {
			System.out.printf("명령어) ");
			String command = scanner.nextLine();

			// 게시물 컨트롤러 실행
			if (command.startsWith("article")) {
				artcicleController.run(scanner, command);
			}
			// 멤버 컨트롤러 실행
			else if (command.startsWith("member")) {
				memberController.run(scanner, command);
			}

			// 프로그램 종료
			else if (command.equals("system exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}

			// 존재하지 않는 명령어
			else {
				System.out.println("존재하지 않는 명령어");
				continue;
			}

		}
		scanner.close();
	}
}