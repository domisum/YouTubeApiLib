package io.domisum.lib.youtubeapilib.action;

import io.domisum.lib.youtubeapilib.model.analytics.VideoMetricSnapshot;

import java.io.IOException;

public interface VideoMetricSnapshotFetcher
{
	
	VideoMetricSnapshot fetchForMonetizedChannel(String videoId)
			throws IOException;
	
	VideoMetricSnapshot fetchForNotMonetizedChannel(String videoId)
			throws IOException;
	
}
