package io.domisum.lib.youtubeapilib.video.actors;

import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.video.YouTubeVideoMetadata;

import java.io.IOException;

public interface VideoMetadataSetter
{
	
	void setMetadata(YouTubeApiCredentials credentials, String videoId, YouTubeVideoMetadata metadata)
			throws IOException;
	
}
