package io.domisum.lib.youtubevideouploadlib.action.upload;

import io.domisum.lib.youtubevideouploadlib.model.PrivacyStatus;
import io.domisum.lib.youtubevideouploadlib.model.video.YouTubeVideo;

import java.io.IOException;

public interface VideoUploader
{

	String upload(YouTubeVideo youTubeVideo, PrivacyStatus privacyStatus) throws IOException;

}
