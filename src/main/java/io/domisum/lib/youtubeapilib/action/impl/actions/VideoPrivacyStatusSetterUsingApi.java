package io.domisum.lib.youtubeapilib.action.impl.actions;

import com.google.api.services.youtube.YouTube.Videos.Update;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoStatus;
import io.domisum.lib.youtubeapilib.action.VideoPrivacyStatusSetter;
import io.domisum.lib.youtubeapilib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.model.PrivacyStatus;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class VideoPrivacyStatusSetterUsingApi implements VideoPrivacyStatusSetter
{

	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// SET
	@Override
	public void setPrivacyStatus(String videoId, PrivacyStatus privacyStatus) throws IOException
	{
		Video video = new Video();
		video.setId(videoId);

		VideoStatus status = new VideoStatus();
		video.setStatus(status);
		status.setPrivacyStatus(privacyStatus.name());

		Update videosUpdateRequest = authorizedYouTubeApiClient.getYouTubeApiClient().videos().update("status", video);
		videosUpdateRequest.execute();
	}

}
