package de.domisum.youtubevideouploadlib.model.analytics;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Metric
{

	REVENUE_DOLLAR_CENT("estimatedRevenue"),
	VIEWS("views"),
	WATCH_TIME_MINUTES("estimatedMinutesWatched"),
	COMMENTS("comments"),
	LIKES("likes"),
	DISLIKES("dislikes");


	@Getter private final String youTubeName;

}
