package io.domisum.lib.youtubeapilib.apiclient.source;

import io.domisum.lib.youtubeapilib.apiclient.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;

public class AuthorizedYouTubeApiClientSourceByInitialization
		implements AuthorizedYouTubeApiClientSource
{
	
	// SOURCE
	@Override
	public AuthorizedYouTubeApiClient getFor(YouTubeApiCredentials credentials)
	{
		return new AuthorizedYouTubeApiClient(credentials);
	}
	
}
