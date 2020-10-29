package com.sbs.example.easytextboard.controller;

import java.util.ArrayList;

import java.util.Scanner;
import com.sbs.example.easytextboard.dto.Article;

public class ArticleController extends Controller {

	ArrayList<Article> articles;
	int lastArticleId = 0;

	// 기본 생성자
	public ArticleController() {
		articles = new ArrayList<Article>();
		inIt();
	}

	// run 메소드
	public void run(Scanner scanner, String command) {

		// 게시물 생성
		if (command.equals("article add")) {

			String title;
			String body;

			System.out.println("==게시물 등록==");
			System.out.printf("제목 : ");
			title = scanner.nextLine();
			System.out.printf("내용 : ");
			body = scanner.nextLine();

			add(title, body);
			System.out.printf("%d번 글이 등록되었습니다.\n", lastArticleId);
		}

		// 게시물 리스트
		else if (command.startsWith("article list")) {
			String[] page = command.split(" ");
			int listNum = 0;
			if (page.length < 3) { // 페이지를 입력 안하면 1페이지가 출력
				System.out.println("번호 / 제목");
				for (int i = articles.size() - 1; i >= articles.size() - 10; i--) {
					if (i < 0) {
						break;
					}
					System.out.printf("%d / %s\n", articles.get(i).id, articles.get(i).title);

				}
				return;

			}
			try {
				listNum = Integer.parseInt(command.split(" ")[2]);
			} catch (NumberFormatException e) {
				System.out.println("페이지는 숫자로 입력해야 합니다.");
				return;
			}

			if (listNum <= 0 || (listNum - 1) * 10 >= articles.size()) {
				System.out.println("페이지가 존재하지 않습니다.");
				return;
			}

			printList(articles, listNum);

		}
		// 게시물 상세
		else if (command.startsWith("article detail")) {
			String[] detail = command.split(" ");
			int Num = 0;
			if (detail.length < 3) {
				System.out.println("존재하지 않는 명령어");
				return;
			}
			try {
				Num = Integer.parseInt(command.split(" ")[2]);
			} catch (NumberFormatException e) {
				System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
				return;
			}

			Article article = getArticle(Num);

			if (article == null) {
				System.out.println("게시물이 존재하지 않습니다.");
				return;
			}

			System.out.println("==게시물 상세==");
			System.out.printf("번호 : %d\n", article.id);
			System.out.printf("제목 : %s\n", article.title);
			System.out.printf("내용 : %s\n", article.body);
			System.out.printf("작성일 : %s\n", article.regDate);
		}
		// 게시물 삭제
		else if (command.startsWith("article delete ")) {
			String[] delete = command.split(" ");
			int Num = 0;
			if (delete.length < 3) {
				System.out.println("존재하지 않는 명령어");
				return;
			}
			try {
				Num = Integer.parseInt(command.split(" ")[2]);
			} catch (NumberFormatException e) {
				System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
				return;
			}

			Article article = getArticle(Num);
			if (article == null) {
				System.out.println("게시물이 존재하지 않습니다.");
				return;
			}
			int searchIndex = getIndexById(Num);
			articles.remove(searchIndex);
			System.out.printf("%d번 게시물이 삭제되었습니다.\n", Num);
		}
		// 게시물 수정
		else if (command.startsWith("article modify ")) {
			String[] modify = command.split(" ");
			int Num = 0;
			if (modify.length < 3) {
				System.out.println("존재하지 않는 명령어");
				return;
			}
			try {
				Num = Integer.parseInt(command.split(" ")[2]);
			} catch (NumberFormatException e) {
				System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
				return;
			}

			Article article = getArticle(Num);
			if (article == null) {
				System.out.println("게시물이 존재하지 않습니다.");
				return;
			}

			String newTitle;
			String newBody;
			System.out.println("==게시물 수정==");
			System.out.printf("새 제목 : ");
			newTitle = scanner.nextLine();
			System.out.printf("새 제목 : ");
			newBody = scanner.nextLine();

			int searchIndex = getIndexById(Num);

			articles.set(searchIndex, new Article(Num, newTitle, newBody));

			System.out.println("게시물이 수정되었습니다.");
		}

		// 게시물 서치
		else if (command.startsWith("article search")) {
			String[] search = command.split(" ");
			int Num = 0;

			ArrayList<Article> searchList = new ArrayList<Article>(); // 검색 결과만 담을 새 ArrayList 생성
			int index = 0;
			for (Article article : articles) {
				if (article.title.contains(search[2])) {
					searchList.add(index, article);
					index++;
				}
			}

			if (search.length < 4) { // 페이지를 입력 안하면 검색 결과에서 1페이지가 출력
				System.out.println("번호 / 제목");
				for (int i = searchList.size() - 1; i >= searchList.size() - 10; i--) {
					if (i < 0) {
						break;
					}
					System.out.printf("%d / %s\n", searchList.get(i).id, searchList.get(i).title);

				}
				return;
			}
			try {
				Num = Integer.parseInt(command.split(" ")[3]);
			} catch (NumberFormatException e) {
				System.out.println("페이지 번호는 숫자로 입력해야 합니다.");
				return;
			}

			System.out.println("==게시물 검색==");
			if (Num <= 0 || (Num - 1) * 10 >= searchList.size()) {
				System.out.println("페이지가 존재하지 않습니다.");
				return;
			}
			System.out.println("번호 / 제목");
			printList(searchList, Num);

		} else {
			System.out.println("존재하지 않는 명령어");
			return;
		}

	}

	// inIt 메소드
	public void inIt() {
		for (int i = 0; i < 32; i++) {
			add("title" + (lastArticleId + 1), "body" + (lastArticleId + 1));
		}
	}

	// add 메소드
	public void add(String title, String body) {
		lastArticleId += 1;
		articles.add(new Article(lastArticleId, title, body));
	}

	// printList 메소드
	public void printList(ArrayList<Article> list, int listNum) {
		int itemsInAPage = 10;
		int start = list.size() - 1;
		start -= itemsInAPage * (listNum - 1);
		int end = start - (itemsInAPage - 1);
		if (end < 0) {
			end = 0;
		}

		for (int i = start; i >= end; i--) {
			System.out.printf("%d / %s\n", list.get(i).id, list.get(i).title);

		}
	}

	// getIndexById 메소드
	public int getIndexById(int id) {
		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).id == id) {
				return i;
			}
		}
		return -1;
	}

	// getArticle 메소드
	public Article getArticle(int id) {
		int index = getIndexById(id);
		if (index == -1) {
			return null;
		}
		return articles.get(index);
	}

}
