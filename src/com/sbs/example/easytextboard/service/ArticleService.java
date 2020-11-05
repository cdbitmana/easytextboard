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

	public int add(String title, String body, String name, int writerNumber) {
		return articleDao.add(title, body, writerNumber);
	}

	public Article getArticleByIndex(int index) {
		return articleDao.getArticleByIndex(index);
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

}
