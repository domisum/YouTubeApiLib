package io.domisum.lib.youtubeapilib.apiclient.source;

import io.domisum.lib.youtubeapilib.apiclient.AuthorizedYouTubeDataApiClient;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;

public class AuthorizedYouTubeDataApiClientSourceByInitialization
		implements AuthorizedYouTubeDataApiClientSource
{
	
	// SOURCE
	@Override
	public AuthorizedYouTubeDataApiClient getFor(YouTubeApiCredentials credentials)
	{
		return new AuthorizedYouTubeDataApiClient(credentials);
	}
	
}
