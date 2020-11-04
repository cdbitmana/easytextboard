package com.sbs.example.easytextboard.service;

import java.util.ArrayList;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dao.*;
import com.sbs.example.easytextboard.dto.*;

public class ArticleService {

	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
	}

	public ArrayList<Article> getArticles() {
		return articleDao.getArticles();
	}

	public Article getArticleByNum(int number) {
		return articleDao.getArticleByNum(number);
	}

	public int add(String title, String body, String name) {
		return articleDao.add(title, body, name);
	}

	public Article getArticleByIndex(int index) {
		return articleDao.getArticleByIndex(index);
	}

	public void printList(int listNum) {
		articleDao.printList(listNum);
	}

	public int remove(int articleNum) {
		return articleDao.remove(articleNum);
	}

	public int modify(int articleNum, String title, String body) {
		return articleDao.modify(articleNum, title, body);

	}

	public ArrayList<Article> searchArticle(String title) {
		return articleDao.searchArticle(title);

	}

	public void searchList(int listNum, ArrayList<Article> articles) {
		articleDao.searchList(listNum, articles);
	}

}
