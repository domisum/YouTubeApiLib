package io.domisum.lib.youtubeapilib.video.actors.upload;

import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface VideoThumbnailUploader
{
	
	void uploadThumbnail(YouTubeApiCredentials credentials, String videoId, BufferedImage thumbnail)
			throws IOException;
	
}
