package service;

import java.util.ArrayList;

import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.dto.Article;

import dao.ArticleDao;

public class ArticleService {

	ArticleDao articleDao;

	// 기본 생성자
	public ArticleService() {
		articleDao = Container.articleDao;
	}

	// getLastArticleId 메소드 : lastArticleId 값 리턴
	public int getLastArticleId() {
		return articleDao.getLastArticleId();
	}

	// getArticles 메소드 : articles 리스트 리턴
	public ArrayList<Article> getArticles() {
		return articleDao.getArticles();
	}

	// add 메소드 : 게시물 추가
	public void add(String title, String body) {
		articleDao.add(title, body);
	}

	// printList 메소드 : 게시물 리스트를 10개씩 페이지로 출력
	public void printList(ArrayList<Article> list, int listNum) {
		articleDao.printList(list, listNum);
	}

	// getIndexById 메소드 : 게시물 번호에 맞는 게시물 인덱스 리턴
	public int getIndexById(int id) {
		return articleDao.getIndexById(id);
	}

	// getArticle 메소드 : 게시물 번호에 맞는 게시물 객체 리턴
	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}
}
