package io.domisum.lib.youtubeapilib.action.impl.actions;

import io.domisum.lib.youtubeapilib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.action.videometadata.VideoMetadataFetcher;
import io.domisum.lib.youtubeapilib.exceptions.VideoDoesNotExistException;
import io.domisum.lib.youtubeapilib.model.video.VideoCategory;
import io.domisum.lib.youtubeapilib.model.video.YouTubeVideoMetadata;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class VideoMetadataFetcherUsingApi
		implements VideoMetadataFetcher
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;
	
	
	// FETCH
	@Override
	public YouTubeVideoMetadata fetch(String videoId)
			throws IOException
	{
		var videosListByIdRequest = authorizedYouTubeApiClient.getYouTubeApiClient().videos().list("snippet");
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
