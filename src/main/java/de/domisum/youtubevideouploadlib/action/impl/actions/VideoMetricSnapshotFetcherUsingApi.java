package de.domisum.youtubevideouploadlib.action.impl.actions;

import com.google.api.services.youtubeAnalytics.v2.YouTubeAnalytics.Reports.Query;
import com.google.api.services.youtubeAnalytics.v2.model.QueryResponse;
import com.google.api.services.youtubeAnalytics.v2.model.ResultTableColumnHeader;
import de.domisum.lib.auxilium.util.StringUtil;
import de.domisum.lib.auxilium.util.java.exceptions.IncompleteCodeError;
import de.domisum.youtubevideouploadlib.action.VideoMetricSnapshotFetcher;
import de.domisum.youtubevideouploadlib.action.impl.AuthorizedYouTubeApiClient;
import de.domisum.youtubevideouploadlib.exceptions.VideoDoesNotExistException;
import de.domisum.youtubevideouploadlib.model.analytics.Metric;
import de.domisum.youtubevideouploadlib.model.analytics.VideoMetricSnapshot;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class VideoMetricSnapshotFetcherUsingApi implements VideoMetricSnapshotFetcher
{

	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// FETCH
	@Override
	public VideoMetricSnapshot fetch(String videoId, boolean monetary) throws IOException
	{
		Query query = authorizedYouTubeApiClient.getYouTubeAnalyticsApiClient().reports().query();
		query.setIds("channel==MINE");
		query.setMetrics(createMetricsString(monetary));
		query.setFilters("video=="+videoId);
		query.setStartDate("2000-01-01");
		query.setEndDate("2099-01-01");

		QueryResponse response = query.execute();

		Map<Metric, Long> metrics = new HashMap<>();
		metrics.put(Metric.REVENUE_DOLLAR_CENT, 0L);
		for(Metric metric : getMetrics(monetary))
			metrics.put(metric, parseMetric(response, metric));

		return new VideoMetricSnapshot(metrics, monetary);
	}

	private String createMetricsString(boolean monetary)
	{
		List<String> youTubeMetricNames = new ArrayList<>();
		for(Metric metric : getMetrics(monetary))
			youTubeMetricNames.add(getMetricYouTubeName(metric));
		return StringUtil.listToString(youTubeMetricNames, ",");
	}

	private Set<Metric> getMetrics(boolean monetary)
	{
		Set<Metric> metrics = new HashSet<>();
		metrics.addAll(Arrays.asList(Metric.values()));
		if(!monetary)
			metrics.remove(Metric.REVENUE_DOLLAR_CENT);

		return metrics;
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

	private long parseMetric(QueryResponse response, Metric metric) throws VideoDoesNotExistException
	{
		int columnIndex = getMetricColumnIndex(response, metric);

		List<List<Object>> rows = response.getRows();
		if(rows.isEmpty())
			throw new VideoDoesNotExistException();

		List<Object> row = rows.get(0);
		double value = ((Number) row.get(columnIndex)).doubleValue();
		return parseMetricValue(metric, value);
	}

	private int getMetricColumnIndex(QueryResponse response, Metric metric)
	{
		for(int i = 0; i < response.getColumnHeaders().size(); i++)
		{
			ResultTableColumnHeader columnHeader = response.getColumnHeaders().get(i);
			if(Objects.equals(columnHeader.getName(), getMetricYouTubeName(metric)))
				return i;
		}

		throw new IllegalStateException("result table didn't contain metric "+metric);
	}

	private long parseMetricValue(Metric metric, double value)
	{
		if(metric == Metric.REVENUE_DOLLAR_CENT)
			return Math.round(value*100);

		return Math.round(value);
	}

}
