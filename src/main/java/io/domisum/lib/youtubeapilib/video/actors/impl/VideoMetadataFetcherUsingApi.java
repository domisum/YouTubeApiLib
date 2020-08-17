package io.domisum.lib.youtubeapilib.video.actors.impl;

import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.apiclient.source.AuthorizedYouTubeDataApiClientSource;
import io.domisum.lib.youtubeapilib.video.VideoCategory;
import io.domisum.lib.youtubeapilib.video.VideoDoesNotExistException;
import io.domisum.lib.youtubeapilib.video.YouTubeVideoMetadata;
import io.domisum.lib.youtubeapilib.video.actors.VideoMetadataFetcher;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class VideoMetadataFetcherUsingApi
		implements VideoMetadataFetcher
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeDataApiClientSource authorizedYouTubeDataApiClientSource;
	
	
	// FETCH
	@Override
	public YouTubeVideoMetadata fetch(YouTubeApiCredentials credentials, String videoId)
			throws IOException
	{
		var youTubeDataApiClient = authorizedYouTubeDataApiClientSource.getFor(credentials).getYouTubeApiClient();
		
		var videosListByIdRequest = youTubeDataApiClient.videos().list("snippet");
		videosListByIdRequest.setId(videoId);
		var response = videosListByIdRequest.execute();
		
		var responseItems = response.getItems();
		if(responseItems.isEmpty())
			throw new VideoDoesNotExistException(videoId);
		var video = responseItems.get(0);
		
		String title = video.getSnippet().getTitle();
		String description = video.getSnippet().getDescription();
		var tags = video.getSnippet().getTags();
		String categoryIdString = video.getSnippet().getCategoryId();
		var videoCategory = VideoCategory.fromCategoryId(Integer.parseInt(categoryIdString));
		
		var youTubeVideoMetadata = new YouTubeVideoMetadata(title, description, tags, videoCategory);
		
		return youTubeVideoMetadata;
	}
	
}
