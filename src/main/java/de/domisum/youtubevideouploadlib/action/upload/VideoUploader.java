package de.domisum.youtubevideouploadlib.action.upload;

import de.domisum.youtubevideouploadlib.model.PrivacyStatus;
import de.domisum.youtubevideouploadlib.model.video.YouTubeVideo;

import java.io.IOException;

public interface VideoUploader
{

	String upload(YouTubeVideo youTubeVideo, PrivacyStatus privacyStatus) throws IOException;

}
