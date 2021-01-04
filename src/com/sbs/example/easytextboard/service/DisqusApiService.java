package com.sbs.example.easytextboard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sbs.example.easytextboard.apidto.DisqusApiDataListThread;
import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.dto.Article;
import com.sbs.example.easytextboard.util.Util;

public class DisqusApiService {
	
	ArticleService articleService;
	
	public DisqusApiService() {
		articleService = Container.articleService;
	}
	
	// disqus에서 데이터를 받아와서 댓글수와 추천수를 DB에 반영함
		public void loadDisqusData() {
			List<Article> articles = articleService.getArticles();

			for (Article article : articles) {
				Map<String, Object> disqusArticleData = getArticleData(article);

				if (disqusArticleData != null) {
					int likesCount = (int) disqusArticleData.get("likesCount");
					int commentsCount = (int) disqusArticleData.get("commentsCount");

					Map<String, Object> modifyArgs = new HashMap<>();
					modifyArgs.put("id", article.getId());
					modifyArgs.put("likesCount", likesCount);
					modifyArgs.put("commentsCount", commentsCount);

					articleService.modify(modifyArgs);
				}
			}
		}
	
	public Map<String, Object> getArticleData(Article article) {
		
		String fileName = Container.buildService.getArticleDetailFileName(article);
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		DisqusApiDataListThread disqusApiDataListThread = (DisqusApiDataListThread) Util.callApiResponseTo(
				DisqusApiDataListThread.class, url, "api_key=" + Container.config.getDisqusApiKey(),
				"forum=" + Container.config.getDisqusForumName(), "thread:ident=" + fileName);

		if (disqusApiDataListThread == null) {
			return null;
		}

		Map<String, Object> rs = new HashMap<>();
		rs.put("likesCount", disqusApiDataListThread.response.get(0).likes);
		rs.put("commentsCount", disqusApiDataListThread.response.get(0).posts);

		return rs;
	}
}
