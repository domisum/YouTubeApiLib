package io.domisum.lib.youtubeapilib;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.domisum.lib.auxiliumlib.datastructures.LazyCache;

import java.time.Duration;

public abstract class AuthorizedYouTubeApiClientSource<T>
{
	
	// CONSTANTS
	private static final Duration TIMEOUT = Duration.ofMinutes(5);
	
	// CACHE
	private final LazyCache<YouTubeApiCredentials,T> cachedClients = LazyCache.neverExpire();
	
	
	// SOURCE
	public synchronized T getFor(YouTubeApiCredentials credentials)
	{
		var cachedClientOptional = cachedClients.get(credentials);
		if(cachedClientOptional.isPresent())
			return cachedClientOptional.get();
		
		var builtYouTubeClient = buildYouTubeApiClient(credentials);
		cachedClients.put(credentials, builtYouTubeClient);
		return builtYouTubeClient;
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
