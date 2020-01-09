package de.domisum.youtubevideouploadlib.action.impl.actions;

import com.google.api.services.youtube.YouTube.Videos.Update;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoStatus;
import de.domisum.youtubevideouploadlib.action.VideoPrivacyStatusSetter;
import de.domisum.youtubevideouploadlib.action.impl.AuthorizedYouTubeApiClient;
import de.domisum.youtubevideouploadlib.model.PrivacyStatus;
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
