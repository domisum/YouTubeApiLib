package io.domisum.lib.youtubeapilib.apiclient;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;

public class AuthorizedYouTubeDataApiClient
		extends AuthorizedYouTubeApiClient<YouTube>
{
	
	// INIT
	public AuthorizedYouTubeDataApiClient(YouTubeApiCredentials youTubeApiCredentials)
	{
		super(youTubeApiCredentials);
	}
	
	
	// BUILD
	@Override
	protected YouTube build(HttpRequestInitializer requestInitializer)
	{
		return new YouTube(new NetHttpTransport(), new JacksonFactory(), requestInitializer);
	}
	
}
