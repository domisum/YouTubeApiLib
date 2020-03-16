package io.domisum.lib.youtubeapilib.action.videometadata;

import io.domisum.lib.youtubeapilib.model.video.YouTubeVideoMetadata;

import java.io.IOException;

public interface VideoMetadataFetcher
{

	YouTubeVideoMetadata fetch(String videoId) throws IOException;

}
