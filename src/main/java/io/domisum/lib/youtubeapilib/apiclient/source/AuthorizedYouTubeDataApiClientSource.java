package io.domisum.lib.youtubeapilib.apiclient.source;

import io.domisum.lib.youtubeapilib.apiclient.AuthorizedYouTubeDataApiClient;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;

public interface AuthorizedYouTubeDataApiClientSource
{
	
	AuthorizedYouTubeDataApiClient getFor(YouTubeApiCredentials credentials);
	
}
