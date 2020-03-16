package io.domisum.lib.youtubeapilib.action.upload;

import io.domisum.lib.youtubeapilib.model.PrivacyStatus;
import io.domisum.lib.youtubeapilib.model.video.YouTubeVideo;

import java.io.IOException;

public interface VideoUploader
{

	String upload(YouTubeVideo youTubeVideo, PrivacyStatus privacyStatus) throws IOException;

}
