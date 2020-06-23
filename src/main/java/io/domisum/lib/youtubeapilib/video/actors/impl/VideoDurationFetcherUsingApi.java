package io.domisum.lib.youtubeapilib.video.actors.impl;

import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.apiclient.source.AuthorizedYouTubeApiClientSource;
import io.domisum.lib.youtubeapilib.video.VideoDoesNotExistException;
import io.domisum.lib.youtubeapilib.video.actors.VideoDurationFetcher;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
public class VideoDurationFetcherUsingApi
		implements VideoDurationFetcher
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeApiClientSource authorizedYouTubeApiClientSource;
	
	
	// FETCH
	@Override
	public Duration fetch(YouTubeApiCredentials credentials, String videoId)
			throws IOException
	{
		var youTubeDataApiClient = authorizedYouTubeApiClientSource.getFor(credentials).getYouTubeDataApiClient();
		
		var videosListByIdRequest = youTubeDataApiClient.videos().list("contentDetails");
		videosListByIdRequest.setId(videoId);
		var response = videosListByIdRequest.execute();
		
		var responseItems = response.getItems();
		if(responseItems.isEmpty())
			throw new VideoDoesNotExistException(videoId);
		String durationString = responseItems.get(0).getContentDetails().getDuration();
		var duration = Duration.parse(durationString);
		
		if(duration.isZero())
			throw new IOException("YouTube API returned video length of zero, video is probably still processing");
		
		return duration;
	}
	
}
