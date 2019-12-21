package de.domisum.youtubevideouploadlib.action;

import de.domisum.youtubevideouploadlib.model.analytics.VideoMetricSnapshot;

import java.io.IOException;

public interface VideoMetricSnapshotFetcher
{

	VideoMetricSnapshot fetch(String videoId, boolean monetary) throws IOException;

}
