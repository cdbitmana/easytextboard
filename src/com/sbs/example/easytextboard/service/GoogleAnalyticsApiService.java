package com.sbs.example.easytextboard.service;

import java.io.IOException;

import com.google.analytics.data.v1alpha.AlphaAnalyticsDataClient;
import com.google.analytics.data.v1alpha.DateRange;
import com.google.analytics.data.v1alpha.Dimension;
import com.google.analytics.data.v1alpha.Entity;
import com.google.analytics.data.v1alpha.Metric;
import com.google.analytics.data.v1alpha.Row;
import com.google.analytics.data.v1alpha.RunReportRequest;
import com.google.analytics.data.v1alpha.RunReportResponse;
import com.sbs.example.easytextboard.container.Container;
import com.sbs.example.easytextboard.dao.Ga4DataDao;

public class GoogleAnalyticsApiService {
	private Ga4DataDao ga4DataDao;

	public GoogleAnalyticsApiService() {
		ga4DataDao = new Ga4DataDao();
	}

	public void updateGa4DataPageHits() {
		String ga4PropertyId = Container.config.getGa4PropertyId();
		try (AlphaAnalyticsDataClient analyticsData = AlphaAnalyticsDataClient.create()) {
			RunReportRequest request = RunReportRequest.newBuilder().setLimit(-1)
					.setEntity(Entity.newBuilder().setPropertyId(ga4PropertyId))
					.addDimensions(Dimension.newBuilder().setName("pagePath"))
					.addMetrics(Metric.newBuilder().setName("activeUsers"))
					.addDateRanges(DateRange.newBuilder().setStartDate("2020-12-01").setEndDate("today")).build();

			

			RunReportResponse response = analyticsData.runReport(request);

			System.out.println("Report result:");
			for (Row row : response.getRowsList()) {

				String pagePath = row.getDimensionValues(0).getValue();
				pagePath = pagePath.replaceAll("/", "");
				pagePath = "/" + pagePath;
				if(pagePath.indexOf("?") != -1) {
					pagePath = pagePath.substring(0, pagePath.indexOf("?"));	
				}
				
				int hit = Integer.parseInt(row.getMetricValues(0).getValue());
				System.out.printf("%s , %d\n", pagePath, hit);

				update(pagePath, hit);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void update(String pagePath, int hit) {
		ga4DataDao.deletePagePath(pagePath);
		ga4DataDao.savePagePath(pagePath, hit);
	}

	public void updatePageHits() {
		updateGa4DataPageHits();
		Container.articleService.updatePageHits();
	}

	
	public int getTodayVisit() {
		String ga4PropertyId = Container.config.getGa4PropertyId();
		int hit = 0;
		try (AlphaAnalyticsDataClient analyticsData = AlphaAnalyticsDataClient.create()) {
			RunReportRequest request = RunReportRequest.newBuilder().setLimit(-1)
					.setEntity(Entity.newBuilder().setPropertyId(ga4PropertyId))
					.addDimensions(Dimension.newBuilder().setName("pagePath"))
					.addMetrics(Metric.newBuilder().setName("activeUsers"))
					.addDateRanges(DateRange.newBuilder().setStartDate("yesterday").setEndDate("today")).build();

			

			RunReportResponse response = analyticsData.runReport(request);

			System.out.println("Report result:");
			for (Row row : response.getRowsList()) {

				String pagePath = row.getDimensionValues(0).getValue();
				pagePath = pagePath.replaceAll("/", "");
				pagePath = "/" + pagePath;
				if(pagePath.indexOf("?") != -1) {
					pagePath = pagePath.substring(0, pagePath.indexOf("?"));	
				}
				if(pagePath.equals("/index.html")) {
					hit = hit + Integer.parseInt(row.getMetricValues(0).getValue());
					
				}
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hit;
	}

	public int getTotalVisit() {
		String ga4PropertyId = Container.config.getGa4PropertyId();
		int hit = 0;
		try (AlphaAnalyticsDataClient analyticsData = AlphaAnalyticsDataClient.create()) {
			RunReportRequest request = RunReportRequest.newBuilder().setLimit(-1)
					.setEntity(Entity.newBuilder().setPropertyId(ga4PropertyId))
					.addDimensions(Dimension.newBuilder().setName("pagePath"))
					.addMetrics(Metric.newBuilder().setName("activeUsers"))
					.addDateRanges(DateRange.newBuilder().setStartDate("2020-12-01").setEndDate("today")).build();

			

			RunReportResponse response = analyticsData.runReport(request);

			System.out.println("Report result:");
			
			for (Row row : response.getRowsList()) {

				String pagePath = row.getDimensionValues(0).getValue();
				pagePath = pagePath.replaceAll("/", "");
				pagePath = "/" + pagePath;
				if(pagePath.indexOf("?") != -1) {
					pagePath = pagePath.substring(0, pagePath.indexOf("?"));	
				}
				if(pagePath.contains("/index.html") || pagePath.equals("/")) {
					hit = hit + Integer.parseInt(row.getMetricValues(0).getValue());
					System.out.println(hit);
					
				}
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hit;
	}
}
