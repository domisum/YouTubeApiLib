package io.domisum.lib.youtubeapilib.action.impl.actions;

import com.google.api.services.youtubeAnalytics.v2.model.QueryResponse;
import io.domisum.lib.auxiliumlib.exceptions.IncompleteCodeError;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.youtubeapilib.action.VideoMetricSnapshotFetcher;
import io.domisum.lib.youtubeapilib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.exceptions.VideoDoesNotExistException;
import io.domisum.lib.youtubeapilib.model.analytics.Metric;
import io.domisum.lib.youtubeapilib.model.analytics.VideoMetricSnapshot;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class VideoMetricSnapshotFetcherUsingApi
		implements VideoMetricSnapshotFetcher
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;
	
	
	// FETCH
	@Override
	public VideoMetricSnapshot fetchForMonetizedChannel(String videoId)
			throws IOException
	{
		return fetch(videoId, true);
	}
	
	@Override
	public VideoMetricSnapshot fetchForNotMonetizedChannel(String videoId)
			throws IOException
	{
		return fetch(videoId, false);
	}
	
	private VideoMetricSnapshot fetch(String videoId, boolean monetized)
			throws IOException
	{
		var omittedMetrics = new HashSet<Metric>();
		if(monetized)
			omittedMetrics.add(Metric.REVENUE_DOLLAR_CENT);
		
		var query = authorizedYouTubeApiClient.getYouTubeAnalyticsApiClient().reports().query();
		query.setIds("channel==MINE");
		query.setMetrics(createMetricsString(omittedMetrics));
		query.setFilters("video=="+videoId);
		query.setStartDate("2000-01-01");
		query.setEndDate("2099-01-01");
		
		var response = query.execute();
		
		if(response.getRows().isEmpty())
			throw new VideoDoesNotExistException(videoId);
		
		var metricValues = new HashMap<Metric,Long>();
		for(var metric : Metric.values())
			if(omittedMetrics.contains(metric))
				metricValues.put(metric, 0L);
			else
				metricValues.put(metric, parseMetric(response, metric));
		
		return new VideoMetricSnapshot(metricValues, monetized);
	}
	
	private String createMetricsString(Set<Metric> omittedMetrics)
	{
		var youTubeMetricNames = new ArrayList<String>();
		for(var metric : Metric.values())
			if(!omittedMetrics.contains(metric))
				youTubeMetricNames.add(getMetricYouTubeName(metric));
		
		return StringUtil.listToString(youTubeMetricNames, ",");
	}
	
	private String getMetricYouTubeName(Metric metric)
	{
		switch(metric)
		{
			case REVENUE_DOLLAR_CENT: return "estimatedRevenue";
			case VIEWS: return "views";
			case WATCH_TIME_MINUTES: return "estimatedMinutesWatched";
			case COMMENTS: return "comments";
			case LIKES: return "likes";
			case DISLIKES: return "dislikes";
			default: throw new IncompleteCodeError("no youtube name specified for metric "+metric);
		}
	}
	
	private long parseMetric(QueryResponse response, Metric metric)
			throws IOException
	{
		int columnIndex = getMetricColumnIndex(response, metric);
		var rows = response.getRows();
		var row = rows.get(0);
		
		Number valueNumber = (Number) row.get(columnIndex);
		double value = valueNumber.doubleValue();
		return convertMetricValueToRightUnit(metric, value);
	}
	
	private int getMetricColumnIndex(QueryResponse response, Metric metric)
			throws IOException
	{
		for(int i = 0; i < response.getColumnHeaders().size(); i++)
		{
			var columnHeader = response.getColumnHeaders().get(i);
			if(Objects.equals(columnHeader.getName(), getMetricYouTubeName(metric)))
				return i;
		}
		
		throw new IOException("result table didn't contain metric "+metric);
	}
	
	private long convertMetricValueToRightUnit(Metric metric, double value)
	{
		if(metric == Metric.REVENUE_DOLLAR_CENT)
			return Math.round(value*100);
		
		return Math.round(value);
	}
	
}
