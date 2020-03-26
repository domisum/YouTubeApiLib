package io.domisum.lib.youtubeapilib.action.impl.actions;

import com.google.api.services.youtube.YouTube.Videos.Update;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import io.domisum.lib.youtubeapilib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.action.videometadata.VideoMetadataSetter;
import io.domisum.lib.youtubeapilib.model.video.YouTubeVideoMetadata;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class VideoMetadataSetterUsingApi
		implements VideoMetadataSetter
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;
	
	
	// SET
	@Override
	public void setMetadata(String videoId, YouTubeVideoMetadata metadata)
			throws IOException
	{
		var videosUpdateRequest = createRequest(videoId, metadata);
		videosUpdateRequest.execute();
	}
	
	private Update createRequest(String videoId, YouTubeVideoMetadata metadata)
			throws IOException
	{
		var video = new Video();
		video.setId(videoId);
		video.setSnippet(createVideoSnippet(metadata));
		
		return authorizedYouTubeApiClient.getYouTubeApiClient().videos().update("snippet", video);
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
