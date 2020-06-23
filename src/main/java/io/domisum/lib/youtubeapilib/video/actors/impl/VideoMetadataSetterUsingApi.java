package io.domisum.lib.youtubeapilib.video.actors.impl;

import com.google.api.services.youtube.YouTube.Videos.Update;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.apiclient.source.AuthorizedYouTubeApiClientSource;
import io.domisum.lib.youtubeapilib.video.YouTubeVideoMetadata;
import io.domisum.lib.youtubeapilib.video.actors.VideoMetadataSetter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class VideoMetadataSetterUsingApi
		implements VideoMetadataSetter
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeApiClientSource authorizedYouTubeApiClientSource;
	
	
	// SET
	@Override
	public void setMetadata(YouTubeApiCredentials credentials, String videoId, YouTubeVideoMetadata metadata)
			throws IOException
	{
		var videosUpdateRequest = createRequest(credentials, videoId, metadata);
		videosUpdateRequest.execute();
	}
	
	private Update createRequest(YouTubeApiCredentials credentials, String videoId, YouTubeVideoMetadata metadata)
			throws IOException
	{
		var youTubeDataApiClient = authorizedYouTubeApiClientSource.getFor(credentials).getYouTubeDataApiClient();
		
		var video = new Video();
		video.setId(videoId);
		video.setSnippet(createVideoSnippet(metadata));
		var update = youTubeDataApiClient.videos().update("snippet", video);
		
		return update;
	}
	
	private VideoSnippet createVideoSnippet(YouTubeVideoMetadata metadata)
	{
		var snippet = new VideoSnippet();
		snippet.setTitle(metadata.getTitle());
		snippet.setDescription(metadata.getDescription());
		snippet.setTags(metadata.getTags());
		snippet.setCategoryId(metadata.getCategory().categoryId+"");
		
		return snippet;
	}
	
}
