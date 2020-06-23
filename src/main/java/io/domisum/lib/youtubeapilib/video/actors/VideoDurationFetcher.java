package io.domisum.lib.youtubeapilib.video.actors;

import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;

import java.io.IOException;
import java.time.Duration;

public interface VideoDurationFetcher
{
	
	Duration fetch(YouTubeApiCredentials credentials, String videoId)
			throws IOException;
	
}
