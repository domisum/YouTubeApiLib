package io.domisum.lib.youtubeapilib.exceptions;

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
