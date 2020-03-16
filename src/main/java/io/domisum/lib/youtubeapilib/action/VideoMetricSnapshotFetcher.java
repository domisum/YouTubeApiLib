package io.domisum.lib.youtubeapilib.action;

import io.domisum.lib.youtubeapilib.model.analytics.VideoMetricSnapshot;

import java.io.IOException;

public interface VideoMetricSnapshotFetcher
{

	VideoMetricSnapshot fetch(String videoId, boolean monetary) throws IOException;

}
