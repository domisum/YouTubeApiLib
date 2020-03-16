package io.domisum.lib.youtubeapilib.action.videometadata;

import io.domisum.lib.youtubeapilib.model.video.YouTubeVideoMetadata;

import java.io.IOException;

public interface VideoMetadataSetter
{

	void setMetadata(String videoId, YouTubeVideoMetadata metadata) throws IOException;

}
