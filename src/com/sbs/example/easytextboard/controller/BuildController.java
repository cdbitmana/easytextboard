package com.sbs.example.easytextboard.controller;

import java.util.Scanner;

import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.service.BuildService;


public class BuildController extends Controller {
	private Scanner sc;
	private BuildService buildService;

	public BuildController() {
		buildService = Container.buildService;
		sc = Container.scanner;
	}

	public void doCommand(String command) {
		if (command.equals("build site")) {
			doHtml();
		}
	}

	private void doHtml() {
		System.out.println("== html 생성을 시작합니다. ==");
		buildService.makeHtml();

	}

}
