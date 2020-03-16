package io.domisum.lib.youtubeapilib.action.upload;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface VideoThumbnailUploader
{

	void uploadThumbnail(String videoId, BufferedImage thumbnail) throws IOException;

}
