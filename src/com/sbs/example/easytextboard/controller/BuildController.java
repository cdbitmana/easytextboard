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
			Thread thr = new Thread(new Runnable() {
				public void run() {
					while (true) {
						doHtml();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			thr.run();
		}

	}

	private void doHtml() {
		
		buildService.makeHtml();
	}

}
