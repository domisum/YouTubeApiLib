package io.domisum.lib.youtubeapilib.action.impl.actions;

import io.domisum.lib.youtubeapilib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.action.videometadata.VideoDurationFetcher;
import io.domisum.lib.youtubeapilib.exceptions.VideoDoesNotExistException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
public class VideoDurationFetcherUsingApi
		implements VideoDurationFetcher
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;
	
	
	// FETCH
	@Override
	public Duration fetch(String videoId)
			throws IOException
	{
		var videosListByIdRequest = authorizedYouTubeApiClient.getYouTubeApiClient().videos().list("contentDetails");
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
