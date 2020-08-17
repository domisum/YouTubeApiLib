package io.domisum.lib.youtubeapilib.video.actors.impl;

import com.google.api.services.youtube.YouTube.Videos.Update;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoStatus;
import io.domisum.lib.youtubeapilib.PrivacyStatus;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.apiclient.source.AuthorizedYouTubeDataApiClientSource;
import io.domisum.lib.youtubeapilib.video.actors.VideoPrivacyStatusSetter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class VideoPrivacyStatusSetterUsingApi
		implements VideoPrivacyStatusSetter
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeDataApiClientSource authorizedYouTubeDataApiClientSource;
	
	
	// SET
	@Override
	public void setPrivacyStatus(YouTubeApiCredentials credentials, String videoId, PrivacyStatus privacyStatus)
			throws IOException
	{
		var videosUpdateRequest = createRequest(credentials, videoId, privacyStatus);
		videosUpdateRequest.execute();
	}
	
	private Update createRequest(YouTubeApiCredentials credentials, String videoId, PrivacyStatus privacyStatus)
			throws IOException
	{
		var youTubeDataApiClient = authorizedYouTubeDataApiClientSource.getFor(credentials).getYouTubeApiClient();
		
		var video = new Video();
		video.setId(videoId);
		
		var status = new VideoStatus();
		status.setPrivacyStatus(privacyStatus.name());
		video.setStatus(status);
		
		var update = youTubeDataApiClient.videos().update("status", video);
		return update;
	}
	
}
