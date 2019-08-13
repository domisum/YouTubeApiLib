package de.domisum.youtubevideouploadlib.action.videometadata;

import de.domisum.youtubevideouploadlib.model.video.YouTubeVideoMetadata;

import java.io.IOException;

public interface VideoMetadataFetcher
{

	YouTubeVideoMetadata fetch(String videoId) throws IOException;

}
