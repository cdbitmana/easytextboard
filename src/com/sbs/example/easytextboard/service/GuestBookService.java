package com.sbs.example.easytextboard.service;

import java.util.List;

import com.sbs.example.easytextboard.container.*;
import com.sbs.example.easytextboard.dao.GuestBookDao;
import com.sbs.example.easytextboard.dto.GuestBook;

public class GuestBookService {
	
	private GuestBookDao guestBookDao;
	
	public GuestBookService() {
		guestBookDao = Container.guestBookDao;

	}

	public List<GuestBook> getGuestBooks() {
		return guestBookDao.getGuestBooks();
	}
	
}
