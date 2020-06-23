package io.domisum.lib.youtubeapilib.video;

import java.io.IOException;

public class VideoDoesNotExistException
		extends IOException
{
	
	// INIT
	public VideoDoesNotExistException(String videoId)
	{
		super("there is no video with id '"+videoId+"' (visible with current permissions)");
	}
	
}
