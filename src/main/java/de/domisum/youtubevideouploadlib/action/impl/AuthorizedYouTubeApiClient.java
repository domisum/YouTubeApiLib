package de.domisum.youtubevideouploadlib.action.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtubeAnalytics.v2.YouTubeAnalytics;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
public class AuthorizedYouTubeApiClient
{

	// CONSTANTS
	private static final Duration TIMEOUT = Duration.ofMinutes(2);

	// REFERENCES
	@Getter private final YouTubeApiCredentials youTubeApiCredentials;


	// GETTERS
	public YouTube getYouTubeApiClient()
	{
		HttpRequestInitializer requestInitializer = getInitializerWithTimeout(createCredential());
		return new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), requestInitializer)
				.setApplicationName("YouTubeVideoUploadLib")
				.build();
	}

	public YouTubeAnalytics getYouTubeAnalyticsApiClient()
	{
		HttpRequestInitializer requestInitializer = getInitializerWithTimeout(createCredential());
		return new YouTubeAnalytics.Builder(new NetHttpTransport(), new JacksonFactory(), requestInitializer)
				.setApplicationName("DomisumReplay")
				.build();
	}


	private HttpRequestInitializer getInitializerWithTimeout(HttpRequestInitializer requestInitializer)
	{
		return httpRequest->
		{
			requestInitializer.initialize(httpRequest);
			httpRequest.setConnectTimeout((int) TIMEOUT.toMillis());
			httpRequest.setReadTimeout((int) TIMEOUT.toMillis());
		};
	}

	private Credential createCredential()
	{
		Credential credential = new Builder()
				.setJsonFactory(new JacksonFactory())
				.setTransport(new NetHttpTransport())
				.setClientSecrets(youTubeApiCredentials.getClientId(), youTubeApiCredentials.getClientSecret())
				.build()
				.setRefreshToken(youTubeApiCredentials.getRefreshToken());

		return credential;
	}

}
