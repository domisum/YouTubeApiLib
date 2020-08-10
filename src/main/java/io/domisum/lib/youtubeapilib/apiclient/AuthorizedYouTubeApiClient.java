package io.domisum.lib.youtubeapilib.apiclient;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import lombok.Getter;

import java.time.Duration;

public class AuthorizedYouTubeApiClient
{
	
	// CONSTANTS
	private static final Duration TIMEOUT = Duration.ofMinutes(5);
	
	// API CLIENTS
	@Getter
	private final YouTube youTubeDataApiClient;
	
	
	// INIT
	public AuthorizedYouTubeApiClient(YouTubeApiCredentials youTubeApiCredentials)
	{
		youTubeDataApiClient = buildYouTubeDataApiClient(youTubeApiCredentials);
	}
	
	
	// BUILDERS
	private YouTube buildYouTubeDataApiClient(YouTubeApiCredentials youTubeApiCredentials)
	{
		var requestInitializer = createAuthorizingRequestInitializer(youTubeApiCredentials);
		requestInitializer = addTimeoutToRequestInitializer(requestInitializer);
		
		var builder = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), requestInitializer);
		builder.setApplicationName("YouTubeApiLib");
		
		return builder.build();
	}
	
	
	// COMPONENT BUILDERS
	private HttpRequestInitializer createAuthorizingRequestInitializer(YouTubeApiCredentials youTubeApiCredentials)
	{
		var credential = new Builder()
				.setJsonFactory(new JacksonFactory())
				.setTransport(new NetHttpTransport())
				.setClientSecrets(youTubeApiCredentials.getClientId(), youTubeApiCredentials.getClientSecret())
				.build();
		credential.setRefreshToken(youTubeApiCredentials.getRefreshToken());
		
		return credential;
	}
	
	private HttpRequestInitializer addTimeoutToRequestInitializer(HttpRequestInitializer baseRequestInitializer)
	{
		return httpRequest->
		{
			baseRequestInitializer.initialize(httpRequest);
			httpRequest.setConnectTimeout((int) TIMEOUT.toMillis());
			httpRequest.setReadTimeout((int) TIMEOUT.toMillis());
		};
	}
	
}
