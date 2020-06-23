package io.domisum.lib.youtubeapilib.video.actors;

import io.domisum.lib.youtubeapilib.PrivacyStatus;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;

import java.io.IOException;

public interface VideoPrivacyStatusSetter
{
	
	void setPrivacyStatus(YouTubeApiCredentials credentials, String videoId, PrivacyStatus privacyStatus)
			throws IOException;
	
}
