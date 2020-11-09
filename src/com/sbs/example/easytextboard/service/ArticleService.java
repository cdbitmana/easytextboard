package com.sbs.example.easytextboard.service;

import java.util.ArrayList;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dao.*;
import com.sbs.example.easytextboard.dto.*;

public class ArticleService {

	private ArticleDao articleDao;
	private int boardNumber;
	private ArrayList<Board> boards;

	public ArticleService() {
		articleDao = Container.articleDao;
		boards = new ArrayList<>();
		boardNumber = 0;

		makeBoard("공지사항");
	}

	public int makeBoard(String name) {
		Board board = new Board();
		board.setBoardId(boardNumber + 1);
		board.setBoardName(name);
		boardNumber = board.getBoardId();

		boards.add(board);

		return boardNumber;

	}

	public ArrayList<Article> getArticles() {
		return articleDao.getArticles();
	}

	public Article getArticleByNum(int number) {
		return articleDao.getArticleByNum(number);
	}

	public Article getArticleByIndex(int index) {
		return articleDao.getArticleByIndex(index);
	}

	public int remove(Article article) {
		return articleDao.remove(article);
	}

	public ArrayList<Article> searchArticle(String title) {
		return articleDao.searchArticle(title);

	}

	public ArrayList<Board> getBoards() {
		return boards;
	}

	public Board getBoardById(int id) {
		for (Board board : boards) {
			if (board.getBoardId() == id) {
				return board;
			}
		}
		return null;
	}

	public boolean isExistBoard(int id) {
		for (Board board : boards) {
			if (board.getBoardId() == id) {
				return true;
			}
		}
		return false;
	}

}
