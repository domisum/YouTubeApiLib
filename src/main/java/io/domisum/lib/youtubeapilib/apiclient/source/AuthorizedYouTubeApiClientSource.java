package io.domisum.lib.youtubeapilib.apiclient.source;

import io.domisum.lib.youtubeapilib.apiclient.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;

public interface AuthorizedYouTubeApiClientSource
{
	
	AuthorizedYouTubeApiClient getFor(YouTubeApiCredentials credentials);
	
}
