package io.domisum.lib.youtubevideouploadlib.action;

import io.domisum.lib.youtubevideouploadlib.model.analytics.VideoMetricSnapshot;

import java.io.IOException;

public interface VideoMetricSnapshotFetcher
{

	VideoMetricSnapshot fetch(String videoId, boolean monetary) throws IOException;

}
