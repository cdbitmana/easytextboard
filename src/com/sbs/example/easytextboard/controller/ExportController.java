package com.sbs.example.easytextboard.controller;

import java.util.Scanner;

import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.service.ExportService;

public class ExportController extends Controller{
	private Scanner sc;
	private ExportService exportService;
	
	public ExportController () {
		exportService = Container.exportService;
		sc = Container.scanner;
	}
	
	public void doCommand(String command) {
		if(command.equals("export html")) {
			doHtml();
		}
	}

	private void doHtml() {
		System.out.println("== html 생성을 시작합니다. ==");
		exportService.makeHtml();
		
	}

}
