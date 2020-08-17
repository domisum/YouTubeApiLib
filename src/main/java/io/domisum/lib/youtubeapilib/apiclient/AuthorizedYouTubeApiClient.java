package io.domisum.lib.youtubeapilib.apiclient;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.Getter;

import java.time.Duration;

public abstract class AuthorizedYouTubeApiClient<T>
{
	
	// CONSTANTS
	private static final Duration TIMEOUT = Duration.ofMinutes(5);
	
	// API CLIENTS
	@Getter
	private final T youTubeApiClient;
	
	
	// INIT
	public AuthorizedYouTubeApiClient(YouTubeApiCredentials youTubeApiCredentials)
	{
		youTubeApiClient = buildYouTubeApiClient(youTubeApiCredentials);
	}
	
	
	// BUILDERS
	private T buildYouTubeApiClient(YouTubeApiCredentials youTubeApiCredentials)
	{
		var requestInitializer = createAuthorizingRequestInitializer(youTubeApiCredentials);
		requestInitializer = addTimeoutToRequestInitializer(requestInitializer);
		
		var youTubeApiClient = build(requestInitializer);
		return youTubeApiClient;
	}
	
	protected abstract T build(HttpRequestInitializer requestInitializer);
	
	
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
