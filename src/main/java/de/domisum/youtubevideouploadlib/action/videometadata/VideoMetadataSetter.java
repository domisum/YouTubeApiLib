package de.domisum.youtubevideouploadlib.action.videometadata;

import de.domisum.youtubevideouploadlib.model.video.YouTubeVideoMetadata;

import java.io.IOException;

public interface VideoMetadataSetter
{

	void setMetadata(String videoId, YouTubeVideoMetadata metadata) throws IOException;

}
