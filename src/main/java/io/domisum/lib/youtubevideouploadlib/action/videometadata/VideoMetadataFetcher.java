package io.domisum.lib.youtubevideouploadlib.action.videometadata;

import io.domisum.lib.youtubevideouploadlib.model.video.YouTubeVideoMetadata;

import java.io.IOException;

public interface VideoMetadataFetcher
{

	YouTubeVideoMetadata fetch(String videoId) throws IOException;

}
