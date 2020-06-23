package io.domisum.lib.youtubeapilib.video.actors.upload;

import io.domisum.lib.youtubeapilib.PrivacyStatus;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.video.YouTubeVideo;

import java.io.IOException;

public interface VideoUploader
{
	
	String upload(YouTubeApiCredentials credentials, YouTubeVideo youTubeVideo, PrivacyStatus privacyStatus)
			throws IOException;
	
}
